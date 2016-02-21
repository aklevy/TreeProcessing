import processing.core.PConstants;
import processing.core.PVector;
import processing.core.PApplet;

import java.awt.Color;
//import java.lang.Math;


public class FallLeaf extends Leaf{
	// Processing applet containing method draw and setup
	PApplet parent;

	// Branch end to be fixed to 
	PVector loc;

	// Leaf color
	Color rgb;

	// Random to choose color
	int icolor;

	// Angle for the leaf to grow, length and weight of this leaf
	float angle, length, weight;

	// Next leaf to morph into when changing season
	Leaf nextLeaf;

	// morphing time
	float max = 300f;
	
	// Time to morph or not
	boolean morph = false;
	boolean noleaf = false;

	// Time  and height to make the leaf fall from
	boolean fall = false;
	float fallHeight = 10;

	// Color for leaves
	public int [] rFall = {255,174,216,212,163,237};
	public int [] gFall = {104,112,48,133,42,0};
	public int [] bFall = {4,10,0,28,42,0};

	// Vertex 
	PVector[] vertex;

	/**************************************
	 * * CONSTRUCTOR
	 */
	FallLeaf(PApplet p, PVector l,int icol,float ang){
		super(p,"fall",l);
		parent = p;
		loc = l;
		icolor = icol;
		angle = ang;
		weight = icolor*0.4f;

		// initializes color
		rgb = new Color(rFall[icolor],gFall[icolor],bFall[icolor]);
		length = icol/0.7f;

		// get shape of the leaf
		vertex = new PVector[]{
				new PVector(0f,0f), //0
				new PVector(1f,0f), //1
				new PVector(1/2f,-0.35f), //2

				new PVector(0.5f,0), //3
				new PVector(0,-0.5f), //4
				new PVector(0,1f), //5
				new PVector(-1f,-1*0.35f), //6
				new PVector(1f,-1*0.35f), //7
				new PVector(-0.35f,0.2f), //8
				new PVector(0.35f,0.2f) //9
		};
		for (int i = 1;i<10;i++){
			vertex[i].mult(length);
		}
		/*System.out.println("true values : ");

		for (int i=0;i<10;i++){
			System.out.println("x: "+vertex[i].x+" y: "+vertex[i].y);
		}*/

	}

	/**************************************
	 * * COLOR METHOD
	 */
	public  Color getRgb (){
		return rgb;
	}

	public void color(){
		parent.stroke(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
		parent.fill(rgb.getRed(),rgb.getGreen(),rgb.getBlue());
	}


	public PVector getVertex(int index){
		//System.out.println("return "+vertex[index].x);
		return vertex[index];
		//.mult(length);
	}
	/**************************************
	 * * MORPHING
	 */
	public Leaf nextL(){
		return nextLeaf;//return new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}
	public void changeSeason(){
		//change to christmas
		nextLeaf = new PsycheLeaf(parent,loc,(int)parent.random(0,6));
	}


	public boolean morphing(float k){
		k *= growth;
		fall = true;
		morph = true;
		
		if(k>=max*1/6){
			if( (int)parent.random(0,100) >= 99){
				noleaf = true;
			}
		}
		/*if(k == 0){				
			System.out.println("final ");
			for (int i=0;i<10;i++){
				System.out.println(vertex[i].x);
		}
		}*/
		if(k>max){
			return true;
		}
		else {
			/*if(k==0){
				System.out.println("true values : ");

				for (int i=0;i<10;i++){
					System.out.println("x: "+vertex[i].x+" y: "+vertex[i].y);
				}
			}*/
			for (int i=0;i<3;i++){
				//System.out.println(vertex[i]);
				length -= k/10*length/max;
				vertex[i].x = (float)(1- k/max)*vertex[i].x 
						+ (float)(k)/max*nextLeaf.getVertex(i).x;
				vertex[i].y = (float)(1- k/max)*vertex[i].y 
						+ (float)(k)/max*nextLeaf.getVertex(i).y;
			}

		}
		return false;
	}


	private void fallAnim(){
		float plip = PApplet.sin(fallHeight);
		parent.translate(fallHeight*plip*plip*plip*parent.random(1,2), fallHeight);
		fallHeight += parent.random(8,13) + fallHeight/15;
	}

	/**************************************
	 * * DISPLAY
	 */
	public void display() {
		//parent.noStroke();
		parent.strokeWeight(weight);//icolor*0.7f);
		this.color();
		//parent.fill(255);
		//	parent.fill(50,100);
		/*parent.triangle(loc.x-1,loc.y,
				loc.x+1,loc.y,
				loc.x,loc.y-1);*/
		if(noleaf){

		}
		else{ 
			if (!morph){
				angle += (2*PConstants.PI-angle)*PConstants.PI/100;

				parent.pushMatrix();
				//parent.rotate(PConstants.PI/(float)(icolor+1));
				parent.rect(loc.x+length/2,loc.y,length,length);
				parent.translate(loc.x-length/2,loc.y);
				parent.rotate(angle);

				parent.triangle(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y,
						vertex[2].x,vertex[2].y);//-3

				parent.triangle(vertex[3].x,vertex[3].y,
						vertex[4].x,vertex[4].y,
						vertex[6].x,vertex[6].y);//*0.55f);

				parent.triangle(vertex[3].x,vertex[3].y,
						vertex[4].x,vertex[4].y,
						vertex[7].x,vertex[7].y);//*0.55f);

				parent.triangle(vertex[0].x,vertex[0].y,
						vertex[5].x,vertex[5].y,
						vertex[8].x,vertex[8].y);

				parent.triangle(vertex[0].x,vertex[0].y,
						vertex[5].x,vertex[5].y,
						vertex[9].x,vertex[9].y);
				parent.popMatrix();
			}
			else{

				parent.pushMatrix();
				parent.translate(loc.x,loc.y);
				parent.rotate(PConstants.PI/(float)(icolor+1));

				parent.triangle(vertex[0].x,vertex[0].y,
						vertex[1].x,vertex[1].y,
						vertex[2].x,vertex[2].y);//-3
				parent.popMatrix();

				parent.pushMatrix();
				parent.translate(loc.x-length/2,loc.y);
				if(fall){
					fallAnim();

					parent.rotate(angle);

					parent.triangle(vertex[3].x,vertex[3].y,
							vertex[4].x,vertex[4].y,
							vertex[6].x,vertex[6].y);//*0.55f);

					parent.triangle(vertex[3].x,vertex[3].y,
							vertex[4].x,vertex[4].y,
							vertex[7].x,vertex[7].y);//*0.55f);

					parent.triangle(vertex[0].x,vertex[0].y,
							vertex[5].x,vertex[5].y,
							vertex[8].x,vertex[8].y);

					parent.triangle(vertex[0].x,vertex[0].y,
							vertex[5].x,vertex[5].y,
							vertex[9].x,vertex[9].y);
				}
				parent.popMatrix();
			}
		}
	}

}
