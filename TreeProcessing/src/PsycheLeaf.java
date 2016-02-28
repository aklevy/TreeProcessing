import java.awt.Color;

import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class PsycheLeaf extends Leaf{

	// Random to choose color
	int icolor;


	/**************************************
	 * * CONSTRUCTOR
	 */
	PsycheLeaf(PApplet p, PVector l,int icol){
		super(p,"psychedelic",l);
		icolor 	= icol;

		vertex = new PVector[] { 
				new PVector (-1, 0),
				new PVector (1, 0),
				new PVector (0, 1),
			};
		
		// initializes color
		//rgb = new Color((int)parent.random(0,255),(int)parent.random(0,255),(int)parent.random(0,255));
		rgb = parent.color(0,(int)parent.random(100,200),0);

	}


	/**************************************
	 * * MORPHING
	**************************************/

	public Leaf nextL(){
		return nextLeaf;//return new SpringLeaf(parent,loc,icolor);
	}

	public void changeSeason(){
		//change to spring
		nextLeaf = new SpringLeaf(parent,loc,(int)parent.random(0,6));
		if(nextLeaf.isFruit()){
			fruit = true;
		}
	}
	
	public boolean morphing(float k){
		k *= growth;
		

		if(k>=maxMorph){
			return true;
		}
		if(k==maxMorph/5){
			
			morph = true;
			//colorMorph = true; //fill leaf
		}

		if(k>(maxMorph/2) && fruit){
			
			fruitSize = (float)(k/maxMorph)*2f;
			return false;
		}
		else { 
			if (morph){
				// Change gradually the stroke weight
				leafWeight = (float)(1 - (k/maxMorph)) ;// + (float)k/maxMorph*(0.4f*icolor) ;


				for (int i=0;i<2;i++){
					vertex[i].x = (float) k/maxMorph * nextL().getVertex(i).x;
					vertex[i].y = (float) k/maxMorph * nextL().getVertex(i).y;
				}
			}
		}
		return false;
	}

	/**************************************
	 * * DISPLAY
	 */
	public void displayFruit(){
		
		parent.pushStyle();
		parent.pushMatrix();
		
		parent.fill			(255, (int) parent.random(80,150), (int) parent.random(130, 190));
		parent.strokeWeight	(icolor*0.4f);
		parent.stroke		(255, (int) parent.random(80,150), (int) parent.random(130, 190));
		
		parent.translate(loc.x,loc.y);
		
		parent.ellipse	(0,0,fruitSize,fruitSize);
		
		parent.popMatrix();
		parent.popStyle();
	}

	public void display() {

		if(!morph){
			// do nothing
			}
		else{
			if(fruit){
				if (fruitSize != 0)
					displayFruit();
			}
			else{
				parent.pushStyle();
				parent.pushMatrix();

				colorMorphing();
				
				parent.translate(loc.x,loc.y);
				parent.rotate	(PConstants.PI/(float)(icolor+1));
				
				parent.rect(vertex[0].x, vertex[0].y,
							vertex[1].x, vertex[1].y);
				
				parent.popMatrix();
				parent.popStyle();
			}
		}
	}

}
