// File: GPG_vectorScale.pde
// Author: m-ezekiel
// Playback and image export of Gamepad Gaussians data files

String[] lines;
int index = 0;

void setup() {
  size(1280, 800);
  background(0);
  stroke(255);
  frameRate(30);
  lines = loadStrings("data.txt");
}


void draw() {
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
    float char_IKI = float(word_vec[1]);

    if (char_IKI > 0.275) {frameRate(3);}

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