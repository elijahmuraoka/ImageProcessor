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

  /**
   * An empty GrayScale command constructor.
   */
  public GreyScale() {
    // used to generate a blank greyscale command object that will be initialized later
  }

  /**
   * A GreyScale command constructor that takes in and sets
   * a specific type of visualization.
   *
   * @param visType the type of greyscale to create, dictating how exactly to visualize
   *                the image
   */
  public GreyScale(String visType) {
    this.visType = visType;
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
   * - Sepia: the weighted sum (0.2126 * R) + (0.7152 * G) + (0.0722 * B)
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
      if (this.visType == null) {
        this.visType = scan.next();
      }
      this.destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The GreyScale command was not called properly.\n"
              + "Please pass in new parameters with the correct format.\n"
              + "\nHere is an example:\n"
              + "GreyScale <imageName> <visType> <destName>\n");
    }
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        int[] current = m.getWorkingImageData().get(i).get(j);
        vHelper(current);
      }
    }
    m.setImageName(this.destName);
    return m;
  }

  /**
   * The helper method used to alter a pixel's components according to the greyscale
   * visualizing type (visType).
   *
   * @param pixel a size-3 array of integers each representing a red, green, and blue
   *              component respectively
   */
  private void vHelper(int[] pixel) {
    int r = pixel[0];
    int g = pixel[1];
    int b = pixel[2];

    int intensified = (r + g + b) / 3;
    int lumafied = (int) ((0.2126 * r) + (0.7152 * g) + (0.0722 * b));
    int maxComponent = Math.max(r, (Math.max(g, b)));
    
    int sepiaR = (int) ((0.393 * r) + (0.749 * g) + (0.189 * b));
    int sepiaG = (int) ((0.349 * r) + (0.686 * g) + (0.168 * b));
    int sepiaB = (int) ((0.272 * r) + (0.534 * g) + (0.131 * b));

    switch (this.visType) {
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
      case "luma":
        pixel[0] = lumafied;
        pixel[1] = lumafied;
        pixel[2] = lumafied;
        break;
      case "intensity":
        pixel[0] = intensified;
        pixel[1] = intensified;
        pixel[2] = intensified;
        break;
      case "value":
        pixel[0] = maxComponent;
        pixel[1] = maxComponent;
        pixel[2] = maxComponent;
        break;
      case "sepia":
        pixel[0] = sepiaR;
        pixel[1] = sepiaG;
        pixel[2] = sepiaB;
        break;
      default:
        throw new IllegalStateException("Invalid greyscale visualize type: " + this.visType);
    }
  }
}
