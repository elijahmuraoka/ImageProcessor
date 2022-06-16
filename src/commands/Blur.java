package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Utils.IPUtils;
import model.IPModel;

/**
 *
 */
public class Blur implements IPCommand {
  /**
   * Change the brightness of the image by the given increment to create a new image,
   * referred to henceforth by the given destination name.
   * The increment may be positive (brightening) or negative (darkening).
   *
   * @param m    the Image model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the modified image model that is either blurred or sharpened
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    IPUtils utils = new IPUtils();
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere is an example:\n"
              + "ChangeBrightness <increment> <imageName> <destName>\n");
    }
    utils.blurAndSharpenHelper(m, this.generateBlurMatrix());
    m.setImageName(destName);
    return m;
  }

  /**
   * Generates a specific matrix kernel used to create a Gaussian blur.
   *
   * @return a 2-D array representing the matrix to be used in the blur and sharpening algorithm
   */
  private double[][] generateBlurMatrix() {
    double[][] result = new double[3][3];
    result[0][0] = 1.0 / 16;
    result[0][1] = 1.0 / 8;
    result[0][2] = 1.0 / 16;

    result[1][0] = 1.0 / 8;
    result[1][1] = 1.0 / 4;
    result[1][2] = 1.0 / 8;

    result[2][0] = 1.0 / 16;
    result[2][1] = 1.0 / 8;
    result[2][2] = 1.0 / 16;
    return result;
  }
}
