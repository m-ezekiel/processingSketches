
int bgColor = 255;

void setup() {
	size(600, 600);
	background(bgColor);
	smooth();

	stroke(0);
	strokeWeight(5);
}

void draw() {
  for (int x = 10; x < 600; x += 25) {
  	for (int y = 10; y < 600; y += 15) {
  		point(x, y);
//  		stroke(x, y, x+y/2);	// color mod
  	}
  }

  println(mouseX, mouseY, mouseX + mouseY);
}

