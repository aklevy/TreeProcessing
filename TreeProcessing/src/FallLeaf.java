import processing.core.PApplet;
import processing.core.PVector;
import processing.core.*;


public class FallLeaf extends Leaf{
	PApplet parent;

	int icolor;// = (int)parent.random(0,6);

	int [] rFall = {255,174,216,212,163,237};
	int [] gFall = {104,112,48,133,42,0};
	int [] bFall = {4,10,0,28,42,0};

	FallLeaf(PApplet p, PVector l,int icol){
		super("fall",l);
		parent = p;
		loc = l;
		icolor = icol;

	}
	public void fallAnim(int h){
	/*	System.out.println(loc.y);
		while(loc.y<h){
			long time = System.currentTimeMillis();
			loc.y +=20f;
			//delay(100);
			display();
			if(System.currentTimeMillis() - time <= 100000){
				
			}
		}*/
	}
	
	public void color(){
		parent.stroke(rFall[icolor],gFall[icolor],bFall[icolor]);

	}
	
	public void display() {
		//parent.noStroke();
		parent.strokeWeight(icolor/2);

		this.color();
		//	parent.fill(50,100);
		parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y+1);
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
