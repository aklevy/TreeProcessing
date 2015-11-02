import java.awt.Color;

import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class PsycheLeaf extends Leaf{
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

	// vertex
	PVector [] vertex = {new PVector(-1,0),
			new PVector(1,0),
			new PVector(0,1),
	};

	/**************************************
	 * * CONSTRUCTOR
	 */
	PsycheLeaf(PApplet p, PVector l,int icol){
		super(p,"psychedelic",l);
		parent = p;
		loc = l;
		icolor = icol;

		// initializes color
		rgb = new Color((int)parent.random(0,255),(int)parent.random(0,255),(int)parent.random(0,255));

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
		return new SpringLeaf(parent,loc,icolor);
	}

	public void changeSeason(){
		//change to spring
		nextLeaf = new SpringLeaf(parent,loc,(int)parent.random(0,6));
	}

	public boolean morphing(int k){
		morph = true;
		float max = 100f;
		if(k>=max){
			return true;
		}

		if(nextLeaf.isFruit()){
			fruit = true;
			//color pink->red
			/*parent.stroke((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);
			parent.fill((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);*/
			displayFruit((k/10f));
			return false;
		}
		else { 
			for (int i=0;i<2;i++){
				vertex[i].x = (float)(1- k/max)*vertex[i].x 
						+ (float)k/max*nextLeaf.getVertex(i).x;
				vertex[i].y = (float)(1- k/max)*vertex[i].y 
						+ (float)k/max*nextLeaf.getVertex(i).y;
			}
		}
		return false;
	}

	/**************************************
	 * * DISPLAY
	 */
	public void displayFruit(float size){
		parent.pushMatrix();
		parent.translate(loc.x,loc.y);
		parent.fill(255,(int)parent.random(80,150),(int)parent.random(130,190));
		parent.stroke(255,(int)parent.random(80,150),(int)parent.random(130,190));
		parent.ellipse(0,0,size,size);
		parent.popMatrix();
	}

	public void display() {
		//parent.noStroke();		
		parent.strokeWeight(2);

		this.color();
		//	parent.fill(50,100);
		if(!morph){
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,
					vertex[2].x,vertex[2].y);
			parent.popMatrix();
		}
		else{
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,4);
			parent.popMatrix();

		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
