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

public class toggleGGPreview extends PApplet {

// File: toggleGGPreview.pde
// Author: m-ezekiel
// PS4-style controller digital painting





ControlIO control;
Configuration config;
ControlDevice gpad;

// KEYLOGGING DEFINITIONS
PrintWriter output;
int [] XYpos_array;
int [] ParamValue_array;
int [] Keypress_array;
float [] Analog_array;

// VARIABLE DEFINITIONS
float analogX, analogY, analogU, analogV;
boolean A1, A2, A3, A4, M1, M2, L1, L2, R1, R2;
boolean left, right, up, down, select1, select2;

boolean imageSaved;

// INITIALIZE PARAMETERS
boolean writeData = false;
boolean actionPad_pressed = false;

int xpos = 0; int ypos = 0;
int dpX = 300; int dpY = 300;
int increment = 2;
int scalar = 50;
int mScalar = scalar / 1;
int brushSize_X = 250;
int brushSize_Y = 250;
int red, blue, green = 0;
int alpha = 60;

int x_mean;
int y_mean;


float fps = 30;


// ASSIGN CONTROL MAPPINGS (variables numbered CCW from left)
int A1_ctrl = blue;
int A2_ctrl = green;
int A3_ctrl = red;
int A4_ctrl = alpha;

// SETUP
public void setup() {
  
  background(0);
  noStroke();

  x_mean = width / 2;
  y_mean = height / 2;

  frameRate(fps);

  // Initialise the ControlIO
  control = ControlIO.getInstance(this);
  
  // Find a device that matches the configuration file
  gpad = control.getMatchedDevice("full_gamePad");
  if (gpad == null) {
    println("No suitable device configured");
    System.exit(-1); // End the program NOW!
  }

 
}




// DRAW
public void draw() {

  getUserInput();

  // REFRESH CONTROL VALUES
  blue = A1_ctrl;
  green = A2_ctrl;
  red = A3_ctrl;
  alpha = A4_ctrl;


  // ANALOG TO INTEGER
  int joystick1 = analogToInteger(analogX, analogY, scalar);
  int joystick2 = analogToInteger(analogU, analogV, scalar);


  // GET COORDINATES
  xpos = gaussianInt(dpX, x_mean);
  ypos = gaussianInt(dpY, y_mean);


  // COLOR & OPACITY BEHAVIORS (d-pad)

  if ((down|left|L1) & A1) {A1_ctrl -= increment;}
  if ((down|left|L1) & A2) {A2_ctrl -= increment;}
  if ((down|left|L1) & A3) {A3_ctrl -= increment;}
  if ((down|left|L1) & A4) {A4_ctrl -= increment;}
  if ((up|right|R1) & A1) {A1_ctrl += increment;}
  if ((up|right|R1) & A2) {A2_ctrl += increment;}
  if ((up|right|R1) & A3) {A3_ctrl += increment;}
  if ((up|right|R1) & A4) {A4_ctrl += increment;}


  // SIZE BEHAVIORS (d-pad)

  if (up & R1) {brushSize_Y += increment * 15;}
  if (down & R1) {brushSize_Y -= increment * 15;}
  if (right & R1) {brushSize_X -= increment * 15;}
  if (left & R1) {brushSize_X += increment * 15;}


  // DISPERSION BEHAVIORS (d-pad)

  if (up & L1) {dpY += increment * 10;}
  if (down & L1) {dpY -= increment * 10;}
  if (right & L1) {dpX -= increment * 10;}
  if (left & L1) {dpX += increment * 10;}


  // POSITION BEHAVIORS (d-pad)

  if (up & select2) {y_mean += -increment * 15;}
  if (down & select2) {y_mean -= -increment * 15;}
  if (right & select2) {x_mean += increment * 15;}
  if (left & select2) {x_mean -= increment * 15;}


  // RANDOM BEHAVIORS: if (L2) {variable = randomInt(min, max);}
  actionPad_pressed = getActionPad();
  if ((L2 & !actionPad_pressed)) {
    brushSize_Y = randomInt(0, 999);
    brushSize_X = randomInt(0, 999);
    dpY = randomInt(0, 400);
    dpX = randomInt(0, 400);
    A1_ctrl = randomInt(0, 255);
    A2_ctrl = randomInt(0, 255);
    A3_ctrl = randomInt(0, 255);
  }

  // Localized random behaviors
  if ((L2 & A4)) A4_ctrl = randomInt(0, 200);
  if ((L2 & A2)) A2_ctrl = randomInt(0, 255);
  if ((L2 & A1)) A1_ctrl = randomInt(0, 255);
  if ((L2 & A3)) A3_ctrl = randomInt(0, 255);

  // ANALOG MODIFIERS

  if (A1 & (abs(joystick1) > 2)) {A1_ctrl += increment * joystick1 / mScalar;}
  if (A2 & (abs(joystick1) > 2)) {A2_ctrl += increment * joystick1 / mScalar;}
  if (A3 & (abs(joystick1) > 2)) {A3_ctrl += increment * joystick1 / mScalar;}
  if (A4 & (abs(joystick1) > 2)) {A4_ctrl += increment * joystick1 / mScalar;}

  if (A1 & (abs(joystick2) > 2)) {A1_ctrl += increment * joystick2 / mScalar;}
  if (A2 & (abs(joystick2) > 2)) {A2_ctrl += increment * joystick2 / mScalar;}
  if (A3 & (abs(joystick2) > 2)) {A3_ctrl += increment * joystick2 / mScalar;}
  if (A4 & (abs(joystick2) > 2)) {A4_ctrl += increment * joystick2 / mScalar;}

  // Brush Size 
  if (R1 & (abs(analogX) > 0.15f)) {brushSize_X += -analogX * 15 * increment;}
  if (R1 & (abs(analogY) > 0.15f)) {brushSize_Y += -analogY * 15 * increment;}
  if (R1 & (abs(analogU) > 0.15f)) {brushSize_X += analogU * 15 * increment;}
  if (R1 & (abs(analogV) > 0.15f)) {brushSize_Y += -analogV * 15 * increment;}

  // Dispersion 
  if (L1 & (abs(analogX) > 0.15f)) {dpX += -analogX * 15 * increment;}
  if (L1 & (abs(analogY) > 0.15f)) {dpY += -analogY * 15 * increment;}
  if (L1 & (abs(analogU) > 0.15f)) {dpX += analogU * 15 * increment;}
  if (L1 & (abs(analogV) > 0.15f)) {dpY += -analogV * 15 * increment;}

  // Position
  if((select1|select2) & (abs(analogX) > 0.15f)) {x_mean += analogX * 15 * increment;}
  if((select1|select2) & (abs(analogY) > 0.15f)) {y_mean += analogY * 15 * increment;}
  if((select1|select2) & (abs(analogU) > 0.15f)) {x_mean += analogU * 15 * increment;}
  if((select1|select2) & (abs(analogV) > 0.15f)) {y_mean += analogV * 15 * increment;}

  // Map D-Pad to colors when Joystick 2 is activated
  if (left & (abs(joystick2) > 2)) {A1_ctrl += increment * joystick2 / mScalar;}
  if (down & (abs(joystick2) > 2)) {A2_ctrl += increment * joystick2 / mScalar;}
  if (right & (abs(joystick2) > 2)) {A3_ctrl += increment * joystick2 / mScalar;}
  if (up & (abs(joystick2) > 2)) {A4_ctrl += increment * joystick2 / mScalar;}



  // MUTE VALUE: if (R2 & button) {variable = 0}

  if ((R2) & A1) {A1_ctrl = 0;}
  if ((R2) & A2) {A2_ctrl = 0;}
  if ((R2) & A3) {A3_ctrl = 0;}
  if ((R2) & A4) {A4_ctrl = 0;}

  if (R2 & L1) {
    brushSize_X = 250;
    brushSize_Y = 250;
  }
  if (R2 & R1) {
    dpX = 300;
    dpY = 300;
  }
  if (R2 & (select1|select2)) {
    x_mean = width/2;
    y_mean = height/2;
  }



  // RESET BACKGROUND

  if (R2 & up) {resetBlack();}
  if (R2 & down) {resetWhite();}
  if (R2 & right) {resetColor();}
  if (R2 & left) {resetInverse();}


  // LIMIT SCALE

  A1_ctrl = limitScale(A1_ctrl, 0, 255);
  A2_ctrl = limitScale(A2_ctrl, 0, 255);
  A3_ctrl = limitScale(A3_ctrl, 0, 255);
  A4_ctrl = limitScale(A4_ctrl, 0, 255);
  dpY = limitScale(dpY, 0, 400);
  dpX = limitScale(dpX, 0, 400);
  brushSize_Y = limitScale(brushSize_Y, 1, 999);
  brushSize_X = limitScale(brushSize_X, 1, 999);
  xpos = limitScale(xpos, 0, width);
  ypos = limitScale(ypos, 0, height);
  // x_mean = limitScale(x_mean, 0, width);
  // y_mean = limitScale(y_mean, 0, height);






  togglePreview();
  // draw_ctrlStrobe(); 
  // drawShapes();

  // DIAGNOSTICS
  // println(XYpos_array[0], XYpos_array[1]);

}
/*
File: GGpreview_functions.pde
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
// Toggle preview
// ---------------

public void togglePreview() {
  int w = width;
  int h = height;

  // Outer window
  int owB = w/8; // 160
  float owH = owB/2.5f; // 60
  int owX = width - owB;
  int owY = 0;
  int gap = w/45; // 28
  // Inner window
  float iwB = owB/3.125f;
  float iwH = iwB * 0.625f;
  // Centerpoints
  float owCX = owX + (owB/2);
  float owCY = owY + (owH/2);
  float iwCX = owX + owB/12 + iwB/2;
  float iwCY = owY + owH/6 + iwH/2;  
  // Parameter values
  String opacity = str(alpha);
  String rd = str(red);
  String grn = str(green);
  String blu = str(blue);
  String x_mn = str(x_mean);
  String y_mn = str(y_mean);
  String dX = str(dpX/4);
  String dY = str(dpY/4);

  int brushScale = w/45;

  // Head Up Display (outer window)
  fill(0, 155);
  rect(owX, owY, owB, owH);


  // Prototype for inner window preview
  stroke(100);
  rect(owX + owB/12, owY + owH/6, iwB, iwH);
  noStroke();


  stroke(150);
  fill(red, green, blue, 127);

  // X-DISPERSION
  ellipse(iwCX - dpX/20 + (x_mean - width/2)/25, iwCY + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  ellipse(iwCX + dpX/20 + (x_mean - width/2)/25, iwCY + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  // Y-DISPERSION
  ellipse(iwCX + (x_mean - width/2)/25, iwCY - dpY/30 + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);
  ellipse(iwCX + (x_mean - width/2)/25, iwCY + dpY/30 + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  // Brush pigment colored ellipse
  noStroke();
  fill(red, green, blue, alpha*3);
  ellipse(owCX + gap + gap/11, owCY - gap/11, gap/2, gap/2);


  // Display color values according to controller position
  textAlign(CENTER, TOP);
  textSize(owB/15);
  // Color Values
  fill(200);
  text(opacity, owCX + gap + gap/11, owCY - gap/1.1f);

  fill(0, 255, 0, 255);
  text(grn, owCX + gap + gap/11, owCY + gap/3);

  fill(0, 200, 255, 255);
  text(blu, owCX + gap/5, owCY - gap/3);

  fill(255, 0, 0, 255);
  text(rd, owCX + (2*gap), owCY - gap/3);

  // Position Values
  fill(180);
  text(x_mn, owCX - (2*gap), owCY + gap/2);
  text(y_mn, owCX - (1*gap), owCY + gap/2);


  // IMAGE SAVED
  if (imageSaved == true) {
    fill(255);
    text("SAVED", owCX, owCY - 20); 
  }

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

public int gaussianInt(int dispersion, int mean) {

  float rGauss = randomGaussian();  // Random number from Z~(0,1)
  int value = floor((rGauss * dispersion) + mean);

  return(value);
}




// -----------------
// Get analog values
// -----------------

public float [] getAnalogValues() {
  float [] AV_array = new float[4];  

  AV_array[0] = analogX;
  AV_array[1] = analogY;
  AV_array[2] = analogU;
  AV_array[3] = analogV;

  return(AV_array);
}


// ---------------------------------------
// Get parameter values (color, disp, size)
// ----------------------------------------

public int [] getParamValues() {
  int [] DV_array = new int[8];  

  DV_array[0] = red;
  DV_array[1] = green;
  DV_array[2] = blue;
  DV_array[3] = alpha;

  DV_array[4] = dpX;
  DV_array[5] = dpY;
  DV_array[6] = brushSize_X;
  DV_array[7] = brushSize_Y;

  return(DV_array);
}






// ------------------------
// Get position coordinates
// ------------------------

public int [] getXYpos() {
  int [] XYpos = new int[2];

  XYpos[0] = xpos;
  XYpos[1] = ypos;

  return(XYpos);
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



// -----------------
// Get direction pad
// -----------------

public boolean getDPad() {
  boolean value = false;

  // If buttons are pressed then value is false
  if (left | right | up | down)
    value = true;

  return(value);
}


// --------------
// Get action pad
// --------------

public boolean getActionPad() {
  boolean value = false;

  // If buttons are pressed then value is false
  if (A1 | A2 | A3 | A4)
    value = true;

  return(value);
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
  background(0);
}
public void resetWhite() {
  background(255);
}
public void resetColor() {
  background(red, green, blue, alpha);
}
public void resetInverse() {
  background(255 - red, 255 - green, 255 - blue, 255 - alpha);
}








  public void settings() {  size(1920, 1200); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "toggleGGPreview" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
