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

public class APT_psychometricTrans extends PApplet {

int rectX, rectY;      // Position of square button
int circleX, circleY;  // Position of circle button
int rectSize = 90;     // Diameter of rect
int circleSize = 93;   // Diameter of circle
int rectColor, circleColor, baseColor;
int rectHighlight, circleHighlight;
int currentColor;
boolean rectOver = false;
boolean circleOver = false;

String title = "Psychometric Memory Transcription Machine";

String privacyNotice = "For your privacy and to protect the fidelity of your memories, the screen will remain blank as you type.  No text will appear, but everything will be recorded, analyzed and logged for future recall and analysis. Please begin typing to initiate your session and click done to return to the main menu.";

public void setup() {
  

  // BUTTONS
  rectColor = color(0);
  rectHighlight = color(51);
  circleColor = color(255);
  circleHighlight = color(204);
  baseColor = color(102);
  currentColor = baseColor;
  circleX = width/2+circleSize/2+10;
  circleY = height/2;
  rectX = width/2-rectSize-10;
  rectY = height/2-rectSize/2;
  ellipseMode(CENTER);

  // TEXT
  textAlign(LEFT, TOP);
}

public void draw() {
  update(mouseX, mouseY);
  background(currentColor);

  text(title, 0, 0);
  
  // 1. Display title and buttons
  // 2. If recall clicked, show recall menu
  //    if memory clicked, play memory
  // 3. If record clicked, log keystrokes

  // BUTTON MOUSEOVERS

  if (rectOver) {
    fill(rectHighlight);
  } else {
    fill(rectColor);
  }
  stroke(255);
  rect(rectX, rectY, rectSize, rectSize);
  
  if (circleOver) {
    fill(circleHighlight);
  } else {
    fill(circleColor);
  }
  stroke(0);
  ellipse(circleX, circleY, circleSize, circleSize);
}
public void update(int x, int y) {
  if ( overCircle(circleX, circleY, circleSize) ) {
    circleOver = true;
    rectOver = false;
  } else if ( overRect(rectX, rectY, rectSize, rectSize) ) {
    rectOver = true;
    circleOver = false;
  } else {
    circleOver = rectOver = false;
  }
}


public boolean overRect(int x, int y, int width, int height)  {
  if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

public boolean overCircle(int x, int y, int diameter) {
  float disX = x - mouseX;
  float disY = y - mouseY;
  if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
    return true;
  } else {
    return false;
  }
}


public void mousePressed() {
  if (rectOver) {
    currentColor = rectColor;
    // Function to show recall menu
  }
  if (circleOver) {
    currentColor = circleColor;
    // Function to show record prompt
  }
}


public void showMenu_Play() {
  
}

public void showPrompt_Record() {

}
  public void settings() {  size(640, 360); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "APT_psychometricTrans" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
