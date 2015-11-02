import processing.core.*;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class SketchApp extends PApplet{
	ArrayList<Branch> tree;
	ArrayList<Leaf> leaves;
	boolean morphed= false;
	boolean grow = false;
	int width = 840;
	int height = 600;
	float time;
	int kFrame = 0;

	public void setup(){
		//	size(840,600);
		background(255);

		frameRate(30);
		tree = new ArrayList<Branch>();
		leaves = new ArrayList<Leaf>();
		time = System.nanoTime() * pow(10,-9) ;

	}
	public void settings() {
		size(840, 600);

	}
	public void season(int kFrame){


		// Changes season 
		//	if((System.nanoTime() * pow(10,-9)-time)>=5 ){ 
		//leaves.clear();

		if(!morphed){
			for (int i = tree.size()-1; i > 512; i--) {
				Branch b = tree.get(i);
				for (Leaf leaf : leaves) {
					morphed = leaf.morphing(kFrame);
				}
			}
		}


		// Resets timer
		//time = System.nanoTime() * pow(10,-9);

	}
	public void mouseClicked(MouseEvent event){
		background(255);


		// Removes previous tree
		tree.clear();
		leaves.clear();
		// Add new tree to the mouse position
		Branch b = new Branch(this,new PVector(event.getX(),event.getY()),new PVector(0,-2.5f),60,true);
		tree.add(b);

	}

	public void keyPressed(){
		leaves.clear();
		Leaf newleaf;
		for (int i = tree.size()-1; i >= 1024/2; i--) {
			Branch b = tree.get(i);

			if(key == 'a'){ //spring
				newleaf = new SpringLeaf(this,b.end,(int)random(0,6)); 
			}
			else if(key == 'z'){ //summer
				newleaf = new SummerLeaf(this,b.end,(int)random(0,6),(int)random(0,10)%2); 
			}
			else if(key == 'e'){ //fall
				newleaf = new FallLeaf(this,b.end,(int)random(0,6),random(0,2*PI)); 
			}
			else if(key == 'r'){ //christmas decoration
				newleaf = new PsycheLeaf(this,b.end,(int)random(0,6)); 
			}
			else{
				newleaf = new SummerLeaf(this,b.end,(int)random(0,6),(int)random(0,10)%2); 
			}
			leaves.add(newleaf);

		}
		if(key == 't'){ //Winter
			/*for (Leaf leaf : leaves) {
				if (leaf instanceof FallLeaf){
					leaf.fallAnim(height);

				}
			}*/
			leaves.clear();
		}
	}

	public void draw(){

		background(255);


		//
		//System.out.println(grow);
		//old.display();
		//leaves.add(newL);

		/*Leaf fleaf = new FallLeaf(this,new PVector((float)width/2,(float)height/2),(int)random(0,6)); 
		fleaf.display();*/
		for (int i = tree.size()-1; i >= 0; i--) {
			// Get the branch, update and draw it
			Branch b = tree.get(i);
			b.update();
			b.render();
			// If it's ready to split
			if (b.timeToBranch()) {
				if (tree.size() < 1024) {
					//tree.remove(i);             // Delete it
					tree.add(b.branch( (int)random(0,60)));   // Add one going right
					tree.add(b.branch((int)random(-60,0)));   // Add one going left
				} 
				else {
					//Leaf newleaf = new FallLeaf(this,b.end,(int)random(0,6),random(0,2*PI)); 
					//Leaf newleaf = new SummerLeaf(this,b.end,(int)random(0,6)); 
					Leaf newleaf = new SpringLeaf(this,b.end,(int)random(0,6)); 
					newleaf.changeSeason();
					leaves.add(newleaf);//new Leaf(this,b.end));
					grow = true;
					time = System.nanoTime() * pow(10,-9);
				}
			}
		}

		for (Leaf leaf : leaves) {
			//System.out.println(leaf.toString());
			leaf.display(); 
		}

		if(grow && !morphed){
			//System.out.println(leaves.size());
			for (Leaf leaf : leaves) {
				morphed = leaf.morphing(kFrame);
			}
			
			kFrame++;
		}
		//System.out.println(kFrame);
		if(kFrame >= 80){

			ArrayList<Leaf> newLeaves = new ArrayList<Leaf>();

			for (Leaf leaf : leaves) {
				Leaf leafAdd = leaf.nextL();
				leafAdd.changeSeason();
				newLeaves.add(leafAdd);
			}
			leaves.clear();

			for (Leaf newLeaf : newLeaves) {
				leaves.add(newLeaf);
			}
			kFrame=0;
			morphed = false;
		}

	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "SketchApp" });
	}
}  
