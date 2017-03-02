// Ellipse on black with mouse coordinate inputs
// 160110

int [] datetime;
int second;

void setup() {
  size(1920, 1200);
  background(0);
}

void draw() {

    datetime = dateTime();
    second = datetime[5];

  if (mousePressed) {
    noStroke();
    fill(mouseY/3, mouseY/3, mouseY/3, 10);
    ellipse(width/2, height/2, mouseY*2, mouseX*2);

    fill(mouseX/3, mouseX/3, mouseX/3, 10);
    ellipse(width/4, height/4, mouseY, mouseX);

    fill(mouseX/3, mouseX/3, mouseX/3, 10);
    ellipse(width/1.5, height/1.5, mouseY, mouseX);
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
