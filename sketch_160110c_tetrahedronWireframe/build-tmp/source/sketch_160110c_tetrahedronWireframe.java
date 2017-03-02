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

public class sketch_160110c_tetrahedronWireframe extends PApplet {

// Tetrahedron wireframe on black with mouse coordinate inputs
// 160110

public void setup() {
  
  background(0);   
  stroke(100);
  strokeWeight(2);
}

public void draw() {
  if (mousePressed) {
    background(0);
    line(200, 200, mouseX, mouseY);
    line(55, 55, mouseX, mouseY);
    line(355, 115, mouseX, mouseY);
    line(55, 55, 200, 200);
    line(55, 55, 355, 115);
    line(355, 115, 200, 200);
  } 
}
  public void settings() {  size(400, 400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_160110c_tetrahedronWireframe" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
