import processing.core.PApplet;
import processing.core.PVector;

public class PsycheLeaf extends Leaf{
	PApplet parent;
	Leaf nextLeaf;
	// vertex
	PVector [] vertex = {new PVector(loc.x-1,loc.y),
						new PVector(loc.x+1,loc.y),
						new PVector(loc.x,loc.y+1)
						};

	PsycheLeaf(PApplet p, PVector l){
		super(p,"psychedelic",l);
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
	public PVector getVertex(int index){
		return vertex[index];
	}
	public boolean morphing(int k){
		return true;
	}
	public Leaf nextL(){
		return new SpringLeaf(parent,loc,(int)parent.random(0,6));
	}
	public void changeSeason(){
		//change to spring
			nextLeaf = new SpringLeaf(parent,loc,(int)parent.random(0,6));
	}
	
	public void display() {
		//parent.noStroke();		
		parent.strokeWeight(2);

		this.color();
		//	parent.fill(50,100);
		parent.triangle(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y,
						vertex[2].x,vertex[2].y);
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
