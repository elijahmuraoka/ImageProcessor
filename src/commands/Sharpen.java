package commands;

/**
 * This command is used to sharpen an image. Sharpening works by accentuating the edges
 * (the boundaries between regions of high contrast) of an image,
 * thereby giving the image a "sharper" look.
 */
public class Sharpen extends AbstractKernelFilter {
  /**
   * Generates a specific kernel matrix used to sharpen an image.
   *
   * @return a 2-D array representing the matrix to be used in the kernel filtering algorithm
   */
  @Override
  protected double[][] generateKernelMatrix() {
    return new double[][]{
        new double[]{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        new double[]{-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}};
  }
}
