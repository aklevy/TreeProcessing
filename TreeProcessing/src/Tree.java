import processing.core.*;
import java.lang.Math;

public class Tree{
	PApplet parent; // The parent PApplet that we will render ourselves onto

	PVector root = new PVector(-10,-10);
	boolean ready = false;
	int limit;

	float length,theta;
	int [] rgb = new int[3];
	
	Tree(PApplet p, float l) {
		parent = p;
		length = l;
		limit = 4;
		// first branch (tree trunk) in black
		for(int i=0;i<3;i++){
			rgb[i] = 0;	
		}
		// theta = PI/6;
	}

	public void setRoot(float x, float y){
		root.x = x;
		root.y = y;
		ready = true;
		//  root = new PVector(x,y); 
	}
	public void display(){
		if(ready){
			parent.strokeWeight(length);
			parent.line(root.x,root.y,root.x,root.y-length/20);
			parent.translate(root.x,root.y-length/20);
			parent.stroke(0,0,0);
			branch(length);
		}
	}
	public void reload(){
		theta = parent.random(0,parent.PI/3);
		limit = (int)parent.random(1,60);
		for(int i=0;i<3;i++){
			rgb[i] = (int)parent.random(0,255);	
		}
	}

	public void color(boolean leaf){
		if(leaf){
			for(int i=0;i<3;i++){
				rgb[i] = (int)parent.random(0,255);	
			}
		}
		else{
			for(int i=0;i<3;i++){
				rgb[i] = 0;	
			}
		}
		parent.stroke(rgb[0],rgb[1],rgb[2]);
	}
	
	public void branch(float len){
		parent.strokeWeight(len/10);
		//parent.stroke(rgb[0],rgb[1],rgb[2]);
		parent.line(0,0,0,-len);
		parent.translate(0,-len);
		theta = parent.random(0,parent.PI/3);

		len *= 0.76;
		if (len > 20){
			for (int i=0;i<1;i++){
				parent.pushMatrix();
				parent.rotate(theta);
				color(false);
				branch(len);
				parent.popMatrix();

				parent.pushMatrix();
				parent.rotate(-theta);
				color(false);
				branch(len);
				parent.popMatrix();
			}
		}

		else if (len < 10 && len > 2){
			color(true);
			for (int i=0;i<1;i++){
				parent.pushMatrix();
				parent.rotate(theta);
				leaf(len);
				parent.popMatrix();

				parent.pushMatrix();
				parent.rotate(-theta);
				leaf(len);
				parent.popMatrix();
			}
		}
}
	public void leaf(float len){
		parent.strokeWeight(len/10);
		//parent.stroke(rgb[0],rgb[1],rgb[2]);
		//parent.line(0,0,0,-len);
		parent.triangle(-len/2f,0f,
				len/2f,0f,
				0f,(float)Math.sin((double)parent.PI/3)*len);
		parent.translate(0,-len);
	
	}
	
	public String info(){
		return "Tree info: position" + root.x + " " + root.y;
	}
}