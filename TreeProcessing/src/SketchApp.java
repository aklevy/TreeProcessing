import processing.core.*;
import processing.event.MouseEvent;

import java.util.ArrayList;

public class SketchApp extends PApplet{
	ArrayList<Branch> tree;
	ArrayList<Leaf> leaves;
	int width = 840;
	int height = 600;
	
	public void setup(){
		//	size(840,600);
		background(255);

		//	frameRate(30);
		tree = new ArrayList<Branch>();
		leaves = new ArrayList<Leaf>();

		// A branch has a starting location, a starting "velocity", and a starting "timer" 
		//Branch b = new Branch(this,new PVector(width/2,height),new PVector(0,-1),100);
		// Add to arraylist
		//tree.add(b);

	}
	public void settings() {
		size(840, 600);
	}
	public void mouseClicked(MouseEvent event){
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
				newleaf = new SummerLeaf(this,b.end,(int)random(0,6)); 
			}
			else if(key == 'e'){ //fall
				newleaf = new FallLeaf(this,b.end,(int)random(0,6),random(0,2*PI)); 
			}
			else if(key == 'r'){ //christmas decoration
				newleaf = new PsycheLeaf(this,b.end); 
			}
			else{
				newleaf = new SummerLeaf(this,b.end,(int)random(0,6)); 
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
					leaves.add(newleaf);//new Leaf(this,b.end));
				}
			}
		}

			for (Leaf leaf : leaves) {
				//System.out.println(leaf.toString());
				leaf.display(); 
			}
		}
	    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "SketchApp" });
      }
}  
