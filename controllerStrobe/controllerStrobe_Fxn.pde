/*
File: GG_functions.pde
Author: m-ezekiel
Desc.: Function script for Gamepad Gaussians
*/



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
// Print diagnostics
// -----------------

public void print_diagnostics() {

  if(up | down | left | right)
    println("D-pad pressed");

  if(A1 | A2 | A3 | A4)
    println("Action buttons pressed");

  if(L1 | L2)
    println("Left triggers pressed");

  if(R1 | R2)
    println("Right triggers pressed");

  if(M1 | M2)
    println("Missile buttons pressed");

  if (select1 | select2)
    println("Select buttons pressed");

}


// --------------
// Draw wireframe
// --------------

public void draw_wireframe() {
  float w = width;
  float h = height;

  float trigger_width = w*5/16;
  float btm_height = h*5/36;
  float top_height = h*3/36;

  float t2_topMargin = h/8;
  float t1_topMargin = h/3.8;
  float trigger_sideMargin = w/5;

  float upDown_x = w/5.5;
  float upDown_y = h/2.5;
  float square_dim = h/9;

  float select_height = h/2.25;

  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3;


  fill(0);

  // Left triggers: L2, L1
  rect(trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  rect(trigger_sideMargin, t1_topMargin, trigger_width, top_height);

  // Right triggers: R2, R1
  rect(width - trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  rect(width - trigger_sideMargin, t1_topMargin, trigger_width, top_height);

  // Direction-Pad: Up, Down, Left, Right
  rect(upDown_x, upDown_y, square_dim, square_dim);
  rect(upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  rect(upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  rect(upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);

  // Action-pad: Up, Dn, Lf, Rt
  ellipse(width - upDown_x, upDown_y, square_dim, square_dim);
  ellipse(width - upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  ellipse(width - upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  ellipse(width - upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);

  // Select buttons: select1, select2
  ellipse(w/2.3, select_height, square_dim/1.2, square_dim/2);
  ellipse(w/1.7, select_height, square_dim/1.2, square_dim/2);

  // Analog Controllers Sketch
  ellipse(anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5, square_dim*2.5);
  ellipse(w - anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5, square_dim*2.5);

}



// -----------------
// Light the strobes
// -----------------

public void lightTheStrobes() {
  float w = width;
  float h = height;

  float trigger_width = w*5/16;
  float btm_height = h*5/36;
  float top_height = h*3/36;

  float t2_topMargin = h/8;
  float t1_topMargin = h/3.8;
  float trigger_sideMargin = w/5;

  float upDown_x = w/5.5;
  float upDown_y = h/2.5;
  float square_dim = h/9;

  float select_height = h/2.25;

  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3;


  fill(255);


  // Bottom triggers
  if (L2) rect(trigger_sideMargin, t2_topMargin, trigger_width, btm_height);
  if (R2) rect(width - trigger_sideMargin, t2_topMargin, trigger_width, btm_height);


  // Top triggers
  if (L1) rect(trigger_sideMargin, t1_topMargin, trigger_width, top_height);
  if (R1) rect(width - trigger_sideMargin, t1_topMargin, trigger_width, top_height);


  // D-Pad: Up, Down, Left, Right
  if (up) rect(upDown_x, upDown_y, square_dim, square_dim);
  if (down) rect(upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  if (left) rect(upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  if (right) rect(upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);


  // Action buttons: Up, Dn, Lf, Rt (A4, A2, A1, A3 by logitech ctrl labels)
  if (A4) ellipse(width - upDown_x, upDown_y, square_dim, square_dim);
  if (A2) ellipse(width - upDown_x, upDown_y + square_dim*2, square_dim, square_dim);
  if (A1) ellipse(width - upDown_x - square_dim, upDown_y + square_dim, square_dim, square_dim);
  if (A3) ellipse(width - upDown_x + square_dim, upDown_y + square_dim, square_dim, square_dim);


  // Select buttons
  if (select1) ellipse(w/2.3, select_height, square_dim/1.2, square_dim/2);
  if (select2) ellipse(w/1.7, select_height, square_dim/1.2, square_dim/2);


  // Analog controllers
  fill(60);
  if (abs(analogX) > 0.15  || abs(analogY) > 0.15) 
    ellipse(anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5, square_dim*2.5);
  if (abs(analogU) > 0.15 || abs(analogV) > 0.15)
    ellipse(w - anlg_Strobe_x, anlg_Strobe_y, square_dim*2.5, square_dim*2.5);
  fill(0);

}


// --------------------
// Draw analog position
// --------------------

void draw_AnlgPos() {
  float w = width;
  float h = height;
  float anlg_Strobe_x = w/3;
  float anlg_Strobe_y = h/1.3;
  float reticle = w/20;
  float anlg_scale = w/20;

  // Left controller
  if (abs(analogX) > 0.15  || abs(analogY) > 0.15) {
    fill(255);
    // draw reticle in opaque white
    ellipse(anlg_Strobe_x + analogX*anlg_scale, anlg_Strobe_y + analogY*anlg_scale, reticle, reticle);
  } else {
    fill(0);
    // draw reticle in transparent black
    ellipse(anlg_Strobe_x + analogX*anlg_scale, anlg_Strobe_y + analogY*anlg_scale, reticle, reticle);
  }

  // Right controller
  if (abs(analogU) > 0.15 || abs(analogV) > 0.15) {
    fill(255);
    ellipse(w - anlg_Strobe_x + analogU*anlg_scale, anlg_Strobe_y + analogV*anlg_scale, reticle, reticle);
  } else {
    fill(0);
    ellipse(w - anlg_Strobe_x + analogU*anlg_scale, anlg_Strobe_y + analogV*anlg_scale, reticle, reticle);
  }

}


