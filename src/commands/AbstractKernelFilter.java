package commands;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import utils.IPUtils;

/**
 * Use this class when creating any Image Processor editing features that need to use
 */
public abstract class AbstractKernelFilter implements IPCommand {
  // the kernel matrix to be used in filtering the image
  protected double[][] kernel = this.generateKernelMatrix();

  AbstractKernelFilter() {

  }
  protected abstract double[][] generateKernelMatrix();

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
    this.blurAndSharpenHelper(m, this.kernel);
    m.setImageName(destName);
    return m;
  }

  /**
   * A helper command method used in both the blur and sharpen classes.
   *
   * @param m      the IPModel to be modified
   * @param matrix the matrix which will be used in the image processing algorithm to either blur
   *               or sharpen an image
   */
  private void blurAndSharpenHelper(IPModel m, double[][] matrix) {
    IPUtils utils = new IPUtils();
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
            if (utils.isWithinDimensions(m, overlapPixelRow, overlapPixelCol)) {
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
        int newR = utils.capComponent(m, (int) sumRed);
        int newG = utils.capComponent(m, (int) sumGreen);
        int newB = utils.capComponent(m, (int) sumBlue);
        int[] newPixel = new int[]{newR, newG, newB};
        newColumn.add(newPixel);
      }
      result.add(newColumn);
    }
    m.setWorkingImageData(result);
  }

}
