import processing.core.PApplet;
import processing.core.PVector;
import processing.core.*;
import java.lang.Math;


public class FallLeaf extends Leaf{
	PApplet parent;

	int icolor;// = (int)parent.random(0,6);
	float angle;
	int [] rFall = {255,174,216,212,163,237};
	int [] gFall = {104,112,48,133,42,0};
	int [] bFall = {4,10,0,28,42,0};

	FallLeaf(PApplet p, PVector l,int icol,float ang){
		super("fall",l);
		parent = p;
		loc = l;
		icolor = icol;
		angle = ang;

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
	public void polygon(float x, float y, float radius, int npoints) {
		  float angle = parent.TWO_PI / npoints;
		  parent.beginShape();
		  for (float a = 0; a < parent.TWO_PI; a += angle) {
		    float sx = x + (float)Math.cos(a) * radius;
		    float sy = y + (float)Math.sin(a) * radius;
		    parent.vertex(sx, sy);
		  }
		  parent.endShape(parent.CLOSE);
		}
	
	public void display() {
		//parent.noStroke();
		parent.strokeWeight(10);//icolor*0.7f);

		this.color();
		//	parent.fill(50,100);
		/*parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y-1);*/
		float length = icolor;
		parent.pushMatrix();
		parent.rect(loc.x+length/2,loc.y,length,length);
		parent.translate(loc.x-length/2,loc.y);
		parent.rotate(angle);

		/*parent.triangle(loc.x-1,loc.y-8,
						loc.x-1.5f,loc.y-6,
						loc.x-0.5f,loc.y-6);
		parent.triangle(loc.x-7.5f,loc.y-5,
						loc.x-6.5f,loc.y-3.5f,
						loc.x-6,loc.y-4);
		parent.triangle(loc.x+9.5f,loc.y-5,
						loc.x+8.5f,loc.y-3.5f,
						loc.x+8,loc.y-4);*/

		parent.triangle(0,0,
						length,0,
						length/2,-0.4f*length);//-3
		parent.triangle(0.5f,0,
						0,-0.5f,
						-length,-length*0.35f);//*0.55f);
		parent.triangle(0.5f,0,
						0,-0.5f,
						length,-length*0.35f);//*0.55f);
		parent.triangle(0,0,
						0,length,
						-0.35f*length,length*0.2f);
		parent.triangle(0,0,
						0,length,
						0.35f*length,length*0.2f);
		parent.popMatrix();

		/*parent.triangle(loc.x+6.5f,loc.y,
						loc.x,loc.y+0.5f,
						loc.x+4,loc.y-0.5f);*/
		//polygon(loc.x,loc.y-radius*1.3f,radius,6);
		
		
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
