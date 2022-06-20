package utils;

import model.IPModel;

/**
 * A utility class used to store helpful methods related to Image Processing.
 */
public class IPUtils {

  /**
   * Checks a given row and column and whether they are both within the given image model's
   * width and height.
   *
   * @param m   the given IPModel
   * @param row the given row value
   * @param col the given column value
   * @return a boolean representing whether the (row, column) coordinate exists within the model's
   * image dimensions
   */
  public boolean isWithinDimensions(IPModel m, int row, int col) {
    return row >= 0 && row < m.getHeight() && col >= 0 && col < m.getWidth();
  }

  /**
   * Caps a given color component, forcing it to exist between 0 and 255
   *
   * @param component the component to be evaluated
   * @return the component in a range between 0 and 255
   */
  public int capComponent(int component) {
    if (component > 255) {
      return 255;
    } else {
      return Math.max(0, component);
    }
  }
}
