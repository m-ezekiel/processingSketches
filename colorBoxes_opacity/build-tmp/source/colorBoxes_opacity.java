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

public class colorBoxes_opacity extends PApplet {

// Filename: colorBoxes_opacity.pde
// Date: Jan. 13, 2016
// Experiment with opacity and XY color/transparency mapping

public void setup() {
  
  background(0);
  fill(100);
  textSize(18);
  text("touch.", width/2, height/2);
} 

public void draw() {} // Empty draw() keeps the program running

public void mousePressed() {
  float x = mouseX;
  float y = mouseY;
  
  if (keyPressed) {
    fill(y, sqrt(x * y), y, x);
    rect(mouseX, mouseY, x, y);
  } else  
    fill(x, y, sqrt(x * y), y);
    rect(mouseX, mouseY, x, y);
}
  public void settings() {  size(800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "colorBoxes_opacity" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
