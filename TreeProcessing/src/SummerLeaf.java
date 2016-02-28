import java.awt.Color;
import java.lang.Math;

import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;


public class SummerLeaf extends Leaf{

	// Random to choose color
	int icolor;



	// Time to morph or not, to fill morph color or not
	boolean triangle = false;
	float morphAngle = 0f;
	
	// Angle for the next leaf (fall leaf) 
	float angle;

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

	PVector [] vertex_fall;

	/**************************************
	 * * CONSTRUCTOR
	 */

	SummerLeaf(PApplet p, PVector l,int icol,int f,float a){
		super(p,"summer",l);

		// initializes color
		rgb = parent.color(rSummer[icolor],gSummer[icolor],bSummer[icolor]);
		alpha = 150f;
		
		// decides whether it is a fruit or leaf and intializes color
		if(f == 1){
			fruit = true;
			fruitSize = 6f;
			fruitrgb = parent.color((int)parent.random(200,255),(int)parent.random(20,80),0);
		} 
		else{
			fruit = false;
		}
		
		icolor 	= icol;
		angle	= a;
		
		// leaf
		leafLength 	= icolor;
		leafWeight 	= icolor*0.7f;

		
		morphAngle = PConstants.PI/(float)(icolor+1);
		
		vertex = new PVector[]{
				new PVector(0,0), 
				new PVector(5,5)
		};
		
		vertex[1].mult(leafLength);

		// initialize the vertex array for next leaf
		vertex_fall = new PVector[]{ 
				new PVector(0,0), 
				new PVector(1,0),	//1
				new PVector(0,0),	//2
				new PVector(0,0),	//3
				new PVector(0,0),	//4
				new PVector(0,0),	//5
			};

	}

	/**************************************
	 * * MORPHING
	 **************************************/

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

	public boolean morphing(float k){
		k *= growth;

		if(k>maxMorph){
			return true;
		}
		else { 
			// Change gradually the stroke weight
			leafWeight = (float)(1-k/maxMorph) *(0.7f*icolor) + (float)k/maxMorph*(0.4f*icolor) ;


			// Morph rectangle first
			vertex[0].x = (float) (1- k/maxMorph) * vertex[0].x 
						+ (float) k/maxMorph * leafLength / 2;
			vertex[0].y = (float) (1- k/maxMorph) * vertex[0].y ;
			
			vertex[1].x = (float) (1- k/maxMorph) * vertex[1].x 
						+ (float) k/maxMorph * (leafLength);
			vertex[1].y = (float) (1- k/maxMorph) * vertex[1].y 
						+ (float) k/maxMorph * (leafLength);


			if(k>maxMorph/40){
				
				if (k > (maxMorph/100) && !fall && (int) parent.random(0,100) > 90){
					fall = true; // fruits fall

					colorMorph = true; //fill leaf
				}

				if (colorMorph)
					alpha +=  maxMorph * (1-1/40);
				
				if(!morph){
			
					morph = true; // leaf morphing to fall leaf
			
					
				}
				
				//morphAngle 	*= (1 - k/maxMorph);
				angle 		+= k/10 * PConstants.PI/100;
				
				
				for (int i=0;i<2;i++){
					
					vertex_fall[i] = vertex[i];
				}
				
				if(fall){
					triangle = true;
					
					for (int i=2;i<5;i++){
						vertex_fall[i].x = (float) nextL().getVertex(i).x * 2 * k/maxMorph;
						vertex_fall[i].y = (float) nextL().getVertex(i).y * 2 * k/maxMorph;
						}
				}
			}
			if(k==maxMorph){
				// do nothing : end of morphing
			}

		}
		return false;
	}


	/**************************************
	 * * DISPLAY
	 */
	public void displayFruit(){
		parent.fill(fruitrgb);
		parent.stroke(fruitrgb);

		parent.ellipse(loc.x,loc.y,fruitSize,fruitSize);
	}

	public void display() {
		
		if(fruit){
			if (fallHeight != -1){
				parent.pushStyle();
				parent.strokeWeight(leafWeight);

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
			parent.pushMatrix();

			parent.strokeWeight(leafWeight);
			toColor();
			
			parent.translate(loc.x,loc.y);
			parent.rotate	(morphAngle);
			
			parent.rect(vertex[0].x, vertex[0].y,
						vertex[1].x, vertex[1].y);
			
			parent.popMatrix();
			parent.popStyle();
		}
		else{

			parent.pushStyle();
			parent.pushMatrix();

			colorMorphing();

			parent.translate(loc.x,loc.y);
			parent.rotate	(morphAngle);
			if (angle < 20) 
				parent.rect(vertex_fall[0].x, vertex_fall[0].y,
						vertex_fall[1].x, vertex_fall[1].y);
			
			parent.popMatrix();
			
			if (triangle){
				parent.pushMatrix();
				
				parent.translate(loc.x-leafLength/2,loc.y);

				parent.translate(vertex[0].x,vertex[0].y);
				//parent.rotate(angle);

				parent.triangle(vertex_fall[0].x, vertex_fall[0].y,
								vertex_fall[1].x, vertex_fall[1].y,
								vertex_fall[2].x, vertex_fall[2].y);

				parent.triangle(vertex_fall[3].x, vertex_fall[3].y,
								vertex_fall[4].x, vertex_fall[4].y,
								vertex_fall[5].x, vertex_fall[5].y);

				parent.popMatrix();
			}
			parent.popStyle();

		}
	}
}


