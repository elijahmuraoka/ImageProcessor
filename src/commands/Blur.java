package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Utils.IPUtils;
import model.IPModel;

/**
 * This command is used to blur an image using a filtering algorithm.
 * In this class, we used a 3x3 kernel matrix to create what is known as
 * a "Gaussian" blur (imagine the values to be heights of a 3D bar graph
 * to see how they form a coarse bell-like Gaussian surface). Blurring can be done
 * by applying the filter to every channel of every pixel to produce the output image.
 */
public class Blur implements IPCommand {
  /**
   * Blur an image model copy which will be referred to henceforth by the given destination name.
   *
   * @param m    the Image model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the image model to be blurred
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
      throw new IllegalStateException("The Blur command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere is an example:\n"
              + "Blur <imageName> <destName>\n");
    }
    utils.blurAndSharpenHelper(m, this.generateBlurMatrix());
    m.setImageName(destName);
    return m;
  }

  /**
   * Generates a specific kernel matrix used to create a Gaussian blur.
   *
   * @return a 2-D array representing the matrix to be used in the blur and sharpening algorithm
   */
  private double[][] generateBlurMatrix() {
    return new double[][]{
            new double[]{1.0 / 16, 1.0 / 8, 1.0 / 16},
            new double[]{1.0 / 8, 1.0 / 4, 1.0 / 8},
            new double[]{1.0 / 16, 1.0 / 8, 1.0 / 16}};
  }
}
