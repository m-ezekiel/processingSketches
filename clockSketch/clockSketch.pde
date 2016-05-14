// Filename: clockSketch.pde
// Author: m-ezekiel
// Date: 
//

void setup () {
	size(400, 400);
	smooth();
}

void draw() {
  int sec = second();  // Values from 0 - 59
  int min = minute();  // Values from 0 - 59
  int hour = hour();    // Values from 0 - 23

  fill(255, 40);
  rect(100, 100, 200, hour * 10); // Hours
 
  fill(40, 40);
  rect(100, 100, 200, min);	// Minutes
 
  fill(100, 40);
  rect(100, 100, 200, sec);	// Seconds

}