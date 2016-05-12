// Filename: keyPress_prototype.pde
// Date: 
// Authors: Inarts, GoToLoop
// Modified by: m-ezekiel
// Keylogging prototype using text processing tools.

static final int NUM = 2;
final TextBox[] tboxes = new TextBox[NUM];
int idx;
 
void setup() {
  size(640, 480);
  frameRate(20);
  smooth(4);
 
  rectMode(CORNER);
  textAlign(LEFT);
  strokeWeight(1.5);
 
  instantiateBoxes();
  tboxes[idx = 1].isFocused = true;
}
 
void draw() {
  background(#778C85);
  for (int i = 0; i != NUM; tboxes[i++].display());
}
 
void mouseClicked() {
  int i = idx = -1;
  while (++i != NUM)  if (tboxes[i].checkFocus())  idx = i;
}
 
void keyTyped() {
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
 
void keyPressed() {
  if (key != CODED | idx < 0)  return;
  final int k = keyCode;
 
  final TextBox tbox = tboxes[idx];
  final int len = tbox.txt.length();
 
  if (k == LEFT)  tbox.txt = tbox.txt.substring(0, max(0, len-1));
  else if (k == RIGHT & len < tbox.lim-3)  tbox.txt += "    ";
}
 
void instantiateBoxes() {
  tboxes[0] = new TextBox(
  width>>2, height/4 + height/16, // x, y
  width - width/2, height/2 - height/4 - height/8, // w, h
  215, // lim
  0300 << 030, color(-1, 040), // textC, baseC
  color(-1, 0100), color(#FF00FF, 0200)); // bordC, slctC
 
  tboxes[1] = new TextBox(
  width>>3, height/2 + height/8, // x, y
  width - width/4, height - height/2 - height/4, // w, h
  640, // lim
  0300 << 030, color(-1, 040), // textC, baseC
  color(-1, 0100), color(#FFFF00, 0200)); // bordC, slctC
}
 
class TextBox { // demands rectMode(CORNER)
  final color textC, baseC, bordC, slctC;
  final short x, y, w, h, xw, yh, lim;
 
  boolean isFocused;
  String txt = "";
 
  TextBox(int xx, int yy, int ww, int hh, int li, 
  color te, color ba, color bo, color se) {
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
 
  void display() {
    stroke(isFocused? slctC : bordC);
    fill(baseC);
    rect(x, y, w, h);
 
    fill(textC);
    text(txt + blinkChar(), x, y, w, h);
  }
 
  String blinkChar() {
    return isFocused && (frameCount>>2 & 1) == 0 ? "_" : "";
  }
 
  boolean checkFocus() {
    return isFocused = mouseX > x & mouseX < xw & mouseY > y & mouseY < yh;
  }
}