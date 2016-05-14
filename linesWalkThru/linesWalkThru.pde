// Filename: linesWalkThru.pde
// Description: 

// Global Variable Setup
int x1 = 300; int y1 = 100;
int x2 = 300; int y2 = 200;
int x3 = 300; int y3 = 450;

void setup() {
	size(600, 400);
	background(0);
	stroke(255);
	strokeWeight(3);
	smooth();
}

void draw() {
	diag(x1, y1);
	diag(x2, y2);
	diag(x3, y3);

	x1 += 1;	// Animate using the draw loop
	x2 -= 1;
	y3 -= 1;	

	println("Processing...");	// Console output
}

void diag(int x, int y) {
	line(x, y, x+20, y-40);
	line(x+10, y, x+30, y-40);
	line(x+20, y, x+40, y-40);
}