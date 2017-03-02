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

public class ellipseDraw_pde extends PApplet {

// Ellipse on black with mouse coordinate inputs
// 160110

int [] datetime;
int second;

public void setup() {
  
  background(0);
}

public void draw() {

    datetime = dateTime();
    second = datetime[5];

  if (mousePressed) {
    noStroke();
    fill(mouseY/3, mouseY/3, mouseY/3, 10);
    ellipse(width/2, height/2, mouseY*2, mouseX*2);

    fill(mouseX/3, mouseX/3, mouseX/3, 10);
    ellipse(width/4, height/4, mouseY, mouseX);

    fill(mouseX/3, mouseX/3, mouseX/3, 10);
    ellipse(width/1.5f, height/1.5f, mouseY, mouseX);
  } 
}

public int [] dateTime() {
  int [] datetime = new int[6];
  datetime[0] = year();
  datetime[1] = month();
  datetime[2] = day();
  datetime[3] = hour();
  datetime[4] = minute();
  datetime[5] = second();

  return(datetime);
}
  public void settings() {  size(1920, 1200); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "ellipseDraw_pde" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
