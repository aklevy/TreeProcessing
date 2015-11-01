import processing.core.PApplet;
import processing.core.PVector;


public class SummerLeaf extends Leaf{
	PApplet parent;
	PVector loc;
	int [] rgb = new int[3];
	int icolor;
Leaf nextLeaf;
	// Leaf and fruit color
	int [] rSummer = {34,27,9,86,22,127};
	int [] gSummer = {120,79,106,130,184,221};
	int [] bSummer = {15,8,9,3,78,76};

	// Vertex
	PVector [] vertex = {new PVector(0,0), 
						new PVector(5,5)};
	
	SummerLeaf(PApplet p, PVector l,int icol,int f){
		super(p,"summer",l);
		parent = p;
		loc = l;
		icolor = icol;
		vertex[1].mult(icolor);
		fruit = false;
		for(int i=0;i<3;i++){
			rgb[i]=0;
		}
		// decides whether it is a fruit or leaf
		if(f == 1){
			fruit = true;
		} 
		
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
		return true;
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

		if(!fruit){
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
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}
