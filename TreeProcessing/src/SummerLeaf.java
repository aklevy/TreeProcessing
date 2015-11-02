import processing.core.PApplet;
import processing.core.PVector;


public class SummerLeaf extends Leaf{
	PApplet parent;
	PVector loc;
	int [] rgb = new int[3];
	int icolor;
	Leaf nextLeaf;
	boolean morph;
	float length;
	// Leaf and fruit color
	int [] rSummer = {34,27,9,86,22,127};
	int [] gSummer = {120,79,106,130,184,221};
	int [] bSummer = {15,8,9,3,78,76};

	// Vertex
	PVector [] vertex = {new PVector(0,0), 
			new PVector(5,5)//1
	};
	PVector [] vertex_fall ={ 
			new PVector(0,0), 
			new PVector(1,0),	//1
			new PVector(0,0),	//2
			new PVector(0,0),	//3
			new PVector(0,0),	//4
			new PVector(0,0),	//5
			new PVector(0,0),	//6
			new PVector(0,0),	//7
			new PVector(0,0),	//8
			new PVector(0,0),	//9
	};	

	SummerLeaf(PApplet p, PVector l,int icol,int f){
		super(p,"summer",l);
		parent = p;
		loc = l;
		icolor = icol;
		length = icolor;

		fruit = false;
		for(int i=0;i<3;i++){
			rgb[i]=0;
		}
		// decides whether it is a fruit or leaf
		if(f == 1){
			fruit = true;
		} 

		vertex[1].mult(length);
	}


	public void color(){
		rgb[0] = rSummer[icolor];
		rgb[1] = gSummer[icolor];
		rgb[2] = bSummer[icolor];

		parent.stroke(rgb[0],rgb[1],rgb[2]);
		/*for(int i=0;i<3;i++){
			rgb[i] = ;	
		}*/
	}
	public void displayFruit(float size){
		parent.fill(icolor*10+190,icolor*10+10,0);
		parent.stroke(icolor*10+190+50,icolor*10+10,0);
		parent.ellipse(loc.x,loc.y,size,size);
	}

	public PVector getVertex(int index){
		return vertex[index];
	}

	public void fallAnim(int h){
	}

	public boolean morphing(int k){

		float max = 100f;
		if(k>=max){
			return true;
		}

		else { 
				//System.out.println(vertex[i]);
				//length -= k*length/max;
				vertex[0].x = (float)(1- k/max)*vertex[0].x 
						+ (float)k/max*(length/2)*2;
				vertex[0].y = (float)(1- k/max)*vertex[0].y 
						+ (float)k/max*2;
				vertex[1].x = (float)(1- k/max)*vertex[1].x 
						+ (float)k/max*(length)*2;
				vertex[1].y = (float)(1- k/max)*vertex[1].y 
						+ (float)k/max*(length)*2;
			
			if(k>=max/4){
				morph = true;
				for (int i=0;i<10;i++){
					vertex_fall[i].x = (float)k/max*nextLeaf.getVertex(i).x*length;
					vertex_fall[i].y = (float)k/max*nextLeaf.getVertex(i).y*length;
				}
			}
		}

		return false;
	}
	public Leaf nextL(){
		return new FallLeaf(parent,loc,icolor,parent.random(0,2*parent.PI));
	}
	public void changeSeason(){
		//change to Fall
		nextLeaf = new FallLeaf(parent,loc,(int)parent.random(0,6),parent.random(0,2*parent.PI));
	}

	public void display() {
		//parent.noStroke();
		//	parent.fill(50,100);
		parent.strokeWeight(icolor*0.7f);
		if(!morph){
			if(!fruit ){
				color();
				parent.fill(255);
				parent.pushMatrix();
				parent.translate(loc.x,loc.y);
				parent.rotate(parent.PI/(float)(icolor+1));
				parent.rect(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y, 4);
				parent.popMatrix();

			}
			else{
				displayFruit(6f);
				/*parent.triangle(loc.x-0.7f,loc.y,
				loc.x+0.7f,loc.y,
				loc.x,loc.y+0.7f);*/
			}
		}
		else{
			parent.stroke((int)parent.random(230,255),
					(int)parent.random(80,110),
					(int)parent.random(0,15));
			parent.fill(255);
			/*parent.fill((int)parent.random(230,255),
					(int)parent.random(80,110),
					(int)parent.random(0,15));*/
			parent.pushMatrix();
			parent.translate(loc.x, loc.y);
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y);
			parent.translate(vertex[0].x,vertex[0].y);
			parent.rotate(parent.random(0,2*parent.PI));

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[1].x,vertex_fall[1].y,
					vertex_fall[2].x,vertex_fall[2].y);//-3

			parent.triangle(vertex_fall[3].x,vertex_fall[3].y,
					vertex_fall[4].x,vertex_fall[4].y,
					vertex_fall[6].x,vertex_fall[6].y);//*0.55f);

			parent.triangle(vertex_fall[3].x,vertex_fall[3].y,
					vertex_fall[4].x,vertex_fall[4].y,
					vertex_fall[7].x,vertex_fall[7].y);//*0.55f);

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[5].x,vertex_fall[5].y,
					vertex_fall[8].x,vertex_fall[8].y);

			parent.triangle(vertex_fall[0].x,vertex_fall[0].y,
					vertex_fall[5].x,vertex_fall[5].y,
					vertex_fall[9].x,vertex_fall[9].y);
			parent.popMatrix();

		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
