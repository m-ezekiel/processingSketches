// Image panes
PImage p1, p2, p3, p4;
int i = 0;
int j = 0;
int newFrame;

void setup() { 
  background(0);
  size(640, 480);
  noStroke();

  frameRate(3);

}

void draw() {  

  p1 = loadImage("/Users/m-ezekiel/Documents/Processing/sketches/video_test/p1.png");
  PImage slice = p1.get(i, 0, 10, 240);
  image(slice, j, 0, 20, 480);
  color c = slice.get(5, 120);
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