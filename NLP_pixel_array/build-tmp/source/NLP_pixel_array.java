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

public class NLP_pixel_array extends PApplet {

// Filename: NLP_pixel_array.pde
// Description: Takes a binary matrix as input and displays a black and white "pixel print"
// visualization.

// 2D Array of objects
Cell[][] grid;

// Number of columns and rows in the grids
int cols = 5;
int rows = 5;

// Table
Table table;
int value;

public void setup() {
  table = loadTable("save_matrix.txt", "header, csv");
  println(table.getRowCount() + " total rows in table");
  
  

  grid = new Cell[cols][rows];
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      // Initialize each object
      grid[i][j] = new Cell(i*40,j*40,40,40,i+j);
    }
  }
}

public void draw() {

  background(0);

  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      value = table.getInt(i, j);
      
      if (value == 0) {
        fill(0);
        grid[j][i].display();
      }
      
      if (value == 1) {
        fill(255);
        grid[j][i].display();
      }
    }
  }

  save("image.png");
}

// A Cell object
class Cell {
  // A cell object knows about its location in the grid 
  // as well as its size with the variables x,y,w,h
  float x,y;   // x,y location
  float w,h;   // width and height
  float angle; // angle for oscillating brightness

  // Cell Constructor
  Cell(float tempX, float tempY, float tempW, float tempH, float tempAngle) {
    x = tempX;
    y = tempY;
    w = tempW;
    h = tempH;
    angle = tempAngle;
  } 
  
  // Oscillation means increase angle
  public void oscillate() {
    angle += 0.02f; 
  }

  public void display() {
    stroke(255);
    // Color calculated using sine wave
    //fill(127+127*sin(angle));
    rect(x,y,w,h); 
  }
}
  public void settings() {  size(201,201); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "NLP_pixel_array" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
