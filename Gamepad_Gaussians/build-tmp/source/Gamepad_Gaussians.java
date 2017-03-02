import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.gamecontrolplus.gui.*; 
import org.gamecontrolplus.*; 
import net.java.games.input.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Gamepad_Gaussians extends PApplet {

// File: Gamepad_Gaussians.pde
// Author: m-ezekiel
// PS4-style controller digital painting





ControlIO control;
Configuration config;
ControlDevice gpad;


// VARIABLE DEFINITIONS
float analogX, analogY, analogU, analogV;
boolean A1, A2, A3, A4, M1, M2, L1, L2, R1, R2;
boolean left, right, up, down, select1, select2;


// INITIALIZE PARAMETERS
int xpos = 0; int ypos = 0;
int dpX = 400; int dpY = 400;
int increment = 2;
int scalar = 50;
int mScalar = scalar / 1;
int size = 300;
int fps = 24;
int red, blue, green = 0;
int alpha = 0;

// ASSIGN CONTROL MAPPINGS (variables numbered CCW from left)
int A1_ctrl = red;
int A2_ctrl = green;
int A3_ctrl = blue;
int A4_ctrl = alpha;
// int R2_ctrl = size;
// int S1_ctrl = dpY;
// int S2_ctrl = dpX;

public void setup() {
  
  background(alpha);
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


public void draw() {

  getUserInput();

  // REFRESH CONTROL VALUES
  red = A1_ctrl;
  green = A2_ctrl;
  blue = A3_ctrl;
  alpha = A4_ctrl;
  // size = R2_ctrl;
  // dpY = S1_ctrl;
  // dpX = S2_ctrl;

  // ANALOG TO INTEGER
  int joystick1 = analogToInteger(analogX, analogY, scalar);
  int joystick2 = analogToInteger(analogU, analogV, scalar);


  // GET COORDINATES
  xpos = gaussianInt(dpX, width);
  ypos = gaussianInt(dpY, height);


  // INCREMENTAL BEHAVIORS

  if ((down|left|L1) & A1) {A1_ctrl -= increment;}
  if ((down|left|L1) & A2) {A2_ctrl -= increment;}
  if ((down|left|L1) & A3) {A3_ctrl -= increment;}
  if ((down|left|L1) & A4) {A4_ctrl -= increment;}
  if ((up|right|R1) & A1) {A1_ctrl += increment;}
  if ((up|right|R1) & A2) {A2_ctrl += increment;}
  if ((up|right|R1) & A3) {A3_ctrl += increment;}
  if ((up|right|R1) & A4) {A4_ctrl += increment;}

  // SIZE BEHAVIORS... possibly add d-pad mods
  if ((R1|up|right) & select1) {size += increment * 5;}
  if ((L1|down|left) & select1) {size -= increment * 5;}

  // DISPERSION BEHAVIORS (d-pad)... possibly add R1 & L1 mods
  if (up & select2) {dpY += increment * 5;}
  if (down & select2) {dpY -= increment * 5;}
  if (right & select2) {dpX += increment * 5;}
  if (left & select2) {dpX -= increment * 5;}

  if (R1 & select2) {
    dpY += increment * 5;
    dpX += increment * 5;
  }
  if (L1 & select2) {
    dpY -= increment * 5;
    dpX -= increment * 5;
  }

  // RANDOM BEHAVIORS: if (R1 & button) {variable = randomInt(min, max);}

  if ((L2) & A1) {A1_ctrl = randomInt(0, 255);}
  if ((L2) & A2) {A2_ctrl = randomInt(0, 255);}
  if ((L2) & A3) {A3_ctrl = randomInt(0, 255);}
  // Limit the range of opacity in random calls
  if ((L2) & A4) {A4_ctrl = randomInt(0, 127);}

  if ((L2) & select1) {size = randomInt(0, 1000);}
  if ((L2) & select2) {
    dpY = randomInt(0, 400);
    dpX = randomInt(0, 400);
  }

  // MUTE VALUE w/R2

  if ((R2) & A1) {A1_ctrl = 0;}
  if ((R2) & A2) {A2_ctrl = 0;}
  if ((R2) & A3) {A3_ctrl = 0;}
  if ((R2) & A4) {A4_ctrl = 0;}
  if ((R2) & select1) {size = 0;}
  if ((R2) & select2) {
    dpY = 0;
    dpX = 0;
  }


  // ANALOG MODIFIERS

  if (A1 & (abs(joystick1) > 2)) {A1_ctrl += increment * joystick1 / mScalar;}
  if (A2 & (abs(joystick1) > 2)) {A2_ctrl += increment * joystick1 / mScalar;}
  if (A3 & (abs(joystick1) > 2)) {A3_ctrl += increment * joystick1 / mScalar;}
  if (A4 & (abs(joystick1) > 2)) {A4_ctrl += increment * joystick1 / mScalar;}

  if (A1 & (abs(joystick2) > 2)) {A1_ctrl += increment * joystick2 / mScalar;}
  if (A2 & (abs(joystick2) > 2)) {A2_ctrl += increment * joystick2 / mScalar;}
  if (A3 & (abs(joystick2) > 2)) {A3_ctrl += increment * joystick2 / mScalar;}
  if (A4 & (abs(joystick2) > 2)) {A4_ctrl += increment * joystick2 / mScalar;}

  if(select2 & (abs(analogX) > 0.1f)) {dpX += analogX * 10 * increment;}
  if(select2 & (abs(analogY) > 0.1f)) {dpY += -analogY * 10 * increment;}
  if(select2 & (abs(analogU) > 0.2f)) {dpX += analogU * 10 * increment;}
  if(select2 & (abs(analogV) > 0.1f)) {dpY += -analogV * 10 * increment;}

  if(select1 & (abs(analogU) > 0.1f)) {size += analogU * 10 * increment;}
  if(select1 & (abs(analogV) > 0.1f)) {size += -analogV * 10 * increment;}


  // GAMEPLAY FUNCTIONS

  if (M1 & M2) {saveImage();}
  if (up & L2 & R2) {resetBlack();}
  if (down & L2 & R2) {resetWhite();}

  // LIMIT SCALE
  A1_ctrl = limitScale(A1_ctrl, 0, 255);
  A2_ctrl = limitScale(A2_ctrl, 0, 255);
  A3_ctrl = limitScale(A3_ctrl, 0, 255);
  A4_ctrl = limitScale(A4_ctrl, 0, 255);
  dpY = limitScale(dpY, 0, 400);
  dpX = limitScale(dpX, 0, 400);
  size = limitScale(size, 0, 1000);

  drawShapes();
  drawParameters();
  // saveFrame();

  // DIAGNOSTICS
  println(joystick1, joystick2, analogX, analogY, analogU, analogV);
}
// Control class

class Control {
    // ASSIGN CONTROL MAPPINGS
    int A1_ctrl = red;
    int A2_ctrl = green;
    int A3_ctrl = blue;
    int A4_ctrl = alpha;

    int S1_ctrl = size;

    int UP_ctrl = dpY;
    int DN_ctrl = dpY;
    int LF_ctrl = dpX;
    int RT_ctrl = dpX;



    public void increment() {}
    public void decrement() {}
}

// button.increment
// button.decrement
// button.random
// button.mute
/*
File: GG_functions.pde
Author: m-ezekiel
Desc.: Function script for Gamepad Gaussians
*/


// -----------------
// Analog to integer
// -----------------

public int analogToInteger(float x, float y, int k) {
  int value = floor((x - y) * k);
  return(value);
}



// ---------------
// Draw parameters
// ---------------

public void drawParameters() {
  int wB = 150;
  int wH = 50;
  int wX = width - wB;
  int wY = 0;
  int gap = 28;
  fill(0, 155);
  rect(wX, wY, wB, wH);

  int centerPointX = wX + (wB/2);
  int centerPointY = wY + (wH/2);

  String dX = str(dpX/4);
  String dY = str(dpY/4);
  String sz = str(size/10);

  String a = str(alpha);
  String r = str(red);
  String g = str(green);
  String b = str(blue);

  textAlign(CENTER, TOP);
  textSize(11);

  fill(red, green, blue);
  text(a, centerPointX + 4 + (1*gap), centerPointY - 20);      // alpha
  fill(0, 255, 0, 140);
  text(g, centerPointX + 4 + (1*gap), centerPointY + 10);      // green
  fill(255, 0, 0, 140);
  text(r, centerPointX + 8, centerPointY - 5);           // red
  fill(0, 200, 255, 150);
  text(b, centerPointX + (2*gap), centerPointY - 5);     // blue


  stroke(100);
  fill(red, green, blue, alpha);

  // X-DISPERSION
  line(centerPointX - (1.5f*gap) - dpX/20, centerPointY, centerPointX - (1.5f*gap) + dpX/20, centerPointY);
  ellipse(centerPointX - (1.5f*gap) - dpX/20, centerPointY, size/50, size/50);
  ellipse(centerPointX - (1.5f*gap) + dpX/20, centerPointY, size/50, size/50);

  // Y-DISPERSION
  line(centerPointX - (1.5f*gap), centerPointY - dpY/30, centerPointX - (1.5f*gap), centerPointY + dpY/30);
  ellipse(centerPointX - (1.5f*gap), centerPointY - dpY/30, size/50, size/50);
  ellipse(centerPointX - (1.5f*gap), centerPointY + dpY/30, size/50, size/50);


  noStroke();
}



// -----------
// Draw shapes
// -----------

public void drawShapes() {
  fill(red, green, blue, alpha);
  ellipse(xpos, ypos, analogX * size, analogY * size);
  ellipse(xpos, ypos, analogV * size, analogU * size);
}



// ----------------------------------
// Limit values to variable scale a:b
// ----------------------------------

public int limitScale(int x, int min, int max) {
  if (x > max)
    x = max;
  if (x < min)
    x = min;
  return(x);
}



// ----------------
// Gaussian integer
// ----------------

public int gaussianInt(int dispersion, int dimension) {

  float rGauss = randomGaussian();  // Random number from Z~(0,1)
  float mean = dimension / 2;
  int value = floor((rGauss * dispersion) + mean);

  return(value);
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
  A1 = gpad.getButton("a").pressed();
  A2 = gpad.getButton("b").pressed();
  A3 = gpad.getButton("x").pressed();
  A4 = gpad.getButton("y").pressed();

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




// -----------
// Mute colors
// -----------

public void muteAlpha() {
  // alpha = 0;
  A4_ctrl = 0;
  increment = 2;
}



// --------------
// Random integer
// --------------

public int randomInt(int a, int b) {
  int value = floor(random(a, b));
  return(value);
}



// -----
// Reset
// -----

public void resetBlack() {
  red = 0; green = 0; blue = 0; alpha = 0;
  background(0);
}
public void resetWhite() {
  red = 0; green = 0; blue = 0; alpha = 0;
  background(255);
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

// Variable class

class Button {
    // begin

    public void increment() {}
    public void decrement() {}
}

// button.increment
// button.decrement
// button.random
// button.mute
  public void settings() {  size(1280, 750); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Gamepad_Gaussians" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
