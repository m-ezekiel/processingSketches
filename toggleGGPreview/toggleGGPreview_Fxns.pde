/*
File: GGpreview_functions.pde
Author: m-ezekiel
Desc.: Function script for Gamepad Gaussians
*/


// -----------------
// Analog to integer
// -----------------

public int analogToInteger(float x, float y, int k) {
  int value = floor((x - y) * k);
  return(value);
}





// ---------------
// Toggle preview
// ---------------

public void togglePreview() {
  int w = width;
  int h = height;

  // Outer window
  int owB = w/8; // 160
  float owH = owB/2.5; // 60
  int owX = width - owB;
  int owY = 0;
  int gap = w/45; // 28
  // Inner window
  float iwB = owB/3.125;
  float iwH = iwB * 0.625;
  // Centerpoints
  float owCX = owX + (owB/2);
  float owCY = owY + (owH/2);
  float iwCX = owX + owB/12 + iwB/2;
  float iwCY = owY + owH/6 + iwH/2;  
  // Parameter values
  String opacity = str(alpha);
  String rd = str(red);
  String grn = str(green);
  String blu = str(blue);
  String x_mn = str(x_mean);
  String y_mn = str(y_mean);
  String dX = str(dpX/4);
  String dY = str(dpY/4);

  int brushScale = w/45;

  // Head Up Display (outer window)
  fill(0, 155);
  rect(owX, owY, owB, owH);


  // Prototype for inner window preview
  stroke(100);
  rect(owX + owB/12, owY + owH/6, iwB, iwH);
  noStroke();


  stroke(150);
  fill(red, green, blue, 127);

  // X-DISPERSION
  ellipse(iwCX - dpX/20 + (x_mean - width/2)/25, iwCY + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  ellipse(iwCX + dpX/20 + (x_mean - width/2)/25, iwCY + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  // Y-DISPERSION
  ellipse(iwCX + (x_mean - width/2)/25, iwCY - dpY/30 + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);
  ellipse(iwCX + (x_mean - width/2)/25, iwCY + dpY/30 + (y_mean - height/2)/25, 
    brushSize_X/brushScale, brushSize_Y/brushScale);

  // Brush pigment colored ellipse
  noStroke();
  fill(red, green, blue, alpha*3);
  ellipse(owCX + gap + gap/11, owCY - gap/11, gap/2, gap/2);


  // Display color values according to controller position
  textAlign(CENTER, TOP);
  textSize(owB/15);
  // Color Values
  fill(200);
  text(opacity, owCX + gap + gap/11, owCY - gap/1.1);

  fill(0, 255, 0, 255);
  text(grn, owCX + gap + gap/11, owCY + gap/3);

  fill(0, 200, 255, 255);
  text(blu, owCX + gap/5, owCY - gap/3);

  fill(255, 0, 0, 255);
  text(rd, owCX + (2*gap), owCY - gap/3);

  // Position Values
  fill(180);
  text(x_mn, owCX - (2*gap), owCY + gap/2);
  text(y_mn, owCX - (1*gap), owCY + gap/2);


  // IMAGE SAVED
  if (imageSaved == true) {
    fill(255);
    text("SAVED", owCX, owCY - 20); 
  }

}






// ----------------------------------
// Limit values to variable scale a:b
// ----------------------------------

public int limitScale(int x, int min, int max) {
  if (x > max)
    x = max;
  if (x < min)
    x = min;
  return(x);
}



// ----------------
// Gaussian integer
// ----------------

public int gaussianInt(int dispersion, int mean) {

  float rGauss = randomGaussian();  // Random number from Z~(0,1)
  int value = floor((rGauss * dispersion) + mean);

  return(value);
}




// -----------------
// Get analog values
// -----------------

public float [] getAnalogValues() {
  float [] AV_array = new float[4];  

  AV_array[0] = analogX;
  AV_array[1] = analogY;
  AV_array[2] = analogU;
  AV_array[3] = analogV;

  return(AV_array);
}


// ---------------------------------------
// Get parameter values (color, disp, size)
// ----------------------------------------

public int [] getParamValues() {
  int [] DV_array = new int[8];  

  DV_array[0] = red;
  DV_array[1] = green;
  DV_array[2] = blue;
  DV_array[3] = alpha;

  DV_array[4] = dpX;
  DV_array[5] = dpY;
  DV_array[6] = brushSize_X;
  DV_array[7] = brushSize_Y;

  return(DV_array);
}






// ------------------------
// Get position coordinates
// ------------------------

public int [] getXYpos() {
  int [] XYpos = new int[2];

  XYpos[0] = xpos;
  XYpos[1] = ypos;

  return(XYpos);
}



// --------------
// Get user input
// --------------

public void getUserInput() {
  select1 = gpad.getButton("start").pressed();
  select2 = gpad.getButton("select").pressed();
  
  // Analog controller to position vars
  analogX =  gpad.getSlider("analogXL").getValue();
  analogY =  gpad.getSlider("analogYL").getValue();
  analogU =  gpad.getSlider("analogXR").getValue();
  analogV =  gpad.getSlider("analogYR").getValue();
  // Analog stick button presses
  M1 = gpad.getButton("M1").pressed();
  M2 = gpad.getButton("M2").pressed();
  
  // Action buttons
  A1 = gpad.getButton("a").pressed();
  A2 = gpad.getButton("b").pressed();
  A3 = gpad.getButton("x").pressed();
  A4 = gpad.getButton("y").pressed();

  // LR triggers
  L1 = gpad.getButton("L1").pressed();
  L2 = gpad.getButton("L2").pressed();
  R1 = gpad.getButton("R1").pressed();
  R2 = gpad.getButton("R2").pressed();
  
  // D-Pad values
  left = gpad.getHat("dPad").left();
  right = gpad.getHat("dPad").right();
  up = gpad.getHat("dPad").up();
  down = gpad.getHat("dPad").down();
}



// -----------------
// Get direction pad
// -----------------

public boolean getDPad() {
  boolean value = false;

  // If buttons are pressed then value is false
  if (left | right | up | down)
    value = true;

  return(value);
}


// --------------
// Get action pad
// --------------

public boolean getActionPad() {
  boolean value = false;

  // If buttons are pressed then value is false
  if (A1 | A2 | A3 | A4)
    value = true;

  return(value);
}



// --------------
// Random integer
// --------------

public int randomInt(int a, int b) {
  int value = floor(random(a, b));
  return(value);
}



// -----
// Reset
// -----

public void resetBlack() {
  background(0);
}
public void resetWhite() {
  background(255);
}
public void resetColor() {
  background(red, green, blue, alpha);
}
public void resetInverse() {
  background(255 - red, 255 - green, 255 - blue, 255 - alpha);
}








