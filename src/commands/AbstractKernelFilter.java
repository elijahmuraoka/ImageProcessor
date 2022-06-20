package commands;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import utils.IPUtils;

/**
 * This abstract class is used when creating any Image Processor editing features that need
 * a kernel matrix to filter images in a specific manner.
 * A basic operation in many image processing algorithms is filtering.
 * A filter has a "kernel," which is a 2D array of numbers, having odd dimensions
 * (3x3, 5x5, etc.). Given a pixel in the image and a channel, the result of the filter
 * can be computed for that pixel and channel.
 */
public abstract class AbstractKernelFilter implements IPCommand {
  protected double[][] kernel; // the kernel matrix needed to filter the image

  /**
   * An empty public constructor for the AbstractKernelFilter class
   * used to initialize the specific kernel matrix needed for filtering.
   *
   * @throws IllegalStateException when the size of the kernel matrix is even
   */
  public AbstractKernelFilter() throws IllegalStateException {
    this.kernel = this.generateKernelMatrix();
    if (this.kernel.length % 2 == 0) {
      throw new IllegalStateException("The Kernel Matrix's size cannot be an even number.");
    }
  }

  /**
   * Generates kernel matrix needed to filter the image.
   *
   * @return the kernel matrix as a 2D array of doubles
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  protected abstract double[][] generateKernelMatrix();

  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("This command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere are some examples:\n"
              + "Blur <imageName> <destName>\n"
              + "Sharpen <imageName> <destName>\n");
    }
    this.kernelFilterHelper(m);
    m.setImageName(destName);
    return m;
  }

  /**
   * A helper command method used in both the blur and sharpen classes.
   *
   * @param m the IPModel to be modified
   */
  private void kernelFilterHelper(IPModel m) {
    IPUtils utils = new IPUtils();
    int matrixStart = (this.kernel.length - 1) / 2;
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
        for (int matrixI = 0; matrixI < this.kernel.length; matrixI++) {
          for (int matrixJ = 0; matrixJ < this.kernel.length; matrixJ++) {
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
              sumRed += overlapPixel[0] * this.kernel[matrixI][matrixJ];
              sumGreen += overlapPixel[1] * this.kernel[matrixI][matrixJ];
              sumBlue += overlapPixel[2] * this.kernel[matrixI][matrixJ];
            }
          }
        }
        // make a new pixel with the new pixel components
        int newR = utils.capComponent((int) sumRed);
        int newG = utils.capComponent((int) sumGreen);
        int newB = utils.capComponent((int) sumBlue);
        int[] newPixel = new int[]{newR, newG, newB};
        newColumn.add(newPixel);
      }
      result.add(newColumn);
    }
    m.setWorkingImageData(result);
  }

}
