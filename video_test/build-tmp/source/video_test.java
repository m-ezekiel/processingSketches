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

public class video_test extends PApplet {

PImage img;

// Image panes



Capture video;

public void setup() {
  
  frameRate(4);

  String[] cameras = Capture.list();
  
  video = new Capture(this, 640, 480, cameras[0], 4);
  video.start();     
}      


public void captureEvent(Capture video) {  
  video.read();
}


public void draw() {
  video.loadPixels();
  image(video, 0 - width/2, 0 - height/2);
  // The following does the same, and is faster when just drawing the image
  // without any additional resizing, transformations, or tint.
  //set(0, 0, cam);
  save("p1.png");
}
  public void settings() {  size(320, 240); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "video_test" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
