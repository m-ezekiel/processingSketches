// File: gamePad_sketch
// Author: m-ezekiel
// Template for using MIDI input devices

import org.gamecontrolplus.gui.*;
import org.gamecontrolplus.*;
import net.java.games.input.*;

ControlIO control;
Configuration config;
ControlDevice gpad;

// Position variables
int xpos = 0;
int ypos = 0;

// Controller variables
float analogX, analogY, analogU, analogV;
boolean A, B, X, Y, M1, M2, L1, L2, R1, R2;
boolean left, right, up, down, select1, select2;

// Drawing variables
int increment = 2;
int dispersion = 100;
int red, blue, green = 0;
int alpha = 100;
int brushSize = 300;


// -----
// Setup
// -----

public void setup() {
  size(1280, 750);
  background(0);
  //frameRate(4);
  fill(0);
  noStroke();

  // Initialise the ControlIO
  control = ControlIO.getInstance(this);
  
  // Find a device that matches the configuration file
  gpad = control.getMatchedDevice("full_gamePad");
  if (gpad == null) {
    println("No suitable device configured");
    System.exit(-1); // End the program NOW!
  }
}


// -------------
// Draw function
// -------------

public void draw() {
  getUserInput();
  drawParameters();

  // Convert analog coordinates to integer [-100:100]
  int joyStick1 = floor((analogX - analogY) * 50);
  int joyStick2 = floor((analogU - analogV) * 50);

  float val = randomGaussian();          // Call random number from Z~(0,1)
  float mean = width/2;                  // Define a mean value (middle of the screen along the x-axis)
  float x = (val * dispersion) + mean;   // Scale the gaussian random number by SD (dispersion) and mean

  ypos = ceil(random(0, height));
  xpos = floor(x);

  // Save work to ~/IMG_exports
  if (L2 & R2) { saveImage(); }


  // Reset ALL parameters
  if (L1 & R1) {background(0); dispersion = 100; alpha = 10; red = 100; green = 100; blue = 100;}
  // Reset color parameters to middle gray
  if (L2 & R2) {alpha = 10; red = 100; green = 100; blue = 100;}
  // Reset background only
  if (select1 & R1) {background(0);}


  // Random values
  if (M1 | L2) {
    red = floor(random(0, 255));
    green = floor(random(0, 255));
    blue = floor(random(0, 255));
    alpha = floor(random(0, 255));
  }
  if (M2 | R2) {
    brushSize = floor(random(0, 500));
    dispersion = floor(random(0, 500));    
  }


  // Brush size...
  if (L1 & right) {brushSize += increment;}
  if (L1 & left) {brushSize -= increment;}


  // Gaussian dispersion
  if (R1 & right) {dispersion += increment;}     
  if (R1 & left) {dispersion -= increment;}


  // Saturation
  if (select2 & (up | right | R1)) {red += increment; green += increment; blue += increment;}
  if (select2 & (down | left | L1)) {red -= increment; green -= increment; blue -= increment;}
  if (L1 & up & select2) {red = 0; green = 0; blue = 0;}


  // Transparency
  if (X & (up | right)) {alpha += increment;}
  if (X & (down | left | L1)) {alpha -= increment;}
  if (L1 & up & X) {alpha = 0;}
  // Analog control
  if (R1 & X) {
    alpha += joyStick1 / 25 * increment;
    fill(red, green, blue, alpha);
    ellipse(xpos, ypos, analogX * brushSize, analogY * brushSize);
  }


  // Colors mapped to action buttons
  if (Y & (up | right)) {red += increment;}      // RED
  if (Y & (down | left | L1)) {red -= increment;}
  if (L1 & up & Y) {red = 0; }
  // Analog control
  if (R1 & Y) {
    red += joyStick1 / 25 * increment;
    fill(red, green, blue, alpha);
    ellipse(xpos, ypos, analogX * brushSize, analogY * brushSize);
  }

  if (A & (up | right)) {green += increment;}    // GREEN
  if (A & (down | left | L1)) {green -= increment;}
  if (L1 & up & A) {green = 0;}
  // Analog control
  if (R1 & A) {
    green += joyStick1 / 25 * increment;
    fill(red, green, blue, alpha);
    ellipse(xpos, ypos, analogX * brushSize, analogY * brushSize);
  }

  if (B & (up | right)) {blue += increment;}     // BLUE
  if (B & (down | left | L1)) {blue -= increment;}
  if (L1 & up & B) {blue = 0;}
  // Analog control
  if (R1 & A) {
    blue += joyStick1 / 25 * increment;
    fill(red, green, blue, alpha);
    ellipse(xpos, ypos, analogX * brushSize, analogY * brushSize);
  }


  // Limit values to 0:255 RGB scale
  alpha = limitVal(alpha);
  red = limitVal(red);
  green = limitVal(green);
  blue = limitVal(blue);

  // Draw shapes
  fill(red, green, blue, alpha);
  ellipse(xpos, ypos, analogX * brushSize, analogY * brushSize);
  ellipse(xpos, ypos, analogV * brushSize, analogU * brushSize);


  // Diagnostics
  // println(dispersion, brushSize, alpha, green, red, blue);
}


