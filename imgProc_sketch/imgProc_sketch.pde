// Filename: imgProc_sketch.pde
// Author: m-ezekiel
// Image processing sketch
// Model in undergarments and grey peacoat gazing stage left

PImage img;

void setup() {
    size(1597, 800);  
    img = loadImage("model_jacket01.jpg");
    background(0);
}

void draw() {
    for (int i = 0; i < 7; i++) {
        image(img, i*200, 0);
    }    
}