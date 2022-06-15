package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import view.IPView;

/**
 * A PPM image file representation.
 */
public class PPMImage extends AbstractImageFile {

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
   * Read an image file in the PPM format and store its data.
   *
   * @param v the Image Processor's view to render necessary messages to
   * @throws IOException when either the input(s) and/or output(s) are invalid
   */
  @Override
  public void read(IPView v) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(this.fileName));
    } catch (FileNotFoundException e) {
      v.renderMessage("File " + this.fileName + " not found!\n");
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
    v.renderMessage("Successfully loaded image: " + this.fileName + "\n");
  }

  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".ppm";
  }
}
