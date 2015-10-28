import processing.core.PApplet;
import processing.core.PVector;

public class PsycheLeaf extends Leaf{
	PApplet parent;


	PsycheLeaf(PApplet p, PVector l){
		super("psychedelic",l);
		parent = p;
		loc = l;
	}
	
	public void fallAnim(int h){
	}
	public void color(){
		parent.stroke((int)parent.random(0,255),
				(int)parent.random(0,255),
				(int)parent.random(0,255));

	}
	
	public void display() {
		//parent.noStroke();		
		parent.strokeWeight(2);

		this.color();
		//	parent.fill(50,100);
		parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y+1);
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
