// Tetrahedron wireframe on black with mouse coordinate inputs
// 160110

void setup() {
  size(400, 400);
  background(0);   
  stroke(100);
  strokeWeight(2);
}

void draw() {
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