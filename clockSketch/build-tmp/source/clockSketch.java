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

public class clockSketch extends PApplet {

// Filename: clockSketch.pde
// Author: m-ezekiel
// Date: 
//



public void setup () {
	
	
  noStroke();

  // Draw the clock face 
  int radius = PApplet.parseInt(width/1.5f);
  int [] centerPoint = {width/2, height/2};
  fill(0);
  ellipse(centerPoint[0], centerPoint[1], radius, radius);
}

public void draw() {

  int radius = PApplet.parseInt(width/1.5f);
  int [] centerPoint = {width/2, height/2};

  int sec = second();
  int min = minute();
  int hour = hour();
  int day = day();

  // days
  fill(160);
  arc(centerPoint[0], centerPoint[1], radius, radius, 0, PI/15 * day);

  // secs
  fill(120);
  arc(centerPoint[0], centerPoint[1], radius/1.3f, radius/1.3f, 0, PI/30 * sec);

  // mins
  fill(70);
  arc(centerPoint[0], centerPoint[1], radius/2, radius/2, 0, PI/30 * min);

  // hour
  fill(0);
  arc(centerPoint[0], centerPoint[1], radius/4, radius/4, 0, PI/12 * hour+1);

}
  public void settings() { 	size(800, 800); 	smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "clockSketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
