package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import utils.IPUtils;
import model.IPModel;

/**
 * This command is used to sharpen an image. Sharpening works by accentuating the edges
 * (the boundaries between regions of high contrast) of an image,
 * thereby giving the image a "sharper" look.
 */
public class Sharpen implements IPCommand {

  /**
   * Sharpen an image model copy which will be referred to henceforth by the given destination name.
   *
   * @param m    the Image model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the image model to be sharpened
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    IPUtils utils = new IPUtils();
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The Sharpen command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere is an example:\n"
              + "Sharpen <imageName> <destName>\n");
    }
    utils.blurAndSharpenHelper(m, this.generateSharpenMatrix());
    m.setImageName(destName);
    return m;
  }

  /**
   * Generates a specific kernel matrix used to sharpen an image.
   *
   * @return a 2-D array representing the matrix to be used in the blur and sharpening algorithm
   */
  private double[][] generateSharpenMatrix() {
    return new double[][]{
        new double[]{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}};
  }
}
