import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class videoPixels_schiffman extends PApplet {


// Size of each cell in the grid
int videoScale = 10;
// Number of columns and rows in the system
int cols, rows;
// Variable for capture device
Capture video;

public void setup() { 
    
  // Initialize columns and rows  
  cols = width / videoScale;  
  rows = height / videoScale;  
  // Construct the Capture object  
  video = new Capture(this, cols, rows);  
  video.start();
}

public void captureEvent(Capture video) {  
  video.read();
}

public void draw() {  
  background(30,40,100);
  video.loadPixels();  
  // Begin loop for columns  
  for (int i = 0; i < cols; i++) {    
    // Begin loop for rows    
    for (int j = 0; j < rows; j++) {      
      // Where are you, pixel-wise?      
      int x = i*videoScale;      
      int y = j*videoScale;    
      
      // Reverse the column to mirro the image.
      int loc = (video.width - i - 1) + j * video.width;       
      
      int c = video.pixels[loc];
      // A rectangle's size is calculated as a function of the pixel\u2019s brightness. 
      // A bright pixel is a large rectangle, and a dark pixel is a small one.
      float sz = (brightness(c)/255) * videoScale;       
      
      rectMode(CENTER);      
      fill(255);      
      noStroke();      
      rect(x + videoScale/2, y + videoScale/2, sz, sz);    
    }  
  }
}  
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "videoPixels_schiffman" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
