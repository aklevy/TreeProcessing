import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
	

import java.awt.Color;


public class SpringLeaf extends Leaf{

	// Random to choose color
	int icolor;
	
	// Color for leaves
	public int [] rSpring = {34,27,9,86,22,127};
	public int [] gSpring = {120,79,106,130,184,221};
	public int [] bSpring = {15,8,9,3,78,76};


	/**************************************
	 * * CONSTRUCTOR
	 */
	SpringLeaf(PApplet p, PVector l,int icol){
		super(p,"spring",l);

		icolor = icol;
		leafWeight = icolor*0.7f;

		// initializes color
		alpha = 255f;
		rgb = parent.color(rSpring[icolor],gSpring[icolor],bSpring[icolor]);

		vertex = new PVector[] {new PVector(0,0), 
								new PVector(4,4)};
		
		// decides whether it is a fruit or leaf
		if(icolor > 4){	
			fruit = true; 
			fruitrgb = parent.color(255,(int)parent.random(80,150),(int)parent.random(130,190));
			fruitSize = 2f;
		}
		
	}

	/**************************************
	 * * MORPHING
	 **************************************/

	public Leaf nextL(){
		return nextLeaf;//return new SummerLeaf(parent,loc,icolor,this.isFruit() ? 1 : 0);
	}

	public void changeSeason(){
		//change to summer
		nextLeaf =  new SummerLeaf(parent,loc,icolor,fruit? 1 : 0,parent.random(0,2*PConstants.PI));
	}

	
	public boolean morphing(float k){
		k *= growth;
	//	morph = true;

		if(k>=maxMorph){
			return true;
		}
		if(k>=30){
			
			colorMorph 	= true;
			alpha 		=  (float) k / (maxMorph * 2f) * (255) ;

		}
		else{
			alpha 		= 1- (float) k / (maxMorph * 2f) * (255) ;
		}
		if(fruit){
			
			fruitSize 	= 2f + (float) k / maxMorph * (6f - 2f);
			
			displayFruit();
			return false;
		}
		else { 
			for (int i=0;i<2;i++){
				
				vertex[i].x = (float) (1- k/maxMorph) * vertex[i].x 
							+ (float) k/maxMorph * nextL().getVertex(i).x;
				vertex[i].y = (float) (1- k/maxMorph) * vertex[i].y 
							+ (float) k/maxMorph * nextL().getVertex(i).y;
			}
		}
		return false;

	}
	/**************************************
	 * * DISPLAY
	 */

	public void displayFruit(){
		parent.pushMatrix();
		parent.pushStyle();
		
		parent.translate(loc.x,loc.y);
		
		colorMorphing();
		
		parent.ellipse(0, 0, fruitSize, fruitSize);
		
		parent.popStyle();
		parent.popMatrix();
	}

	public void display() {
		parent.pushStyle();
		parent.strokeWeight(icolor*0.4f);

		if(!fruit ){ //leaf
			if(!colorMorph)
				toColor();
			else
				colorMorphing();

			parent.pushMatrix();
			
			parent.translate(loc.x,loc.y);
			parent.rotate	(PConstants.PI/(float)(icolor+1));
			
			parent.rect(vertex[0].x, vertex[0].y,
						vertex[1].x, vertex[1].y);
			
			parent.popMatrix();

		}
		else{
			displayFruit();
		}
		
		parent.popStyle();
	}

}