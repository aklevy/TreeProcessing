import processing.core.PConstants;
import processing.core.PVector;
import processing.core.PApplet;

import java.awt.Color;
//import java.lang.Math;


public class FallLeaf extends Leaf{

	// Random to choose color
	int icolor;

	// Angle for the leaf to grow, length and weight of this leaf
	float angle;//, length, weight;

	// Time to morph or not
	boolean noleaf 	= false;

	// Time  and height to make the leaf fall from
	boolean fall 	= false;
	float fallHeight = 10;
	float deviation = 0;
	
	// Color for leaves
	public int [] rFall = {255,174, 216, 212, 163, 	237};
	public int [] gFall = {104,112, 48,  133, 42, 	0};
	public int [] bFall = {4,	10,	 0,  28,  42, 	0};


	/**************************************
	 * * CONSTRUCTOR
	 */
	FallLeaf(PApplet p, PVector l,int icol,float ang){
		super(p,"fall",l);

		icolor = icol;
		angle = ang;
		
		// Leaf
		leafWeight = icolor*0.4f;
		leafLength = icol/0.7f;

		// initializes color
		rgb = parent.color (rFall[icolor], gFall[icolor], bFall[icolor]);

		// get shape of the leaf
		vertex = new PVector[]{
					new PVector (0f, 	0f), //0
					new PVector (1f, 	0f), //1
					new PVector (1/2f,	-0.35f), //2

					new PVector (0.5f, 	0), //3
					new PVector (0, 		-0.5f), //4
					new PVector (-1f, 	-1*0.35f), //5
					};
		
		for (int i = 1;i<5;i++){
			vertex[i].mult(leafLength/2);
		}

	}


	
	/**************************************
	 * * MORPHING
	 **************************************/
	public Leaf nextL(){
		return nextLeaf;//return new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}
	public void changeSeason(){
		//change to winter
		nextLeaf = new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}


	public boolean morphing(float k){
		if(k>maxMorph){
			return true;
		}
		
		if (!noleaf){
			
		k *= growth;
		//fall = true;
		morph = true;
		

			if (!fall){
				//if( (int)parent.random(0,100) >= 92){
				fall = (int)parent.random(0,100) >= 98 ? true : false;
			}


		}
		return false;
	}


	private void fallAnim(){
		if((fallHeight + loc.y) >= parent.height){
			fall = false;
			noleaf = true;
			//noleaf = true;
		}
		else{

			deviation += PConstants.PI/80;
			
			float oscill = PApplet.cos(deviation);
			parent.translate(fallHeight*icolor/3*oscill, fallHeight);

			//parent.translate(fallHeight*deviation*deviation*deviation, fallHeight);
			fallHeight += (parent.random(8,13))/10 + parent.random(0,5);// + fallHeight/15)/10;
		}
	}

	/**************************************
	 * * DISPLAY
	 **************************************/
	public void display() {
		
		parent.pushStyle();
		parent.strokeWeight(leafWeight);
		toColor();
		
		if(noleaf){

		}
		else{ 
			if (!morph){
				angle += (2*PConstants.PI-angle)*PConstants.PI/100;

				parent.pushMatrix();
				
				//parent.rotate(PConstants.PI/(float)(icolor+1));
				/*parent.rect		(loc.x + leafLength/2, loc.y, leafLength, leafLength);
				*/
				parent.translate(loc.x - leafLength/2, loc.y);
				parent.rotate	(angle);

				parent.triangle(vertex[0].x, vertex[0].y,
								vertex[1].x, vertex[1].y,
								vertex[2].x, vertex[2].y);

				parent.triangle(vertex[3].x, vertex[3].y,
								vertex[4].x, vertex[4].y,
								vertex[5].x, vertex[5].y);

				parent.popMatrix();
			}
			else{
				parent.pushMatrix();
				if(fall){
					fallAnim();
				}
					
				parent.pushMatrix();
					parent.translate	(loc.x, loc.y);
					parent.rotate		(PConstants.PI/(float)(icolor+1));

					parent.triangle(vertex[0].x, vertex[0].y,
								vertex[1].x, vertex[1].y,
								vertex[2].x, vertex[2].y);
				
					parent.popMatrix();

				
					parent.pushMatrix();
				
					parent.translate	(loc.x - leafLength/2, loc.y);
					parent.rotate(angle);

					parent.triangle(vertex[3].x, vertex[3].y,
									vertex[4].x, vertex[4].y,
									vertex[5].x, vertex[5].y);//*0.55f);

					parent.popMatrix();
					parent.popMatrix();
				
				
			}
		}
		parent.popStyle();
	}

}
