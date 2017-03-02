import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class linesWalkThru extends PApplet {

// Filename: linesWalkThru.pde
// Description: 

// Global Variable Setup
int x1 = 300; int y1 = 100;
int x2 = 300; int y2 = 200;
int x3 = 300; int y3 = 450;

public void setup() {
	
	background(0);
	stroke(255);
	strokeWeight(3);
	
}

public void draw() {
	diag(x1, y1);
	diag(x2, y2);
	diag(x3, y3);

	x1 += 1;	// Animate using the draw loop
	x2 -= 1;
	y3 -= 1;	

	println("Processing...");	// Console output
}

public void diag(int x, int y) {
	line(x, y, x+20, y-40);
	line(x+10, y, x+30, y-40);
	line(x+20, y, x+40, y-40);
}
  public void settings() { 	size(600, 400); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "linesWalkThru" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
