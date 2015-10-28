import processing.core.*;

public class Leaf {
	PApplet parent;
	PVector loc;
	int [] rgb = new int[3];
	int type; //type of colors for leaves (fall/summer/psychedelic)
	int [] rFall = {255,174,216,212,163,237};
	int [] gFall = {104,112,48,133,42,0};
	int [] bFall = {4,10,0,28,42,0};


	Leaf(PApplet p, PVector l,int t) {
		parent = p;
		loc = l;
		type = t;
		for(int i=0;i<3;i++){
			rgb[i] = 0;	
		}
	}
	public void color(){
		switch (type){
		case 0: //fall
			colorFall();
			break;
		case 1: //summer
			colorSummer();
			break;
		case 2: //psyche
			colorPsyche();
			break;
		default:
			colorSummer();
			break;
		}			
		parent.stroke(rgb[0],rgb[1],rgb[2]);
	}
	public void colorFall(){
		int index = (int)parent.random(0,6);
		rgb[0] = rFall[index];
		rgb[1] = gFall[index];
		rgb[2] = bFall[index];
		/*for(int i=0;i<3;i++){
			rgb[i] = ;	
		}*/
	}
	public void colorSummer(){
		for(int i=0;i<3;i++){
			rgb[i] = (int)parent.random(0,255);	
		}
	}
	public void colorPsyche(){
		for(int i=0;i<3;i++){
			rgb[i] = (int)parent.random(0,255);	
		}
	}
	
	void display() {
		parent.noStroke();
		color();
		//	parent.fill(50,100);
		parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y+1);
		//parent.ellipse(loc.x,loc.y,2,2);   
	}
}
