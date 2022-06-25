package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A PPM image file and model representation.
 */
public class PPMImage extends BetterAbstractImageModel {

  /**
   * The abstract image file constructor used to create an Image File object and store its
   * file name.
   *
   * @param fileName the (desired) name of the file
   */
  public PPMImage(String fileName) {
    super(fileName);
  }

  /**
   * A super constructor that takes in all fields and creates a PPM image object.
   *
   * @param imageName        the name of the image
   * @param height           the height of the image
   * @param width            the width of the image
   * @param maxComponent     the maximum color component
   * @param workingImageData the 2D array list of pixels representing the image's data
   * @param fileName         the name of the image file
   * @throws IllegalArgumentException when the width, height, or maximum color component is
   *                                  less than zero
   */
  public PPMImage(String imageName, int height, int width, int maxComponent,
                  List<List<int[]>> workingImageData, String fileName)
          throws IllegalArgumentException {
    super(imageName, height, width, maxComponent, workingImageData, fileName);
  }

  /**
   * Read an image file in the PPM format and store its data.
   *
   * @throws IllegalStateException when either the input(s) and/or output(s) are invalid
   */
  @Override
  public void read() throws IllegalStateException {
    Scanner sc;
    System.out.println("Current File Name:" + this.fileName);

    try {
      sc = new Scanner(new FileInputStream(this.fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + this.fileName + " not found!\n");
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
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3.");
    }
    this.width = sc.nextInt();
    this.height = sc.nextInt();
    this.maxComponent = sc.nextInt();

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
  }

  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".ppm";
  }
}
