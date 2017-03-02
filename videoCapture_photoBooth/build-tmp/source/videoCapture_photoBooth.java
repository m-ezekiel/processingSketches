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

public class videoCapture_photoBooth extends PApplet {

// Image panes
PImage p1, p2, p3, p4;
int i = 0;
int j = 0;
int newFrame;

public void setup() { 
  background(0);
  
  noStroke();

  frameRate(3);

}

public void draw() {  

  p1 = loadImage("/Users/m-ezekiel/Documents/Processing/sketches/video_test/p1.png");
  PImage slice = p1.get(i, 0, 10, 240);
  image(slice, j, 0, 20, 480);
  int c = slice.get(5, 120);
  println("c: "+c/1000000);

  newFrame = abs(c/1000000);

  image(p1, 0, 0, 160, 120);

  // fill(c, 30);
  // rect(j, 0, 20, 480);

  i += 10;
  j += 20;

  if (i == 320) {
    i = 0;
    j = 0;
  }

  // frameRate(newFrame + 1);

}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "videoCapture_photoBooth" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
