import processing.core.*;

public class MainApp extends PApplet{
	  // Your global variables goes here
    float diameter =  100f;

    // Processing setup method
    public void setup() {
        //size(640, 480);
    }
    public void settings() {
    	  size(640, 480);
    	}
    // Processing draw method
    public void draw() {
        ellipse(width * 0.5f, height * 0.5f, diameter, diameter);
    }
    
    public static void main(String args[]) {
        PApplet.main(new String[] { "--present", "MainApp" });
      }
}
