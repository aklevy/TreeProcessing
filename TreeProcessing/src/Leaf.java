import java.lang.String;
import processing.core.PApplet;
import processing.core.PVector;
import java.awt.Color;

public abstract class Leaf {
	PApplet parent;
	String type;
	PVector loc;
	PVector [] vertex; 
	boolean fruit = false;
	Leaf nextLeaf;
	Color rgb;
	float growth = 1f;
	
	Leaf(PApplet p,String t,PVector l) {
		parent = p;
		type = t;
		loc = l.copy();
		
	}
	public abstract Color getRgb ();
	
	public abstract void color(); // Chooses the color

	public abstract PVector getVertex(int index);

	public abstract void display(); // Displays the right form

	public  boolean isFruit(){
		return fruit;
	}
	public abstract boolean morphing(float k);

	public abstract Leaf nextL();
	
	public abstract void changeSeason();
	
	public void setGrowingTime(float sliderValue){
		growth = 1.f/sliderValue;
	}
	public String toString(){
		return type + " leaf at ("+loc.x+","+loc.y+") with the color "+getRgb().toString();
	}
}
