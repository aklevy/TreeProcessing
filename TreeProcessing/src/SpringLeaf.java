import processing.core.PApplet;
import processing.core.PVector;


public class SpringLeaf extends Leaf{
	PApplet parent;
	PVector loc;
	int [] rgb = new int[3];
	int icolor,fruit;

	int [] rSpring = {34,27,9,86,22,127};
	int [] gSpring = {120,79,106,130,184,221};
	int [] bSpring = {15,8,9,3,78,76};

	SpringLeaf(PApplet p, PVector l,int icol){
		super("spring",l);
		parent = p;
		loc = l;
		icolor = icol;
		
		for(int i=0;i<3;i++){
			rgb[i]=0;
		}
		// decides whether it is a fruit or leaf
		fruit = icolor; 
		
	}
	
	
	public void color(){
			rgb[0] = rSpring[icolor];
			rgb[1] = gSpring[icolor];
			rgb[2] = bSpring[icolor];
		
		parent.stroke(rgb[0],rgb[1],rgb[2]);
		/*for(int i=0;i<3;i++){
			rgb[i] = ;	
		}*/
	}
	public void fruit(){
		parent.stroke(255,(int)parent.random(80,150),(int)parent.random(130,190));
		parent.ellipse(loc.x,loc.y,2,2);
	}
	public void fallAnim(int h){
	}
	public void display() {
		//parent.noStroke();
		//	parent.fill(50,100);
		parent.strokeWeight(icolor*0.7f);

		if(fruit < 5){
			color();
			parent.pushMatrix();
			parent.translate(loc.x,loc.y);
			parent.rotate(parent.PI/(float)(icolor+1));
			parent.rect(0,0, 2, 2, 4);
			parent.popMatrix();
			
		}
		else{
			fruit();
			/*parent.triangle(loc.x-0.7f,loc.y,
				loc.x+0.7f,loc.y,
				loc.x,loc.y+0.7f);*/
		}
		//parent.ellipse(loc.x,loc.y,2,2);   
	}

}