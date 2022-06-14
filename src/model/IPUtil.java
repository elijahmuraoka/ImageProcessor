package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import view.IPView;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class IPUtil {
  // the height of the PPM image
  private int height;
  // the width of the PPM image
  private int width;
  // the working image displayed as a 1-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  private List<List<int[]>> workingImageData;

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public void readPPM(String filename, IPView v) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      v.renderMessage("File " + filename + " not found!\n");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      v.renderMessage("Invalid PPM file: plain RAW file should begin with P3.");
    }
    this.width = sc.nextInt();
    v.renderMessage("Width of image: " + width + "\n");
    this.height = sc.nextInt();
    v.renderMessage("Height of image: " + height + "\n");
    int maxValue = sc.nextInt();
    v.renderMessage("Maximum value of a color in this file (usually 255): " + maxValue + "\n");

    this.workingImageData = new ArrayList<>();
    for (int i = 0; i < this.height; i++) {
      ArrayList<int[]> newColumn = new ArrayList<>();
      for (int j = 0; j < this.width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        int[] pixel = {r, g, b};
        newColumn.add(pixel);
        // System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
      this.workingImageData.add(newColumn);
    }
    v.renderMessage("Successfully loaded image: " + filename + "\n");
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public List<List<int[]>> getWorkingImageData() {
    return this.workingImageData;
  }
}

