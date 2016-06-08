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

public class keyPress_prototype extends PApplet {

// Filename: keyPress_prototype.pde (Originally 'Text Box Writer')
// Author: m-ezekiel
// Based on 'Text Box Writer' by Inarts and GoToLoop
// Keylogging prototype using text processing tools.

static final int NUM = 2;						// Define the number of text boxes.
final TextBox[] tboxes = new TextBox[NUM];		// Call the constructors for TextBox class. 
int idx;
 
// ****************************************************
// Set window size, framerate, and anti-aliasing level.
// ****************************************************

public void setup() {
  
  frameRate(20);
  
 
  rectMode(CORNER);
  textAlign(LEFT);
  strokeWeight(1.5f);
 
  instantiateBoxes();
  tboxes[idx = 1].isFocused = true;
}


// **************
// Draw function.
// **************
 
public void draw() {
  background(0xff778C85);
  for (int i = 0; i != NUM; tboxes[i++].display());
}

 
// *************************************************************
// Use mouseClicked() to indicate intended text box destination.
// *************************************************************

public void mouseClicked() {
  int i = idx = -1;
  while (++i != NUM)  if (tboxes[i].checkFocus())  idx = i;
}


// ************************************************
// Define the coded keys (alphanumeric and symbol)
// and whitespace and deletion keys.
// ************************************************
 
public void keyTyped() {
  final char k = key;
  if (k == CODED | idx < 0)  return;
 
  final TextBox tbox = tboxes[idx];
  final int len = tbox.txt.length();
 
  if (k == BACKSPACE)  tbox.txt = tbox.txt.substring(0, max(0, len-1));
  else if (len >= tbox.lim)  return;
  else if (k == ENTER | k == RETURN)     tbox.txt += "\n";
  else if (k == TAB & len < tbox.lim-3)  tbox.txt += "    ";
  else if (k == DELETE)  tbox.txt = "";
  else if (k >= ' ')     tbox.txt += str(k);
}


// ************************************************
// Define LEFT and RIGHT arrow keys for navigation.
// ************************************************

public void keyPressed() {
  if (key != CODED | idx < 0)  return;
  final int k = keyCode;
 
  final TextBox tbox = tboxes[idx];
  final int len = tbox.txt.length();
 
  if (k == LEFT)  tbox.txt = tbox.txt.substring(0, max(0, len-1));
  else if (k == RIGHT & len < tbox.lim-3)  tbox.txt += "    ";
}


// ********************************************************
// Define dimension and color parameters of the Text Boxes.
// ********************************************************

public void instantiateBoxes() {
  tboxes[0] = new TextBox(
  width>>2, height/4 + height/16, // x, y
  width - width/2, height/2 - height/4 - height/8, // w, h
  215, // lim
  0300 << 030, color(-1, 040), // textC, baseC
  color(-1, 0100), color(0xffFF00FF, 0200)); // bordC, slctC
 
  tboxes[1] = new TextBox(
  width>>3, height/2 + height/8, // x, y
  width - width/4, height - height/2 - height/4, // w, h
  640, // lim
  0300 << 030, color(-1, 040), // textC, baseC
  color(-1, 0100), color(0xffFFFF00, 0200)); // bordC, slctC
}


// *************************************
// Define methods for the TextBox class.
// *************************************

class TextBox {  // demands rectMode(CORNER)
  final int textC, baseC, bordC, slctC;
  final short x, y, w, h, xw, yh, lim;
 
  boolean isFocused;
  String txt = "";		// Initialize txt variable to an empty string.
 
  TextBox(int xx, int yy, int ww, int hh, int li, 
  int te, int ba, int bo, int se) {
    x = (short) xx;
    y = (short) yy;
    w = (short) ww;
    h = (short) hh;
 
    lim = (short) li;
 
    xw = (short) (xx + ww);
    yh = (short) (yy + hh);
 
    textC = te;
    baseC = ba;
    bordC = bo;
    slctC = se;
  }
 
  public void display() {							// Fxn: display
    stroke(isFocused? slctC : bordC);
    fill(baseC);
    rect(x, y, w, h);
 
    fill(textC);
    text(txt + blinkChar(), x, y, w, h);
  }
 
  public String blinkChar() {
    return isFocused && (frameCount>>2 & 1) == 0 ? "_" : "";
  }
 
  public boolean checkFocus() {
    return isFocused = mouseX > x & mouseX < xw & mouseY > y & mouseY < yh;
  }
}
  public void settings() {  size(640, 480);  smooth(4); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "keyPress_prototype" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
