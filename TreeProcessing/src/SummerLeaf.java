import java.awt.Color;
import java.lang.Math;
import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;


public class SummerLeaf extends Leaf{
	// Processing applet containing method draw and setup
	PApplet parent;

	// Branch end to be fixed to 
	PVector loc;

	// Leaf color
	Color rgb;

	// Random to choose color
	int icolor;

	// Next leaf to morph into when changing season
	Leaf nextLeaf;

	// Time to morph or not, to fill morph color or not
	boolean morph = false;
	boolean morphColFill = false;

	// Angle for the next leaf (fall leaf) 
	float angle;
	
	// Length and weight of this leaf
	float length, weight;

	// Time  and height to make the leaf fall from
	boolean fall = false;
	float fallHeight = 10;

	// Leaf and fruit color
	public int [] rSummer = {34,27,9,86,22,127};
	public int [] gSummer = {120,79,106,130,184,221};
	public int [] bSummer = {15,8,9,3,78,76};

	// Vertex
	PVector [] vertex = {new PVector(0,0), 
			new PVector(5,5)//1
	};
	PVector [] vertex_fall;/* 
			new PVector(0,0), 
			new PVector(1,0),	//1
			new PVector(0,0),	//2
			new PVector(0,0),	//3
			new PVector(0,0),	//4
			new PVector(0,0),	//5
			new PVector(0,0),	//6
			new PVector(0,0),	//7
			new PVector(0,0),	//8
			new PVector(0,0),	//9
	};	*/

	/**************************************
	 * * CONSTRUCTOR
	 */

	SummerLeaf(PApplet p, PVector l,int icol,int f,float a){
		super(p,"summer",l);
		parent = p;
		loc = l;

		// initializes color
		rgb = new Color(rSummer[icolor],gSummer[icolor],bSummer[icolor]); 

		icolor = icol;
		angle = a;
		length = icolor;
		weight = icolor*0.7f;
				
		fruit = false;

		// decides whether it is a fruit or leaf
		if(f == 1){
			fruit = true;
		} 

		vertex[1].mult(length);

		// intialize the vertex array for next leaf
		vertex_fall = new PVector[]{ 
				new PVector(0,0), 
				new PVector(1,0),	//1
				new PVector(0,0),	//2
				new PVector(0,0),	//3
				new PVector(0,0),	//4
				new PVector(0,0),	//5
				new PVector(0,0),	//6
				new PVector(0,0),	//7
				new PVector(0,0),	//8
				new PVector(0,0),	//9
		};
	}
	/**************************************
	 * * COLOR
	 */
	public  Color getRgb (){
		return rgb;
	}
	public void color(){
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		//	parent.fill(rgb.getRed()+10,rgb.getGreen(),rgb.getBlue()+10);

	}



	public PVector getVertex(int index){
		return vertex[index];
	}

	/**************************************
	 * * MORPHING
	 */

	public Leaf nextL(){
		return nextLeaf;//new FallLeaf(parent,loc,icolor,angle);
	}

	public void changeSeason(){
		//change to Fall
		nextLeaf = new FallLeaf(parent,loc,icolor,angle);
	}
	private void fallAnim(){ //for fruits
		parent.translate(0, fallHeight);
		fallHeight += parent.random(5,8) + fallHeight/20;
	}

