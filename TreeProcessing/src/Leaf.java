import java.lang.String;

import processing.core.PVector;

public abstract class Leaf {

	String type;
	PVector loc;

	Leaf(String t,PVector l) {
		type = t;
		loc = l.copy();
	}

	public abstract void color(); // Chooses the color
	

	public abstract void display(); // Displays the right form
	
	public abstract void fallAnim(int h);

	public String toString(){
		return type + " leaf ";
	}
}
