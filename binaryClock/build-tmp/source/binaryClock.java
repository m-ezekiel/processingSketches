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
// Features I'd like to add to binaryClock:
//     - Fading blocks
//     - Colors


String sec, min, hr;
TimeColumn hours, minutes, seconds;

public void setup() {
      
    background(0);   // bgColor 

    // rectMode(CENTER);
    textAlign(RIGHT);

    noStroke();
    

    int partitions = 6;
    hours = new TimeColumn(85, 20, 200, 600, partitions, hr);
    minutes = new TimeColumn(300, 20, 200, 600, partitions, min);
    seconds = new TimeColumn(515, 20, 200, 600, partitions, sec);

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
            fill(220);
            rect(hours.x, 
                hours.y + (i * (hours.height/hours.p)), 
                hours.width, 
                hours.height/hours.p);
        }
    }

    fill(0);
    minutes.display_col();
    // Control loop for minutes
    for (int i = 0; i < minutes.p; i++) {
        if (min.charAt(i) == '1') {
            fill(110);
            rect(minutes.x, 
                minutes.y + (i * (minutes.height/minutes.p)), 
                minutes.width, 
                minutes.height/minutes.p);
        }
    }

    fill(0);
    seconds.display_col();
    // Control loop for seconds
    for (int i = 0; i < seconds.p; i++) {
        if (sec.charAt(i) == '1') {
            fill(50);
            rect(seconds.x, 
                seconds.y + (i * (seconds.height/seconds.p)), 
                seconds.width, 
                seconds.height/seconds.p);
        } else {
            fill(255, 0);   // Fill transparent
            rect(seconds.x, 
                seconds.y + (i * (seconds.height/seconds.p)), 
                seconds.width, 
                seconds.height/seconds.p);
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
  public void settings() {  size(800, 640);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "binaryClock" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
