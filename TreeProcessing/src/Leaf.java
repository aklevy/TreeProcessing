import java.lang.String;
import processing.core.PApplet;
import processing.core.PVector;
import java.awt.Color;

public abstract class Leaf {
	
	// Processing applet containing method draw and setup
	PApplet parent;
	
	// Leaf type (=season)
	String type;
	
	// Branch end to be fixed to 
	PVector loc;
	
	// Color information
	int rgb 		= 0;	// leaf color
	int fruitrgb 	= 0;	// fruit color
	float alpha 	= 255f; // transparency of the leaf
	
	// Leaf aspect
	PVector [] vertex; 
	float leafWeight 	= 0f;
	float leafLength 	= 0f;
	
	// Fruit aspect
	boolean fruit = false;	// if fruit : true
	float fruitSize		= 0f;
	
	// Growth speed
	float growth 	= 1f;
	
	// Morphing 
	float maxMorph = 600f; 			// morphing max count
	boolean morph = false; 		// Boolean for geometrical form morphing
	boolean colorMorph = false; // Boolean for color gradient
	Leaf nextLeaf;				// Next leaf to morph into when changing season
	
	
	Leaf(PApplet p,String t,PVector l) {
		parent = p;
		type = t;
		loc = l.copy();
		
	}
	
	public PVector getVertex(int index){
		return vertex[index];
	}

	public  boolean isFruit(){
		return fruit;
	}
	/**************************************
	 * * COLOR METHOD
	 *************************************/
	
	public int getRgb (){
		if(fruit){
			return fruitrgb;
		}
		else
			return rgb;
	}
	
	public void toColor(){
		parent.fill		(rgb, alpha);
		parent.stroke	(rgb);
	}
	/* *************************************/


	public abstract void display(); // Displays the right form

	/**************************************
	 * * MORPHING
	**************************************/
	
	public abstract boolean morphing(float k);

	public abstract Leaf nextL();
	
	public void colorMorphing(){
		float step=35f;
		int rgb_start;
		if (fruit)
			rgb_start = fruitrgb;
		else
			rgb_start = rgb;
		
		int rgb_end = nextL().getRgb();
		
		int r = (int) (parent.red(rgb_start) + (parent.red(rgb_end) - parent.red(rgb_start))/step);
		int g = (int) (parent.green(rgb_start) + (parent.green(rgb_end) - parent.green(rgb_start))/step);
		int b = (int) (parent.blue(rgb_start) + (parent.blue(rgb_end) - parent.blue(rgb_start))/step);

		rgb_start = parent.color(r,g,b);
		
		if (fruit)
			fruitrgb = rgb_start;
		else
			rgb = rgb_start;
		
		parent.strokeWeight(leafWeight);
		parent.stroke(rgb_start);
		
		parent.fill(rgb_start,alpha);
		
	}
	public abstract void changeSeason();
	
	/* *************************************/

	public void setGrowingTime(float sliderValue){
		growth = 1.f/sliderValue;
	}
	
	public String toString(){
		return type + " leaf at ("+loc.x+","+loc.y+") with the color "+parent.red(rgb)+" "+parent.green(rgb)+ " "+parent.blue(rgb);
	}
}
