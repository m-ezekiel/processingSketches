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

public class point_array extends PApplet {


int bgColor = 255;

public void setup() {
	
	background(bgColor);
	

	stroke(0);
	strokeWeight(5);
}

public void draw() {
  for (int x = 10; x < 600; x += 25) {
  	for (int y = 10; y < 600; y += 15) {
  		point(x, y);
//  		stroke(x, y, x+y/2);	// color mod
  	}
  }

  println(mouseX, mouseY, mouseX + mouseY);
}

  public void settings() { 	size(600, 600); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "point_array" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
