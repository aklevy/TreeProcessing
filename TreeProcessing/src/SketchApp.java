import processing.core.*;
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
			Branch b = new Branch(this,new PVector(width/2,height),new PVector(0,-1),100);
			// Add to arraylist
			tree.add(b);

	}
	public void settings() {
		size(840, 600);
	}
	public void draw(){
		background(255);
		if(mousePressed == true){
			// A branch has a starting location, a starting "velocity", and a starting "timer" 
		//	Branch b = new Branch(this,new PVector(width/2,height),new PVector(0,-1),100);
			// Add to arraylist
		//	tree.add(b);
		}
		if(keyPressed && (key == 'r')){
			//  t.reload();
		}
		for (int i = tree.size()-1; i >= 0; i--) {
			// Get the branch, update and draw it
			Branch b = tree.get(i);
			b.update();
			b.render();
			// If it's ready to split
			if (b.timeToBranch()) {
				if (tree.size() < 1024) {
					//tree.remove(i);             // Delete it
					tree.add(b.branch( 30));   // Add one going right
					tree.add(b.branch(-25));   // Add one going left
				} 
				else {
					leaves.add(new Leaf(this,b.end,0));
				}
			}
		}

			for (Leaf leaf : leaves) {
				leaf.display(); 
			}
		}
	    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "SketchApp" });
      }
}  
