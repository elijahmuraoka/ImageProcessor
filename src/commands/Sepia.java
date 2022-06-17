package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import utils.IPUtils;
import model.IPModel;

/**
 * This command is used to convert a normal color image into a sepia-toned image.
 * Sepia-toned images refer to ones taken in the 19th and early 20th century
 * which had a characteristic reddish brown tone.
 */
public class Sepia implements IPCommand {

  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    utils.IPUtils utils = new IPUtils();
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The Sepia command was not called properly.\n"
              + "Please pass in new parameters with the correct format.\n"
              + "\nHere is an example:\n"
              + "Sepia <imageName> <destName>\n");
    }
    utils.transformColorHelper(m, this.generateSepiaMatrix());
    m.setImageName(destName);
    return m;
  }

  /**
   * Generates a specific kernel matrix used to create a Sepia-tone image.
   *
   * @return a 2-D array representing the matrix to be used in the color transformation algorithm
   */
  private double[][] generateSepiaMatrix() {
    double[][] result = new double[3][3];
    result[0][0] = 0.393;
    result[0][1] = 0.769;
    result[0][2] = 0.189;

    result[1][0] = 0.349;
    result[1][1] = 0.686;
    result[1][2] = 0.168;

    result[2][0] = 0.272;
    result[2][1] = 0.534;
    result[2][2] = 0.131;
    return result;
  }
}