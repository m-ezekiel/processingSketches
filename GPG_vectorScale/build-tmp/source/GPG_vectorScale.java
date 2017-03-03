import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GPG_vectorScale extends PApplet {

// File: GPG_vectorScale.pde
// Author: m-ezekiel
// Playback and image export of Gamepad Gaussians data files

Table table;
int index = 0;

// VARIABLE DEFINITIONS
float analogX, analogY, analogU, analogV;
boolean A1, A2, A3, A4, M1, M2, L1, L2, R1, R2;
boolean left, right, up, down, select1, select2;

// INITIALIZE PARAMETERS
int xpos = 0; int ypos = 0;
int dpX = 300; int dpY = 300;
int increment = 2;
int scalar = 50;
int mScalar = scalar / 1;
int brushSize_X = 350;
int brushSize_Y = 350;
int red, blue, green = 0;
int alpha = 60;


public void setup() {
  
  background(0);
  noStroke();
  frameRate(60);
  table = loadTable("2017-3-2-19-28-45_gamepadKeys.txt", "header, tsv");
  println(table.getRowCount() + " total rows in table");
  println(table.getColumnCount() + " total columns in table");
}


public void draw() {

  // GET DATA
  xpos = table.getInt(index, "xpos");
  ypos = table.getInt(index, "ypos");
  brushSize_X = table.getInt(index, "sX");
  brushSize_Y = table.getInt(index, "sY");
  dpX = table.getInt(index, "dpX");
  dpY = table.getInt(index, "dpY");
  red = table.getInt(index, "red");
  green = table.getInt(index, "green");
  blue = table.getInt(index, "blue");
  alpha = table.getInt(index, "opacity");
  analogX = table.getFloat(index, "anlgX");
  analogY = table.getFloat(index, "anlgY");
  analogU = table.getFloat(index, "anlgU");
  analogV = table.getFloat(index, "anlgV");

  // Add save frame and exit.
  // if (index == lines.length) { exit(); }

  if (index < table.getRowCount() - 1) {
    // assign variable values with appropriate indexes

    drawShapes();

    index = index + 1; 
  } 
}


public void drawShapes() {
  fill(red, green, blue, alpha);
  ellipse(xpos, ypos, analogX * brushSize_X, analogY * brushSize_Y);
  ellipse(xpos, ypos, analogU * brushSize_X, analogV * brushSize_Y);
}
  public void settings() {  size(1280, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GPG_vectorScale" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
