package model;

public class Pixel {
  int red;
  int green;
  int blue;

  Pixel(int r, int g, int b) {
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }
}
