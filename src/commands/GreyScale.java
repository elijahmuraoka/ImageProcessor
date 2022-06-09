package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;

/**
 * This command is used to create a greyscale version of an image according to
 * a specific channel. Either red, blue, green, value, intensity or luma.
 */
public class GreyScale implements IPCommand {
  // a string that describes the type of greyscale to create and how to visualize it
  private String visType;
  // the new destination name representing the image
  private String destName;
  // a single pixel represented by an array of three integers
  private int[] pixel;

  public GreyScale() {

  }


  /**
   * Create a greyscale version of the image with a new name, and
   * refer to it henceforth in the program by the given destination name.
   * You should be able to create greyscale images that specifically
   * visualize the following:
   * - Red: a pixel's red component
   * - Green: a pixel's green component
   * - Blue: a pixel's blue component
   * - Value: the maximum value of the three components for each pixel
   * - Intensity: the average of the three components for each pixel
   * - Luma: the weighted sum (0.2126 * R) + (0.7152 * G) + (0.0722 * B)
   *
   * @param m    the Image represented model to be used and acted on
   * @param scan the scanner used to read and retrieve user inputs
   * @return the modified IPModel that is now flipped horizontally
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed
   */
  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    try {
      this.visType = scan.next();
      this.destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The Visualize command was not called properly.\n"
              + "Please pass in new parameters with the following format:\n"
              + "GreyScale <c> <imageName> <destName>\n");
    }
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        pixel = m.getWorkingImageData().get(i).get(j);
        vHelper(this.visType, pixel);
      }
    }
    m.setImageName(this.destName);
    return m;
  }

  private void vHelper(String s, int[] pixel) {
    int r = pixel[0];
    int g = pixel[1];
    int b = pixel[2];

    //int grey = (int)((0.2989 * r) + (0.5870 * g) + (0.1140 * b));

    switch (s) {
      case "red":
        pixel[1] = pixel[0];
        pixel[2] = pixel[0];
        break;
      case "green":
        pixel[0] = pixel[1];
        pixel[2] = pixel[1];
        break;
      case "blue":
        pixel[0] = pixel[2];
        pixel[1] = pixel[2];
        break;
      case "default":
        throw new IllegalStateException("Invalid visualize parameter: " + s);
    }
  }
}
