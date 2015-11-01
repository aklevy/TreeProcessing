import java.lang.String;
import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Leaf {
	PApplet parent;
	String type;
	PVector loc;
	PVector [] vertex; 
	boolean fruit = false;
	Leaf nextLeaf;
	
	Leaf(PApplet p,String t,PVector l) {
		parent = p;
		type = t;
		loc = l.copy();
		
	}
	
	public abstract void color(); // Chooses the color

	public abstract PVector getVertex(int index);

	public abstract void display(); // Displays the right form

	public abstract void fallAnim(int h);

	public  boolean isFruit(){
		return fruit;
	}
	public abstract boolean morphing(int k);

	public abstract Leaf nextL();
	
	public abstract void changeSeason();
	
	public String toString(){
		return type + " leaf at ("+loc.x+","+loc.y+")";
	}
}
