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
	Color rgb,fruitrgb;

	// Random to choose color
	int icolor;

	// Next leaf to morph into when changing season
	Leaf nextLeaf;

	// fruit Size
	float size;

	// morphing time
	float max = 300f;

	// Time to morph or not, to fill morph color or not
	boolean morph = false;
	boolean morphColFill = false;
	boolean triangle = false;
	float morphAngle = 0f;
	
	// Angle for the next leaf (fall leaf) 
	float angle;

	// Length and weight of this leaf
	float length, weight;

	// transparency of the fill color
	float alpha = 150f;

	// morphing step
	float mstepx[] = {0,0,0,0,0,0,0,0};
	float mstepy[] = {0,0,0,0,0,0,0,0};

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

		// decides whether it is a fruit or leaf and intializes color
		if(f == 1){
			fruit = true;
			size = 6f;
			fruitrgb = new Color((int)parent.random(200,255),(int)parent.random(20,80),0);
		} 
		else{
			fruit = false;
		}
		icolor 	= icol;
		angle	= a;
		length 	= icolor;
		weight 	= icolor*0.7f;

		morphAngle = PConstants.PI/(float)(icolor+1);
		
		vertex[1].mult(length);

		// initialize the vertex array for next leaf
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
	public Color getRgb (){
		if(fruit){
			return fruitrgb;
		}
		else
			return rgb;
	}
	public void color(){
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		parent.fill(rgb.getRed(),rgb.getGreen(),rgb.getBlue(),alpha);
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
		nextLeaf = new FallLeaf(parent,loc,(int)parent.random(0,6),angle);
	}
	private void fallAnim(){ //for fruits
		parent.translate(0, (float)(icolor/3)*fallHeight);
		fallHeight += (parent.random(5,10) + fallHeight/20);
		
		if (fallHeight > parent.displayHeight){
			fall = false;
			fallHeight = -1;
		}
	}

	public void colorMorphing(){
		float step=15f;
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
		parent.fill(rgb.getRed(),rgb.getGreen(),rgb.getBlue(),alpha);
		if(morphColFill){
			alpha +=  max * (1-1/40) ;
			parent.fill(rgb.getRed(),rgb.getGreen(),rgb.getBlue(),alpha);
		}
	}

	public boolean morphing(float k){
		k *= growth;

		if(k>max){
			return true;
		}
		else { 
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


			if(k>max/40){
				if (k>max/50 && !fall && (int)parent.random(0,100)>90){
					fall = true; // fruits fall

					morphColFill = true; //fill leaf
				}
				//}


				if(!morph){
					morph = true; // leaf morphing to fall leaf
					for (int i=2;i<10;i++){
						float dx = PApplet.abs(nextLeaf.getVertex(i).x - vertex_fall[i].x)*95;
						float dy =  PApplet.abs(nextLeaf.getVertex(i).y - vertex_fall[i].y)*95;

						if(dx == 0)
							dx = 300;
						//System.out.println("dx : "+dx);

						float tx = (float) ((k-max/40) / dx) - 1; 
						//System.out.println("tx : "+(k-max*2/7) / dx);
						if(dy == 0)
							dy = 300;
						float ty = (float) ((k-max/40) / dy) - 1; 

						mstepx[i-2] = tx*tx*tx*tx*tx;
						mstepy[i-2] = ty*ty*ty*ty*ty;
					}
					
				}
				
				morphAngle *= (1 - k/max);
				angle += k/10*PConstants.PI/100;
				for (int i=0;i<2;i++){
					vertex_fall[i] = vertex[i];
				}
				if(fall){
					triangle = true;
					for (int i=2;i<10;i++){
						//System.out.println((k-max/4)/(3*max/4));//(k-max/4)/(3*max/4)
						//vertex_fall[i].x = (float)(k-(max*2/5))/(max*3/5)*nextLeaf.getVertex(i).x;//*length/2;
						//vertex_fall[i].y = (float)(k-(max*2/5))/(max*3/5)*nextLeaf.getVertex(i).y;//*length/2;
						//System.out.println("nL "+nextLeaf.getVertex(i).x);
						//System.out.println("vF  "+vertex_fall[i].x);

						vertex_fall[i].x += (float) nextLeaf.getVertex(i).x * (mstepx[i-2] +1);
						vertex_fall[i].y += (float) nextLeaf.getVertex(i).y * (mstepy[i-2] +1);
						//	System.out.println(vertex_fall[i].x);
						//	System.out.println(vertex_fall[i].y);

						/*
					vertex_fall[i].x += (float)1/max * (float)nextLeaf.getVertex(i).x;//*length/2;
					vertex_fall[i].y += (float)1/max * (float)nextLeaf.getVertex(i).y;
						 */}
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
	public void displayFruit(){
		//parent.fill(icolor*10+190,icolor*10+10,0);
		parent.fill(fruitrgb.getRed(),fruitrgb.getGreen(),fruitrgb.getBlue());
		parent.stroke(fruitrgb.getRed()+50,fruitrgb.getGreen(),fruitrgb.getBlue());
		//parent.stroke(icolor*10+190+50,icolor*10+10,0);
		parent.ellipse(loc.x,loc.y,size,size);
	}

	public void display() {
		//parent.noStroke();
		//	parent.fill(50,100);
		//parent.strokeWeight(icolor*vertex[0].x/10f);
		if(fruit){
			if (fallHeight != -1){
				parent.pushStyle();
				parent.strokeWeight(weight);

				parent.pushMatrix();
				if (fall){
					fallAnim();
				}
				displayFruit();
				parent.popMatrix();
				parent.popStyle();
			}
		}
		else if(!morph){
			parent.pushStyle();
			parent.strokeWeight(weight);

			color();
			parent.pushMatrix();
			//parent.noFill();
			//parent.fill(255);
			parent.translate(loc.x,loc.y);
			parent.rotate(morphAngle);
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y);//, 4);
			parent.popMatrix();
			parent.popStyle();
		}
		else{

			parent.pushStyle();
			//System.out.println("angle: "+angle);
			colorMorphing();

			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(morphAngle);

			//parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.rect(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[1].x,vertex_fall[1].y);
			parent.popMatrix();
			if (triangle){
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
				parent.popStyle();
			}
		}
	}
	//parent.ellipse(loc.x,loc.y,2,2);   
}


