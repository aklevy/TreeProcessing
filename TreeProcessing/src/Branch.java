import processing.core.*;
import java.lang.Math;

public class Branch {

	PApplet parent;
	PVector start,end,speed;
	float timer, timerstart;
	boolean growing = true;
	

	Branch(PApplet p, PVector pos, PVector s, float n) {
		parent = p;
		start = pos.copy();
		end = pos.copy(); //at the beginning, the branch is a point
		speed =s.copy();//.mult(1f/10f);
		timerstart = n;
		timer = timerstart;
	}
	// Move location
	  void update() {
	   if (growing) {
	      end.add(speed);
	      
	    }
	  }

	  // Draw a dot at location
	  void render() {
		parent.strokeWeight(timerstart/20f);
	    parent.stroke(0);
	    parent.line(start.x,start.y,end.x,end.y);
	  }

	  // Check the timer to decide the growth
	  boolean timeToBranch() {
	    timer--;
	    if (timer < 0 && growing) {
	      growing = false;
	      return true;
	    } 
	    else {
	        return false;
	      }
	    }

	    // Create a new branch at the current location, but change direction by a given angle
	    Branch branch(float angle) {
	      // What is my current heading
	      float theta = speed.heading();
	      // What is my current speed
	      float mag = speed.mag();
	      // Turn me
	      theta += Math.toRadians(angle);
	      // Look, polar coordinates to cartesian!!
	      PVector newspeed = new PVector(mag*(float)Math.cos(theta),mag*(float)Math.sin(theta));
	      // Return a new Branch
	      return new Branch(parent,end,newspeed,timerstart*0.66f);
	    }

}
