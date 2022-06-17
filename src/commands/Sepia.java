package commands;

/**
 * This command is used to convert a normal color image into a sepia-toned image.
 * Sepia-toned images refer to ones taken in the 19th and early 20th century
 * which had a characteristic reddish brown tone.
 */
public class Sepia extends AbstractTransformColor {

  /**
   * Generates a specific kernel matrix used to create a Sepia-tone image.
   *
   * @return a 2-D array representing the matrix to be used in the color transformation algorithm
   */
  @Override
  protected double[][] generateColorMatrix() {
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