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
	
	// Length and weight of the next leaf
	float weight;
	
	// Time to morph or not
	boolean morph = false;
	boolean morphColFill = false;
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
		return nextLeaf;//return new SpringLeaf(parent,loc,icolor);
	}

	public void changeSeason(){
		//change to spring
		nextLeaf = new SpringLeaf(parent,loc,(int)parent.random(0,6));
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
		morph = true;
		float max = 300f;
		if(k>=max){
			return true;
		}
		if(k==max/30){
			morphColFill = true; //fill leaf
		}
		
		if(nextLeaf.isFruit()){
			fruit = true;
			//color pink->red
			/*parent.stroke((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);
			parent.fill((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);*/
			displayFruit(k/20f);
			return false;
		}
		else { 
			// Change gradually the stroke weight
			weight = (float)(1-k/max) *2 + (float)k/max*(0.4f*icolor) ;
			colorMorphing();
			
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
		//parent.strokeWeight(2);

		this.color();
		//	parent.fill(50,100);
		if(!morph){
			parent.strokeWeight(2);

			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,
					vertex[2].x,vertex[2].y);
			parent.popMatrix();
		}
		else{
			parent.strokeWeight(weight);
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
