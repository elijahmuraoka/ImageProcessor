package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import Utils.IPUtils;
import model.IPModel;

/**
 * This command is used to sharpen an image. Sharpening works by accentuating the edges
 * (the boundaries between regions of high contrast) of an image,
 * thereby giving the image a "sharper" look.
 */
public class Sharpen implements IPCommand {

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
   * Generates a specific matrix kernel used to sharpen an image.
   *
   * @return a 2-D array representing the matrix to be used in the blur and sharpening algorithm
   */
  private double[][] generateSharpenMatrix() {
    double[][] result = new double[5][5];
    result[0][0] = 1.0 / -8;
    result[0][1] = 1.0 / -8;
    result[0][2] = 1.0 / -8;
    result[0][3] = 1.0 / -8;
    result[0][4] = 1.0 / -8;

    result[1][0] = 1.0 / -8;
    result[1][1] = 1.0 / 4;
    result[1][2] = 1.0 / 4;
    result[1][3] = 1.0 / 4;
    result[1][4] = 1.0 / -8;

    result[2][0] = 1.0 / -8;
    result[2][1] = 1.0 / 4;
    result[2][2] = 1.0;
    result[2][3] = 1.0 / 4;
    result[2][4] = 1.0 / -8;

    result[3][0] = 1.0 / -8;
    result[3][1] = 1.0 / 4;
    result[3][2] = 1.0 / 4;
    result[3][3] = 1.0 / 4;
    result[3][4] = 1.0 / -8;

    result[4][0] = 1.0 / -8;
    result[4][1] = 1.0 / -8;
    result[4][2] = 1.0 / -8;
    result[4][3] = 1.0 / -8;
    result[4][4] = 1.0 / -8;

    return result;
  }
}
