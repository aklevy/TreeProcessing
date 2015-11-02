import java.awt.Color;
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

	// Time to morph or not
	boolean morph;

	// Angle for the next leaf (fall leaf)
	float angle;

	// Length of the leaf
	float length;

	// Leaf and fruit color
	public int [] rSummer = {34,27,9,86,22,127};
	public int [] gSummer = {120,79,106,130,184,221};
	public int [] bSummer = {15,8,9,3,78,76};

	// Vertex
	PVector [] vertex = {new PVector(0,0), 
			new PVector(5,5)//1
	};
	PVector [] vertex_fall ={ 
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

	/**************************************
	 * * CONSTRUCTOR
	 */

	SummerLeaf(PApplet p, PVector l,int icol,int f){
		super(p,"summer",l);
		parent = p;
		loc = l;
		
		// initializes color
		rgb = new Color(rSummer[icolor],gSummer[icolor],bSummer[icolor]); 

		icolor = icol;
		angle = parent.random(0,2*PConstants.PI);
		length = icolor;


		fruit = false;

		// decides whether it is a fruit or leaf
		if(f == 1){
			fruit = true;
		} 

		vertex[1].mult(length);
	}
	/**************************************
	 * * COLOR
	 */
	public  Color getRgb (){
		return rgb;
	}
	public void color(){
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
	}



	public PVector getVertex(int index){
		return vertex[index];
	}

	/**************************************
	 * * MORPHING
	 */

	public Leaf nextL(){
		return new FallLeaf(parent,loc,icolor,angle);
	}

	public void changeSeason(){
		//change to Fall
		nextLeaf = new FallLeaf(parent,loc,icolor,angle);
		System.out.println(nextLeaf.toString());

	}
	public void colorMorphing(){
		float step=100f;
		Color rgb_end = nextLeaf.getRgb();
		float hsb_start[] = new float[3];
		float hsb_end[] = new float[3];

		Color.RGBtoHSB(rgb.getRed(),rgb.getGreen(),rgb.getBlue(),hsb_start);
		//	System.out.println(nextLeaf.toString());
		/*Color.RGBtoHSB(rgb_end.getRed(),rgb_end.getGreen(),rgb_end.getBlue(),hsb_end);

		// Change the hue to make a color gradient
		hsb_start[0] += Math.abs(hsb_end[0]-hsb_start[0])/step;
		 */
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());


	}

	public boolean morphing(int k){
		//colMorph = k;
		float max = 100f;
		if(k>=max){
			return true;
		}
		else { 
			//System.out.println(vertex[i]);
			//length -= k*length/max;
			vertex[0].x = (float)(1- k/max)*vertex[0].x 
					+ (float)k/max*(length/2)*2;
			vertex[0].y = (float)(1- k/max)*vertex[0].y 
					+ (float)k/max*2;
			vertex[1].x = (float)(1- k/max)*vertex[1].x 
					+ (float)k/max*(length)*2;
			vertex[1].y = (float)(1- k/max)*vertex[1].y 
					+ (float)k/max*(length)*2;

			if(k>=max/10){
				morph = true;
				for (int i=0;i<10;i++){
					vertex_fall[i].x = (float)k/max*nextLeaf.getVertex(i).x*length;
					vertex_fall[i].y = (float)k/max*nextLeaf.getVertex(i).y*length;
				}
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
		parent.strokeWeight(icolor*0.7f);
		if(!morph){
			if(!fruit ){
				color();
				parent.fill(255);
				parent.pushMatrix();
				parent.translate(loc.x,loc.y);
				parent.rotate(parent.PI/(float)(icolor+1));
				parent.rect(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y, 4);
				parent.popMatrix();

			}
			else{
				displayFruit(6f);
				/*parent.triangle(loc.x-0.7f,loc.y,
				loc.x+0.7f,loc.y,
				loc.x,loc.y+0.7f);*/
			}
		}
		else{
			/*parent.stroke((int)parent.random(230,255),
					(int)parent.random(80,110),
					(int)parent.random(0,15));*/
			parent.fill(255);
			colorMorphing();
			/*parent.fill((int)parent.random(230,255),
					(int)parent.random(80,110),
					(int)parent.random(0,15));*/
			parent.pushMatrix();
			parent.translate(loc.x, loc.y);
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y);
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
