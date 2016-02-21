import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.MouseEvent;
import g4p_controls.*;

public class SketchApp extends PApplet{
	ArrayList<Branch> tree;
	ArrayList<Leaf> leaves;
	boolean morphed= false;
	boolean grow = false;
	int width = 840;
	int height = 600;
	float time;
	float kFrame = 0;
	Color bgColor;
	//GUI
	GCustomSlider slider;
	//Leaf l;
	public int [] r = {157,255,196,116};
	public int [] g = {255,96,126,166};
	public int [] b = {108,57,80,188};
	int season = 2;
	
	public void setup(){
		//	size(840,600);
		bgColor = new Color(255);
		background(bgColor.getRGB());

		frameRate(30);
		tree = new ArrayList<Branch>();
		leaves = new ArrayList<Leaf>();
		/*l = new SummerLeaf(this,new PVector(width/2,height/2),(int)random(0,6),2); //leaf
		l.changeSeason();
		System.out.println(l.toString());
		System.out.println(l.nextL().toString());
		 */time = System.nanoTime() * pow(10,-9) ;
		 G4P.registerSketch(this);

		 slider = new GCustomSlider(this, 20, 20, 260, 50, null);
		 // show          opaque  ticks value limits
		 slider.setShowDecor(false, true, true, true);
		 slider.setNbrTicks(10);
		 slider.setLimits(1, 0, 2);
		 
	}
	public void settings() {
		size(840, 600);

	}/*
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

	}*/
	public void mouseClicked(MouseEvent event){
		background(bgColor.getRGB());

		//Leaf newleaf = new SpringLeaf(this,b.end,(int)random(0,6)); 



		// Removes previous tree
		tree.clear();
		leaves.clear();
		// Add new tree to the mouse position
		Branch b = new Branch(this,new PVector(event.getX(),event.getY()),new PVector(0,-2.5f),60,true);
		tree.add(b);

	}
	/*public void keyPressed(){
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
			leaves.clear();
		}
	}*/

	public void draw(){
		
		// background color gradient
		float step=300f;
		Color rgb_end = new Color(r[season],g[season],b[season]);
		float hsb_start[] = new float[3];
		float hsb_end[] = new float[3];

		Color.RGBtoHSB(bgColor.getRed(),bgColor.getGreen(),bgColor.getBlue(),hsb_start);
		//	System.out.println(nextLeaf.toString());
		Color.RGBtoHSB(rgb_end.getRed(),rgb_end.getGreen(),rgb_end.getBlue(),hsb_end);

		// Change the hue to make a color gradient
		hsb_start[0] += (hsb_end[0]-hsb_start[0])/step;
		hsb_start[1] += (hsb_end[1]-hsb_start[1])/step;
		hsb_start[2] += (hsb_end[2]-hsb_start[2])/step;

		bgColor = new Color(Color.HSBtoRGB(hsb_start[0],hsb_start[1],hsb_start[2]));
		background(bgColor.getRGB());

		/*	l.display();
		l.morphing(kFrame);
		kFrame++;
		if(kFrame > 300){
			l = l.nextL();
			l.changeSeason();
			kFrame = 0;
		}*/


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
					Leaf newleaf;
					switch (season){
					case 0 : 
						newleaf = new SpringLeaf(this,b.end,(int)random(0,6)); 
						break;
					case 1 :
						newleaf = new SummerLeaf(this,b.end,(int)random(0,6),(int)random(0,2),(float)random(0,2*PConstants.PI)); 
						break;
					case 2: 
						newleaf = new FallLeaf(this,b.end,(int)random(0,6),random(0,2*PI)); 
						break;
					case 3:
						newleaf = new PsycheLeaf(this,b.end,(int)random(0,6));
						break;
					default:
						newleaf = new SpringLeaf(this,b.end,(int)random(0,6)); 
							break;		
					}
					//Leaf newleaf = new FallLeaf(this,b.end,(int)random(0,6),random(0,2*PI)); 
					//Leaf newleaf = new SummerLeaf(this,b.end,(int)random(0,6),(int)random(0,2),(float)random(0,2*PConstants.PI)); 
					newleaf.changeSeason();

					leaves.add(newleaf);//new Leaf(this,b.end));
					grow = true;
					time = System.nanoTime() * pow(10,-9);
				}
			}
		}

		// Display each leaf
		for (Leaf leaf : leaves) {
			//System.out.println(leaf.toString());
			leaf.display(); 
		}

		// Applying morphing to leaves
		if(grow && !morphed){
			//System.out.println(leaves.size());
			for (Leaf leaf : leaves) {
				morphed = leaf.morphing(kFrame);
			}

			kFrame++;
		}

		// End of morphing, replacing by new leaves
		if(kFrame > 300){
			for (int i = 0;i<leaves.size();i++){
				Leaf leafAdd = leaves.get(i).nextL();
				leafAdd.changeSeason();
				leaves.set(i,leafAdd);		
			}

			kFrame=0;
			morphed = false;
			if (season == 3){
				season  = 0;
			}
			else{
				season ++;
			}
		}

	}
	/*public void handleSliderEvents(GValueControl slider, GEvent event){
			for (int i = 0;i<leaves.size();i++){
				leaves.get(i).setGrowingTime(slider.getValueF());
			}
	}*/
	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "SketchApp" });
	}
}  