	public void colorMorphing(){
		float step=35f;
		Color rgb_end = nextLeaf.getRgb();
		float hsb_start[] = new float[3];
		float hsb_end[] = new float[3];

		Color.RGBtoHSB(rgb.getRed(),rgb.getGreen(),rgb.getBlue(),hsb_start);
		//	System.out.println(nextLeaf.toString());
		Color.RGBtoHSB(rgb_end.getRed(),rgb_end.getGreen(),rgb_end.getBlue(),hsb_end);

		// Change the hue to make a color gradient
		hsb_start[0] += (hsb_end[0]-hsb_start[0])/step;
		hsb_start[1] += (hsb_end[1]-hsb_start[1])/step;
		hsb_start[2] += (hsb_end[2]-hsb_start[2])/step;

		rgb = new Color(Color.HSBtoRGB(hsb_start[0],hsb_start[1],hsb_start[2]));
		
		parent.strokeWeight(weight);
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		parent.fill(255);
		if(morphColFill){
			parent.fill(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		}
	}

	public boolean morphing(int k){
		//colMorph = k;
		float max = 300f;
		if(k>max){
			return true;
		}
		else { 
			if(k==max/30){
				fall = true; // fruits fall
				morphColFill = true; //fill leaf
			}
			// Change gradually the stroke weight
			weight = (float)(1-k/max) *(0.7f*icolor) + (float)k/max*(0.4f*icolor) ;
			
			// Morph rectangle first
			vertex[0].x = (float)(1- k/max)*vertex[0].x 
					+ (float)k/max*length/2;
			vertex[0].y = (float)(1- k/max)*vertex[0].y ;
			//+ (float)k/max*loc.y;//2;
			vertex[1].x = (float)(1- k/max)*vertex[1].x 
					+ (float)k/max*(length);//*2;
			vertex[1].y = (float)(1- k/max)*vertex[1].y 
					+ (float)k/max*(length);//*2;

			if(k>=max*3/5){
				morph = true; // leaf morphing to fall leaf
				
				for (int i=0;i<2;i++){
					vertex_fall[i] = vertex[i];
				}
				for (int i=2;i<10;i++){
					//System.out.println((k-max/4)/(3*max/4));//(k-max/4)/(3*max/4)
					vertex_fall[i].x = (float)(k-max/4)/(3*max/4)*nextLeaf.getVertex(i).x;//*length/2;
					vertex_fall[i].y = (float)(k-max/4)/(3*max/4)*nextLeaf.getVertex(i).y;//*length/2;

				}
			}
			if(k==max){
			/*	for (int i=0;i<10;i++){
					System.out.println("x: "+vertex_fall[i].x+" y: "+vertex_fall[i].y);
				}
				System.out.println(" should be ");
				for (int i=0;i<10;i++){
					System.out.println("x: "+nextLeaf.getVertex(i).x+" y: "+nextLeaf.getVertex(i).y);
				}*/
			}

		}
		return false;
	}


	/**************************************
	 * * DISPLAY
	 */
	public void displayFruit(float size){
		parent.fill(icolor*10+190,icolor*10+10,0);
		parent.stroke(icolor*10+190+50,icolor*10+10,0);
		parent.ellipse(loc.x,loc.y,size,size);
	}

	public void display() {
		//parent.noStroke();
		//	parent.fill(50,100);
		//parent.strokeWeight(icolor*vertex[0].x/10f);
		if(!morph){
			parent.strokeWeight(weight);
			if(!fruit ){
				color();
				parent.fill(255);
				parent.pushMatrix();
				parent.translate(loc.x,loc.y);
				parent.rotate(PConstants.PI/(float)(icolor+1));
				parent.rect(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y);//, 4);
				parent.popMatrix();

			}
			else{
				parent.pushMatrix();

				if (fall){
					fallAnim();
				}
				displayFruit(6f);
				parent.popMatrix();

				/*parent.triangle(loc.x-0.7f,loc.y,
				loc.x+0.7f,loc.y,
				loc.x,loc.y+0.7f);*/
			}
		}
		else{
			//System.out.println("angle: "+angle);
			colorMorphing();

			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			//parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.rect(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[1].x,vertex_fall[1].y);
			parent.popMatrix();

			parent.pushMatrix();
			parent.translate(loc.x-length/2,loc.y);

			//parent.translate(loc.x, loc.y);
			parent.translate(vertex[0].x,vertex[0].y);
			parent.rotate(angle);

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[1].x,vertex_fall[1].y,
					vertex_fall[2].x,vertex_fall[2].y);//-3

			parent.triangle(vertex_fall[3].x,vertex_fall[3].y,
					vertex_fall[4].x,vertex_fall[4].y,
					vertex_fall[6].x,vertex_fall[6].y);//*0.55f);

			parent.triangle(vertex_fall[3].x,vertex_fall[3].y,
					vertex_fall[4].x,vertex_fall[4].y,
					vertex_fall[7].x,vertex_fall[7].y);//*0.55f);

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[5].x,vertex_fall[5].y,
					vertex_fall[8].x,vertex_fall[8].y);

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[5].x,vertex_fall[5].y,
					vertex_fall[9].x,vertex_fall[9].y);
			parent.popMatrix();


		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
