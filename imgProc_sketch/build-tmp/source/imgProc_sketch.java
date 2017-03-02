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

public class imgProc_sketch extends PApplet {

// Filename: imgProc_sketch.pde
// Author: m-ezekiel
// Image processing sketch
// Model in undergarments and grey peacoat gazing stage left

PImage img;

public void setup() {
      
    img = loadImage("model_jacket01.jpg");
    background(0);
}

public void draw() {
    for (int i = 0; i < 7; i++) {
        image(img, i*200, 0);
    }    
}
  public void settings() {  size(1597, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "imgProc_sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
