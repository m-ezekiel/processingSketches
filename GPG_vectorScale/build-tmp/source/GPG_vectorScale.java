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
int brushSize_X = 350;
int brushSize_Y = 350;
int red, blue, green = 0;
int alpha = 60;

int scaleFactor = 8;


public void setup() {
  
  background(0);
  noStroke();
  frameRate(60);
  table = loadTable("2017-3-2-19-58-11_gamepadKeys.txt", "header, tsv");
  println(table.getRowCount() + " total rows in table");
  println(table.getColumnCount() + " total columns in table");
}


public void draw() {

  // Use cases to determine the value of a scalar
  if (width == 1280)
    println("yes! 1280 ");

  // GET DATA
  xpos = table.getInt(index, "xpos") * scaleFactor;
  ypos = table.getInt(index, "ypos") * scaleFactor;
  brushSize_X = table.getInt(index, "sX") * scaleFactor;
  brushSize_Y = table.getInt(index, "sY") * scaleFactor;
  dpX = table.getInt(index, "dpX") * scaleFactor;
  dpY = table.getInt(index, "dpY") * scaleFactor;
  red = table.getInt(index, "red");
  green = table.getInt(index, "green");
  blue = table.getInt(index, "blue");
  alpha = table.getInt(index, "opacity");
  analogX = table.getFloat(index, "anlgX");
  analogY = table.getFloat(index, "anlgY");
  analogU = table.getFloat(index, "anlgU");
  analogV = table.getFloat(index, "anlgV");

  if (index < table.getRowCount() - 1) {
    drawShapes();
    index = index + 1; 
  }

  println("index: "+index);

  // Add save frame and exit.
  if (index == table.getRowCount() - 1) {
    saveFrame("hd_image.png");
    exit();
  }
}


public void drawShapes() {
  fill(red, green, blue, alpha);
  ellipse(xpos, ypos, analogX * brushSize_X, analogY * brushSize_Y);
  ellipse(xpos, ypos, analogU * brushSize_X, analogV * brushSize_Y);
}
  public void settings() {  size(10240, 6400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GPG_vectorScale" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
