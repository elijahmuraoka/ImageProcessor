package utils;

import java.util.ArrayList;
import java.util.List;

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
   * @return a boolean representing whether the (row, column) exists within the model's
   *         image dimensions
   */
  public boolean isWithinDimensions(IPModel m, int row, int col) {
    return row >= 0 && row < m.getHeight() && col >= 0 && col < m.getWidth();
  }

  /**
   * Caps a given color component, forcing it to exist between 0 and 255.
   *
   * @param m         the IPModel used to retrieve its max component
   * @param component the component to be evaluated
   * @return the component in a range between 0 and the maximum component value of the model
   */
  public int capComponent(IPModel m, int component) {
    if (component > m.getMaxComponent()) {
      return m.getMaxComponent();
    } else {
      return Math.max(0, component);
    }
  }

  /**
   * A helper command method used in both the blur and sharpen classes.
   *
   * @param m      the IPModel to be modified
   * @param matrix the matrix which will be used in the image processing algorithm to either blur
   *               or sharpen an image
   */
  public void blurAndSharpenHelper(IPModel m, double[][] matrix) {
    int matrixStart = (matrix.length - 1) / 2;
    List<List<int[]>> result = new ArrayList<>();
    // for each row
    for (int i = 0; i < m.getHeight(); i++) {
      // for each column
      List<int[]> newColumn = new ArrayList<>();
      for (int j = 0; j < m.getWidth(); j++) {
        double sumRed = 0;
        double sumGreen = 0;
        double sumBlue = 0;
        // for every cell in the given matrix
        for (int matrixI = 0; matrixI < matrix.length; matrixI++) {
          for (int matrixJ = 0; matrixJ < matrix.length; matrixJ++) {
            // math to get the matrix coordinate with respect to the original image 2-D array
            // and the current center pixel
            int overlapPixelRow = i - matrixStart + matrixI;
            int overlapPixelCol = j - matrixStart + matrixJ;
            // if the matrix's row and column values are both within the imageData's
            // width and height
            if (this.isWithinDimensions(m, overlapPixelRow, overlapPixelCol)) {
              // get the appropriate pixel from the image
              int[] overlapPixel = m.getWorkingImageData().get(overlapPixelRow)
                      .get(overlapPixelCol);
              // add to the RGB sums when a matrix cell is overlapped on an existing image pixel
              sumRed += overlapPixel[0] * matrix[matrixI][matrixJ];
              sumGreen += overlapPixel[1] * matrix[matrixI][matrixJ];
              sumBlue += overlapPixel[2] * matrix[matrixI][matrixJ];
            }
          }
        }
        // make a new pixel with the new pixel components
        int newR = this.capComponent(m, (int) sumRed);
        int newG = this.capComponent(m, (int) sumGreen);
        int newB = this.capComponent(m, (int) sumBlue);
        int[] newPixel = new int[]{newR, newG, newB};
        newColumn.add(newPixel);
      }
      result.add(newColumn);
    }
    m.setWorkingImageData(result);
  }

  /**
   * A helper method used that can be used in color transformation commands.
   *
   * @param m      the IPModel to be modified
   * @param matrix the matrix which will be used in the image processing algorithm to either blur
   *               or sharpen an image
   */
  public void transformColorHelper(IPModel m, double[][] matrix) {
    List<List<int[]>> result = new ArrayList<>();
    // for each row
    for (int i = 0; i < m.getHeight(); i++) {
      // for each column
      List<int[]> newColumn = new ArrayList<>();
      for (int j = 0; j < m.getWidth(); j++) {
        int[] pixel = m.getWorkingImageData().get(i).get(j);
        int[] newPixel = new int[3];
        // for every cell in the given matrix
        for (int matrixI = 0; matrixI < matrix.length; matrixI++) {
          // initialize new component pixel
          double newComponent = 0;
          for (int matrixJ = 0; matrixJ < matrix.length; matrixJ++) {
            // multiply each item in the matrix's row and the respective pixel component
            // add the resulting product to the current sum of the new component
            newComponent += matrix[matrixI][matrixJ] * pixel[matrixJ];
          }
          // adds and caps the new pixel component to the new pixel
          newPixel[matrixI] = this.capComponent(m, (int) newComponent);
        }
        newColumn.add(newPixel);
      }
      result.add(newColumn);
    }
    m.setWorkingImageData(result);
  }
}
