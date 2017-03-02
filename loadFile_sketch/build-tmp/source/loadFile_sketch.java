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

public class loadFile_sketch extends PApplet {

// Anonymous Love Letters
// Author: m-ezekiel

PFont f;

String[] lines;
int index = 0;
int gap = 10;
int x_pos = 10;
int y_pos = 10;


public void setup() {
  
  background(0);
  stroke(255);
  // frameRate(24);
  lines = loadStrings("anonLetter.txt");

  // Create the font
  printArray(PFont.list());
  f = loadFont("Monospaced-14.vlw");
  textFont(f);
  textAlign(LEFT, TOP);
}

public void draw() {
  frameRate(24);
  // Initialize string array from word vector
  String[] word_vec = split(lines[index], '\t');

  // This is some weird hacky code, but it works.
  if (index == lines.length) { exit(); }

  if (index < lines.length - 1) {
    // println(word_vec[0]);
    // println(word_vec[1]);
    // println(word_vec[2]);
    // println(word_vec[0].length());
    int word_len = word_vec[0].length();
    float char_IKI = PApplet.parseFloat(word_vec[1]);

    if (char_IKI > 0.3f) {frameRate(3);}

    drawType(word_vec, x_pos, y_pos);
    x_pos += word_len*8 + gap;
    filter(BLUR, 1);

    if (x_pos > width - 60) {
      x_pos = 10;
      y_pos += 20;
    }
    index = index + 1; 
  } 
}

public void drawType(String[] s, int x, int y) {
  text(s[0], x, y);
}
class Record {
  String name;
  float mpg;
  int cylinders;
  float displacement;
  float horsepower;
  float weight;
  float acceleration;
  int year;
  float origin;

  public Record(String[] pieces) {
    name = pieces[0];
    mpg = PApplet.parseFloat(pieces[1]);
    cylinders = PApplet.parseInt(pieces[2]);
    displacement = PApplet.parseFloat(pieces[3]);
    horsepower = PApplet.parseFloat(pieces[4]);
    weight = PApplet.parseFloat(pieces[5]);
    acceleration = PApplet.parseFloat(pieces[6]);
    year = PApplet.parseInt(pieces[7]);
    origin = PApplet.parseFloat(pieces[8]);
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "loadFile_sketch" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
