// Ellipse on black with mouse coordinate inputs
// 160110

void setup() {
  size(400, 400);
  background(0);
}

void draw() {
  if (mousePressed) {
    stroke(0);
    fill(mouseY, mouseY, mouseY);
    ellipse(200, 200, mouseY, mouseX);
  } 
}