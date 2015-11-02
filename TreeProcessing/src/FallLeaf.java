import processing.core.*;

import java.lang.Math;


public class FallLeaf extends Leaf{
	PApplet parent;
	Leaf nextLeaf;
	boolean morph;
	int icolor;// = (int)parent.random(0,6);
	float angle;
	float length;

	// Color for leaves
	int [] rFall = {255,174,216,212,163,237};
	int [] gFall = {104,112,48,133,42,0};
	int [] bFall = {4,10,0,28,42,0};

	// Vertex 
	PVector[] vertex= {new PVector(0,0), //0
			new PVector(1,0), //1
			new PVector(1/2,-0.35f), //2

			new PVector(0.5f,0), //3
			new PVector(0,-0.5f), //4
			new PVector(0,1), //5
			new PVector(-1,-1*0.35f), //6
			new PVector(1,-1*0.35f), //7
			new PVector(-0.35f,0.2f), //8
			new PVector(0.35f,0.2f) //9
	};

	FallLeaf(PApplet p, PVector l,int icol,float ang){
		super(p,"fall",l);
		parent = p;
		loc = l;
		icolor = icol;
		angle = ang;

		length = icolor;
		vertex[1].mult(length);
		vertex[2].mult(length);
		vertex[5].mult(length);
		vertex[6].mult(length);
		vertex[7].mult(length);
		vertex[8].mult(length);
		vertex[9].mult(length);

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
	public PVector getVertex(int index){
		return vertex[index];
	}
	public boolean morphing(int k){

		float max = 80f;
		if(k>=max){
			return true;
		}

		else { 
			if(k>=35){
				parent.stroke((int)parent.random(0,255),
						(int)parent.random(0,255),
						(int)parent.random(0,255));
				morph = true;
			}
			for (int i=0;i<3;i++){
				System.out.println(vertex[i]);
				length -= k*length/max;
				vertex[i].x = (float)(1- k/max)*vertex[i].x 
						+ (float)k/max*nextLeaf.getVertex(i).x;
				vertex[i].y = (float)(1- k/max)*vertex[i].y 
						+ (float)k/max*nextLeaf.getVertex(i).y;
			}
			if(k<=35){
				for (int i=3;i<10;i++){
					vertex[i].x = (float)(1- k/max)*vertex[i].x;
					vertex[i].y = (float)(1- k/max)*vertex[i].y;
				}
			}
		}
		return false;
	}

	public Leaf nextL(){
		return new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}
	public void changeSeason(){
		//change to christmas
		nextLeaf = new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}

	public void display() {
		//parent.noStroke();
		parent.strokeWeight(10*length/icolor);//icolor*0.7f);

		this.color();
		//	parent.fill(50,100);
		/*parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y-1);*/
		if (!morph){
			float length = icolor;
			parent.pushMatrix();
			parent.rect(loc.x+length/2,loc.y,length,length);
			parent.translate(loc.x-length/2,loc.y);
			parent.rotate(angle);

			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,
					vertex[2].x,vertex[2].y);//-3

			parent.triangle(vertex[3].x,vertex[3].y,
					vertex[4].x,vertex[4].y,
					vertex[6].x,vertex[6].y);//*0.55f);

			parent.triangle(vertex[3].x,vertex[3].y,
					vertex[4].x,vertex[4].y,
					vertex[7].x,vertex[7].y);//*0.55f);

			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[5].x,vertex[5].y,
					vertex[8].x,vertex[8].y);

			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[5].x,vertex[5].y,
					vertex[9].x,vertex[9].y);
			parent.popMatrix();
		}
		else{

			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(parent.PI/(float)(icolor+1));

			parent.triangle(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,
					vertex[2].x,vertex[2].y);//-3
			parent.popMatrix();
		}
		/*parent.triangle(loc.x+6.5f,loc.y,
						loc.x,loc.y+0.5f,
						loc.x+4,loc.y-0.5f);*/
		//polygon(loc.x,loc.y-radius*1.3f,radius,6);


		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
