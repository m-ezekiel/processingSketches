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

public class controllerStrobe extends PApplet {

// File: controllerStrobe.pde
// Author: m-ezekiel
// PS4-style controller digital painting





ControlIO control;
Configuration config;
ControlDevice gpad;

// VARIABLE DEFINITIONS
float analogX, analogY, analogU, analogV;
boolean A1, A2, A3, A4, M1, M2, L1, L2, R1, R2;
boolean left, right, up, down, select1, select2;

boolean imageSaved;

// INITIALIZE PARAMETERS
boolean writeData = false;

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
  stroke(175);
  rectMode(CENTER);
  ellipseMode(CENTER);

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
  background(0);
  getUserInput();
  draw_wireframe();
  lightTheStrobes();
  draw_AnlgPos();
  print_diagnostics();

  // togglePreview();
}



/*
File: GG_functions.pde
Author: m-ezekiel
Desc.: Function script for Gamepad Gaussians
*/



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
// Print diagnostics
// -----------------

public void print_diagnostics() {

  if(up | down | left | right)
    println("D-pad pressed");

  if(A1 | A2 | A3 | A4)
    println("Action buttons pressed");

  if(L1 | L2)
    println("Left triggers pressed");

  if(R1 | R2)
    println("Right triggers pressed");

  if(M1 | M2)
    println("Missile buttons pressed");

  if (select1 | select2)
    println("Select buttons pressed");

}


// --------------
// Draw wireframe
// --------------

public void draw_wireframe() {
  float w = width;
  float h = height;

  float trigger_width = w*5/16;
  float btm_height = h*5/36;
  float top_height = h*3/36;

  float t2_topMargin = h/8;
  float t1_topMargin = h/3.8f;
  float trigger_sideMargin = w/5;

  float upDown_x = w/5.5f;
  float upDown_y = h/2.5f;
  float square_dim = h/9;

  float select_height = h/2.25f;

  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3f;


  fill(0);

  // Left triggers: L2, L1
  rect(trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  rect(trigger_sideMargin, t1_topMargin, trigger_width, top_height);

  // Right triggers: R2, R1
  rect(width - trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  rect(width - trigger_sideMargin, t1_topMargin, trigger_width, top_height);

  // Direction-Pad: Up, Down, Left, Right
  rect(upDown_x, upDown_y, square_dim, square_dim);
  rect(upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  rect(upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  rect(upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);

  // Action-pad: Up, Dn, Lf, Rt
  ellipse(width - upDown_x, upDown_y, square_dim, square_dim);
  ellipse(width - upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  ellipse(width - upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  ellipse(width - upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);

  // Select buttons: select1, select2
  ellipse(w/2.3f, select_height, square_dim/1.2f, square_dim/2);
  ellipse(w/1.7f, select_height, square_dim/1.2f, square_dim/2);

  // Analog Controllers Sketch
  ellipse(anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5f, square_dim*2.5f);
  ellipse(w - anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5f, square_dim*2.5f);

}



// -----------------
// Light the strobes
// -----------------

public void lightTheStrobes() {
  float w = width;
  float h = height;

  float trigger_width = w*5/16;
  float btm_height = h*5/36;
  float top_height = h*3/36;

  float t2_topMargin = h/8;
  float t1_topMargin = h/3.8f;
  float trigger_sideMargin = w/5;

  float upDown_x = w/5.5f;
  float upDown_y = h/2.5f;
  float square_dim = h/9;

  float select_height = h/2.25f;

  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3f;


  fill(255);


  // Bottom triggers
  if (L2) rect(trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  if (R2) rect(width - trigger_sideMargin, t2_topMargin, trigger_width, btm_height);


  // Top triggers
  if (L1) rect(trigger_sideMargin, t1_topMargin, trigger_width, top_height);
  if (R1) rect(width - trigger_sideMargin, t1_topMargin, trigger_width, top_height);


  // D-Pad: Up, Down, Left, Right
  if (up) rect(upDown_x, upDown_y, square_dim, square_dim);
  if (down) rect(upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  if (left) rect(upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  if (right) rect(upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);


  // Action buttons: Up, Dn, Lf, Rt (A4, A2, A1, A3 by logitech ctrl labels)
  if (A4) ellipse(width - upDown_x, upDown_y, square_dim, square_dim);
  if (A2) ellipse(width - upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  if (A1) ellipse(width - upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  if (A3) ellipse(width - upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);


  // Select buttons
  if (select1) ellipse(w/2.3f, select_height, square_dim/1.2f, square_dim/2);
  if (select2) ellipse(w/1.7f, select_height, square_dim/1.2f, square_dim/2);


  // Analog controllers
  fill(60);
  if (abs(analogX) > 0.15f  || abs(analogY) > 0.15f) 
    ellipse(anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5f, square_dim*2.5f);
  if (abs(analogU) > 0.15f || abs(analogV) > 0.15f)
    ellipse(w - anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5f, square_dim*2.5f);
  fill(0);

}


// --------------------
// Draw analog position
// --------------------

public void draw_AnlgPos() {
  float w = width;
  float h = height;
  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3f;
  float reticle = w/20;
  float anlg_scale = w/20;

  // Left controller
  if (abs(analogX) > 0.15f  || abs(analogY) > 0.15f) {
    fill(255);
    // draw reticle in opaque white
    ellipse(anlg_Strobe_x + analogX*anlg_scale, anlg_Strobe_y + analogY*anlg_scale, reticle, reticle);
  } else {
    fill(0);
    // draw reticle in transparent black
    ellipse(anlg_Strobe_x + analogX*anlg_scale, anlg_Strobe_y + analogY*anlg_scale, reticle, reticle);
  }

  // Right controller
  if (abs(analogU) > 0.15f || abs(analogV) > 0.15f) {
    fill(255);
    ellipse(w - anlg_Strobe_x + analogU*anlg_scale, anlg_Strobe_y + analogV*anlg_scale, reticle, reticle);
  } else {
    fill(0);
    ellipse(w - anlg_Strobe_x + analogU*anlg_scale, anlg_Strobe_y + analogV*anlg_scale, reticle, reticle);
  }

}


  public void settings() {  size(300, 225); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "controllerStrobe" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
