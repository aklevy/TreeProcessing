import processing.core.PApplet;
import processing.core.PVector;

public class PsycheLeaf extends Leaf{
	PApplet parent;
	Leaf nextLeaf;
	boolean square;
	int icolor;
	// vertex
	PVector [] vertex = {new PVector(-1,0),
						new PVector(1,0),
						new PVector(0,1),
						};

	PsycheLeaf(PApplet p, PVector l,int icol){
		super(p,"psychedelic",l);
		parent = p;
		loc = l;
		icolor = icol;
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
	
	public void displayFruit(float size){
		parent.pushMatrix();
		parent.translate(loc.x,loc.y);
		parent.fill(255,(int)parent.random(80,150),(int)parent.random(130,190));
		parent.stroke(255,(int)parent.random(80,150),(int)parent.random(130,190));
		parent.ellipse(0,0,size,size);
		parent.popMatrix();
	}
	public boolean morphing(int k){
		square = true;
		if(k>=70){
			return true;
		}

		if(nextLeaf.isFruit()){
			fruit = true;
			//color pink->red
			/*parent.stroke((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);
			parent.fill((k*6/10000f)*10+190,(k*6/10000f)*10+190,0);*/
			displayFruit((k/10f));
			return false;
		}
		else { 
			for (int i=0;i<2;i++){
				vertex[i].x = (float)(1- k/500f)*vertex[i].x 
							+ (float)k/500f*nextLeaf.getVertex(i).x;
				vertex[i].y = (float)(1- k/500f)*vertex[i].y 
							+ (float)k/500f*nextLeaf.getVertex(i).y;
			}
		}
		return false;
	}
	
	public Leaf nextL(){
		return new SpringLeaf(parent,loc,icolor);
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
		if(!square){
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(parent.PI/(float)(icolor+1));
		parent.triangle(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y,
						vertex[2].x,vertex[2].y);
		parent.popMatrix();
		}
		else{
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(parent.PI/(float)(icolor+1));
			parent.rect(vertex[0].x,vertex[0].y,
					vertex[1].x,vertex[1].y,4);
			parent.popMatrix();

		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