// ----------
// Save image
// ----------

public void saveImage() {
  // Get datetime information
  int [] datetime = new int[6];
  datetime[0] = year();
  datetime[1] = month();
  datetime[2] = day();
  datetime[3] = hour();
  datetime[4] = minute();
  datetime[5] = second();

  // Create new datestamped file in the sketch directory
  save("IMG_exports/gamePad_sketch_" + join(nf(datetime, 0), "-") + ".png");  
}


// ----------------------------------
// Limit values to color scale, 0:255
// ----------------------------------

public int limitVal(int x) {
  if (x > 255)
    x = 255;
  if (x < 0)
    x = 0;
  return(x);
}


// ---------------
// Draw parameters
// ---------------

public void drawParameters() {
  int wB = 170;
  int wH = 13;
  int wX = width - wB;
  int wY = height - wH;
  int gap = 28;
  fill(0, 155);
  rect(wX, wY, wB, wH);

  String disp = str(dispersion);
  String a = str(alpha);
  String r = str(red);
  String g = str(green);
  String b = str(blue);
  String s = str(brushSize);

  textAlign(CENTER, TOP);
  textSize(9.5);

  fill(red, green, blue);
  text(a, wX+15, wY);             // alpha
  fill(255, 0, 0, 140);
  text(r, wX+15+(1*gap), wY);     // red
  fill(0, 255, 0, 140);
  text(g, wX+15+(2*gap), wY);     // green
  fill(0, 200, 255, 150);
  text(b, wX+15+(3*gap), wY);     // blue
  fill(255, 150);
  text(s, wX+15+(4*gap), wY);     // brush size
  fill(255, 150);
  text(disp, wX+15+(5*gap), wY);  // dispersion
}

// --------------
// Get user input
// --------------

public void getUserInput() {
  select1 = gpad.getButton("start").pressed();
  select2 = gpad.getButton("select").pressed();
  
  // Analog controller to position vars
  analogX =  gpad.getSlider("analogXL").getValue();
  analogY =  gpad.getSlider("analogYL").getValue();
  analogU =  gpad.getSlider("analogXR").getValue();
  analogV =  gpad.getSlider("analogYR").getValue();
  // Analog stick button presses
  M1 = gpad.getButton("M1").pressed();
  M2 = gpad.getButton("M2").pressed();
  
  // Action buttons
  A = gpad.getButton("a").pressed();
  B = gpad.getButton("b").pressed();
  X = gpad.getButton("x").pressed();
  Y = gpad.getButton("y").pressed();

  // LR triggers
  L1 = gpad.getButton("L1").pressed();
  L2 = gpad.getButton("L2").pressed();
  R1 = gpad.getButton("R1").pressed();
  R2 = gpad.getButton("R2").pressed();
  
  // D-Pad values
  left = gpad.getHat("dPad").left();
  right = gpad.getHat("dPad").right();
  up = gpad.getHat("dPad").up();
  down = gpad.getHat("dPad").down();
}