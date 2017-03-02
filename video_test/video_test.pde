PImage img;

// Image panes

import processing.video.*;

Capture video;

void setup() {
  size(320, 240);
  frameRate(4);

  String[] cameras = Capture.list();
  
  video = new Capture(this, 640, 480, cameras[0], 4);
  video.start();     
}      


void captureEvent(Capture video) {  
  video.read();
}


void draw() {
  video.loadPixels();
  image(video, 0 - width/2, 0 - height/2);
  // The following does the same, and is faster when just drawing the image
  // without any additional resizing, transformations, or tint.
  //set(0, 0, cam);
  save("p1.png");
}