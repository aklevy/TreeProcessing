import processing.core.*;

public class SketchApp extends PApplet{
	Tree t;
	int width = 840;
	int height = 600;
	PVector p =new PVector(420,500);
	public void setup(){
	//	size(840,600);
		background(255);
		t = new Tree(this,100f);

		frameRate(30);


	}
	public void settings() {
	    	  size(840, 600);
	}
	public void draw(){
	    //noLoop();

	      background(255);
	     // line(mouseX,mouseY,width/2,height/2);
	     // translate(840/2,600/2);
	      if(mousePressed == true){
	        // translate(840/2-mouseX,600/2-mouseY);
	         t.setRoot(mouseX,mouseY);
	      }
	      if(keyPressed && (key == 'r')){
	        t.reload();
	      }
	          //    print(t.info());
	      t.display();
	     }
   
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "SketchApp" });
      }
}  
