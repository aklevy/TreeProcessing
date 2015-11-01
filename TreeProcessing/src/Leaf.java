import java.lang.String;
import processing.core.PConstants;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Leaf {
	PApplet parent;
	String type;
	PVector loc;
	int imageNb = 30;

	Leaf(PApplet p,String t,PVector l) {
		parent = p;
		type = t;
		loc = l.copy();
	}

	public abstract void color(); // Chooses the color
	

	public abstract void display(); // Displays the right form
	
	public abstract void fallAnim(int h);

	public void morphing(){
		
	}
	
	public Leaf changeSeason(PVector l){
		if (type.equals("spring")){ //change to summer
			return new SummerLeaf(parent,l,(int)parent.random(0,6));
		}
		else if (type.equals("summer")){ //change to fall
			return new FallLeaf(parent,l,(int)parent.random(0,6),parent.random(0,2*parent.PI));
		}
		else if (type.equals("fall")){ //change to christmas
			return new PsycheLeaf(parent,l);
		}
		else if (type.equals("psychedelic")){ //change to christmas
			return new SpringLeaf(parent,l,(int)parent.random(0,6));
		}
		return this;
	}
	public String toString(){
		return type + " leaf ";
	}
}
