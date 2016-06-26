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

public class binaryClock extends PApplet {

// Filename: binaryClock.pde
// Author: m-ezekiel
//
//

String sec, min, hr;        // Define so they don't break the TC defs below.
TimeColumn hours, minutes, seconds;

public void setup() {
      
    background(0);   // bgColor 

    // rectMode(CENTER);
    textAlign(RIGHT);

    noStroke();
    

    int partitions = 6;
    hours = new TimeColumn(0, 0, 200, 600, partitions, hr);
    minutes = new TimeColumn(200, 0, 200, 600, partitions, min);
    seconds = new TimeColumn(400, 0, 200, 600, partitions, sec);

}

public void draw() {

    // Binary Time Variables
    String sec = binary(second(), 6);
    String min = binary(minute(), 6);
    String hr = binary(hour(), 6);

    fill(0);
    hours.display_col();
    // Control loop for hours
    for (int i = 0; i < hours.p; i++) {
        if (hr.charAt(i) == '1') {
            fill(255);
            rect(hours.x, 
                hours.y + (i * (height/hours.p)), 
                width, 
                height/hours.p);
        }
    }

    fill(75);
    minutes.display_col();
    // Control loop for minutes
    for (int i = 0; i < minutes.p; i++) {
        if (min.charAt(i) == '1') {
            fill(255);
            rect(minutes.x, 
                minutes.y + (i * (height/minutes.p)), 
                width, 
                height/minutes.p);
        }
    }

    fill(150);
    seconds.display_col();
    // Control loop for seconds
    for (int i = 0; i < seconds.p; i++) {
        if (sec.charAt(i) == '1') {
            fill(255);
            rect(seconds.x, 
                seconds.y + (i * (height/seconds.p)), 
                width, 
                height/seconds.p);
        }
    }

println("second: "+sec.charAt(5));

}

class TimeColumn {
    int x, y, width, height, p;
    String time;

    TimeColumn(int xpos, int ypos, int wd, int ht, int partitions, String time) {
        x = xpos;
        y = ypos;
        width = wd;
        height = ht;
        p = partitions;
        time = time;
    }

    public void display_col() {
        rect(x, y, width, height);
    }

}
  public void settings() {  size(600, 600);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "binaryClock" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
