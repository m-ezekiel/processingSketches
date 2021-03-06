// Color Transparent Boxes
// Jan. 13, 2016

void setup() {
  size(800, 600);
  background(0);
  fill(100);
  noStroke();
  textSize(18);
  text("touch.", width/2, height/2);
} 

void draw() {
} // Empty draw() keeps the program running

void mousePressed() {
  float x = mouseX;
  float y = mouseY;
  
  if (keyPressed) {
    fill(y, sqrt(x*y), y, x);
    rect(mouseX, mouseY, x, y);
  } else  
    fill(x, y, sqrt(x*y), y);
    rect(mouseX, mouseY, x, y);
}