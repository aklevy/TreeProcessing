import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.Color;


public class SpringLeaf extends Leaf{
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

	float weight;
	// morphing time
	float max = 300f;
	
	// Time to morph or not
	boolean morph = false;

	// Color for leaves
	public int [] rSpring = {34,27,9,86,22,127};
	public int [] gSpring = {120,79,106,130,184,221};
	public int [] bSpring = {15,8,9,3,78,76};

	// Vertex
	PVector [] vertex = {new PVector(0,0), 
			new PVector(4,4)};

	/**************************************
	 * * CONSTRUCTOR
	 */
	SpringLeaf(PApplet p, PVector l,int icol){
		super(p,"spring",l);
		parent = p;
		loc = l;
		icolor = icol;
		fruit = false;
		weight = icolor*0.7f;

		// initializes color
		rgb = new Color(rSpring[icolor],gSpring[icolor],bSpring[icolor]);

		// decides whether it is a fruit or leaf
		if(icolor > 4){
			fruit = true; 
		}

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
		return nextLeaf;//return new SummerLeaf(parent,loc,icolor,this.isFruit() ? 1 : 0);
	}

	public void changeSeason(){
		//change to summer
		nextLeaf =  new SummerLeaf(parent,loc,icolor,this.isFruit() ? 1 : 0,parent.random(0,2*PConstants.PI));
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
		
	}
	public boolean morphing(float k){
		k *= growth;
		morph = true;
		
		if(k>=max){
			return true;
		}

		if(nextLeaf.isFruit()){
			fruit = true;
			if(k>=30){
				//color pink->red
				parent.stroke((k*6/max)*10+190,(k*6/max)*10+190,0);
				parent.fill((k*6/max)*10+190,(k*6/max)*10+190,0);
			}
			displayFruit((2-k/500f)*3);
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
		//	parent.fill(50,100);
		parent.strokeWeight(icolor*0.4f);

		if(!fruit ){ //leaf
			if(!morph)
				color();
			if(morph)
				colorMorphing();
			parent.fill(255);
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(PConstants.PI/(float)(icolor+1));
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y);//, 4);
			parent.popMatrix();

		}
		else{
			displayFruit(2f);
		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}