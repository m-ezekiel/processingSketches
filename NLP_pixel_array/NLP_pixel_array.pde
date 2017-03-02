// Filename: NLP_pixel_array.pde
// Description: Takes a binary matrix as input and displays a black and white "pixel print"
// visualization.

// 2D Array of objects
Cell[][] grid;

// Number of columns and rows in the grids
int cols = 5;
int rows = 5;

// Table
Table table;
int value;

void setup() {
  table = loadTable("save_matrix.txt", "header, csv");
  println(table.getRowCount() + " total rows in table");
  
  size(201,201);

  grid = new Cell[cols][rows];
  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      // Initialize each object
      grid[i][j] = new Cell(i*40,j*40,40,40,i+j);
    }
  }
}

void draw() {

  background(0);

  for (int i = 0; i < cols; i++) {
    for (int j = 0; j < rows; j++) {
      value = table.getInt(i, j);
      
      if (value == 0) {
        fill(0);
        grid[j][i].display();
      }
      
      if (value == 1) {
        fill(255);
        grid[j][i].display();
      }
    }
  }

  save("image.png");
}

// A Cell object
class Cell {
  // A cell object knows about its location in the grid 
  // as well as its size with the variables x,y,w,h
  float x,y;   // x,y location
  float w,h;   // width and height
  float angle; // angle for oscillating brightness

  // Cell Constructor
  Cell(float tempX, float tempY, float tempW, float tempH, float tempAngle) {
    x = tempX;
    y = tempY;
    w = tempW;
    h = tempH;
    angle = tempAngle;
  } 
  
  // Oscillation means increase angle
  void oscillate() {
    angle += 0.02; 
  }

  void display() {
    stroke(255);
    // Color calculated using sine wave
    //fill(127+127*sin(angle));
    rect(x,y,w,h); 
  }
}