public String timeStamp() {
  String timeStamp;

  // Get datetime information
  int [] datetime = new int[6];
  datetime[0] = year();
  datetime[1] = month();
  datetime[2] = day();
  datetime[3] = hour();
  datetime[4] = minute();
  datetime[5] = second();

  // Create new datestamped file in the sketch directory
  save("IMG_exports/gamePad_sketch_" + join(nf(datetime, 0), "-") + ".png");  

  return(timeStamp)
}