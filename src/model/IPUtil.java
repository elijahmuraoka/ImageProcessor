package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

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
  private final List<List<int[]>> workingImageData = new ArrayList<>();

  /**
   * Read any image file and store its data.
   *
   * @param imagePath the path of the file.
   * @param v        the Image Processor's view to render necessary message
   * @throws IOException when either the input(s) and/or output(s) are invalid
   */
  public void readImage(String imagePath, IPView v) throws IOException {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      v.renderMessage("File " + imagePath + " not found!\n");
      return;
    }
    if (img != null) {
      this.width = img.getWidth();
      v.renderMessage("Width of image: " + this.width + "\n");
      this.height = img.getHeight();
      v.renderMessage("Height of image: " + this.height + "\n");
      for (int i = 0; i < this.height; i++) {
        ArrayList<int[]> newColumn = new ArrayList<>();
        for (int j = 0; j < this.width; j++) {
          Color c = new Color(img.getRGB(j, i));
          int r = c.getRed();
          int g = c.getGreen();
          int b = c.getBlue();
          int[] pixel = new int[]{r, g, b};
          newColumn.add(pixel);
          // System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        }
        this.workingImageData.add(newColumn);
      }
      v.renderMessage("Successfully loaded image: " + imagePath + "\n");
    }
  }

  /**
   * Read an image file in the PPM format and store its data.
   *
   * @param filename the path of the file.
   * @param v        the Image Processor's view to render necessary messages to
   * @throws IOException when either the input(s) and/or output(s) are invalid
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
    v.renderMessage("Width of image: " + this.width + "\n");
    this.height = sc.nextInt();
    v.renderMessage("Height of image: " + this.height + "\n");
    int maxValue = sc.nextInt();
    v.renderMessage("Maximum value of a color in this file (usually 255): " + maxValue + "\n");

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

