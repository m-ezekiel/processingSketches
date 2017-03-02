// Ellipse on black with mouse coordinate inputs
// 160110

void setup() {
  size(700, 700);
  background(0);
}

void draw() {
  if (mousePressed) {
    stroke(0);
    fill(mouseY, mouseY, mouseY);
    ellipse(width/2, height/2, mouseY, mouseX);
  } 
}