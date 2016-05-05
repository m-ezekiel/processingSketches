// Filename: keyPress_prototype.pde
// Author: m-ezekiel
// Date: 
//
// Keylogging prototype using text processing tools.

void setup() {
	size(600, 400);
	background(100);
	smooth();
}

void draw() {

	int m = millis();	// Milliseconds

	if (keyPressed == true) {
		println(m);
	}
}