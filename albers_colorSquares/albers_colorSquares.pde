// Filename: albers_colorSquares.pde
// Author: m-ezekiel
// Date: May 12, 2016
// Interactive relative color demo


int rMode = ceil(random(3));
int rHue = floor(random(400));
int rSat = floor(random(50, 400));
int rBright = floor(random(50, 400));


// ******************************************
// Setup background, and primary color boxes.
// ******************************************

void setup() {
	size(800, 450);	 
	colorMode(HSB, height, height, height);
	background(0, 0, 20);	// bgColor 

	rectMode(CENTER);
	textAlign(RIGHT);

	noStroke();
	smooth();

	// Left outer square
	int leftCol = floor(random(height));
	int leftSat = floor(random(height));
	int leftBright = floor(random(height));
	fill(leftCol, leftSat, leftBright);
	rect(width/2 - 200, height/2 - 25, 400, 400);

	// Right outer square
	int rightCol = floor(random(height));
	int rightSat = floor(random(height));
	int rightBright = floor(random(height));
	fill(rightCol, rightSat, rightBright);
	rect(width/2 + 200, height/2 - 25, 400, 400);
}


// **********************************************************
// Draw function takes mouse XY for inner square color input.
// **********************************************************

void draw() {

	if (rMode == 1) {				// rMode == 1: colorHue
		// Left box
		fill(mouseX, rSat, rBright);
		rect(width/2 - 200, height/2 - 25, 100, 100);

		// Right box
		fill(mouseY, rSat, rBright);
		rect(width/2 + 200, height/2 - 25, 100, 100);
	}

	if (rMode == 2) {				// rMode == 2: colorSaturation
		// Left box
		fill(rHue, mouseX, rBright);
		rect(width/2 - 200, height/2 - 25, 100, 100);

		// Right box
		fill(rHue, mouseY, rBright);
		rect(width/2 + 200, height/2 - 25, 100, 100);
	}

	if (rMode == 3) {				// rMode == 3: colorBrightness		
		// Left box
		fill(rHue, rSat, mouseX);
		rect(width/2 - 200, height/2 - 25, 100, 100);

		// Right box
		fill(rHue, rSat, mouseY);
		rect(width/2 + 200, height/2 - 25, 100, 100);
	}

	println(mouseX, mouseY);		// Console output for debugging
	println("rMode: "+ rMode);	
	println("rHue: "+ rHue);		
	println("rSat: "+ rSat);		
	println("rBright: "+ rBright);	
} 


// **************************************************
// mouseClick interactivity draws comparison squares.
// **************************************************

void mouseClicked() {
	fill(0, 0, 20);						// Redraw bg to clear old text
	rect(width/2, 425, 800, 50);	

	if (rMode == 1) {					// Draw tiny squares at bottom
		fill(mouseX, rSat, rBright);
		rect(375, 425, 50, 50);
		fill(mouseY, rSat, rBright);
		rect(425, 425, 50, 50);
	} else if (rMode == 2) {
		fill(rHue, mouseX, rBright);
		rect(375, 425, 50, 50);
		fill(rHue, mouseY, rBright);
		rect(425, 425, 50, 50);
	} else if (rMode == 3) {
		fill(rHue, rSat, mouseX);
		rect(375, 425, 50, 50);
		fill(rHue, rSat, mouseY);
		rect(425, 425, 50, 50);
	}

}