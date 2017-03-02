int rectX, rectY;      // Position of square button
int circleX, circleY;  // Position of circle button
int rectSize = 90;     // Diameter of rect
int circleSize = 93;   // Diameter of circle
color rectColor, circleColor, baseColor;
color rectHighlight, circleHighlight;
color currentColor;
boolean rectOver = false;
boolean circleOver = false;

String title = "Psychometric Memory Transcription Machine";

String privacyNotice = "For your privacy and to protect the fidelity of your memories, the screen will remain blank as you type.  No text will appear, but everything will be recorded, analyzed and logged for future recall and analysis. Please begin typing to initiate your session and click done to return to the main menu.";

void setup() {
  size(640, 360);

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

void draw() {
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