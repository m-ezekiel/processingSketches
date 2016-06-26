// Filename: clockSketch.pde
// Author: m-ezekiel
// Date: 
//



void setup () {
	size(800, 800);
	smooth();
  noStroke();

  // Draw the clock face 
  int radius = int(width/1.5);
  int [] centerPoint = {width/2, height/2};
  fill(0);
  ellipse(centerPoint[0], centerPoint[1], radius, radius);
}

void draw() {

  int radius = int(width/1.5);
  int [] centerPoint = {width/2, height/2};

  int sec = second();
  int min = minute();
  int hour = hour();
  int day = day();

  // days
  fill(160);
  arc(centerPoint[0], centerPoint[1], radius, radius, 0, PI/15 * day);

  // secs
  fill(120);
  arc(centerPoint[0], centerPoint[1], radius/1.3, radius/1.3, 0, PI/30 * sec);

  // mins
  fill(70);
  arc(centerPoint[0], centerPoint[1], radius/2, radius/2, 0, PI/30 * min);

  // hour
  fill(0);
  arc(centerPoint[0], centerPoint[1], radius/4, radius/4, 0, PI/12 * hour+1);

}