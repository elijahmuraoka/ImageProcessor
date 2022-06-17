package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import utils.IPUtils;

/**
 * This abstract class is used when creating any Image Processor editing features that use a matrix
 * to transform the color (RGB values) of an image. Use matrix multiplication to change a
 * pixel's RGB components accordingly.
 */
public abstract class AbstractTransformColor implements IPCommand {
  protected double[][] matrix; // the matrix needed to transform the image's color

  /**
   * An empty public constructor for the AbstractKernelFilter class
   * used to initialize a specific matrix needed for the color transformation.
   */
  public AbstractTransformColor() {
    this.matrix = this.generateColorMatrix();
  }

  /**
   * Generate the matrix needed to transform the image's color.
   *
   * @return the color matrix as a 2D array list of doubles
   */
  protected abstract double[][] generateColorMatrix();

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
    this.transformColorHelper(m, this.generateColorMatrix());
    m.setImageName(destName);
    return m;
  }

  /**
   * A helper method used that can be used in color transformation commands.
   *
   * @param m      the IPModel to be modified
   * @param matrix the matrix which will be used in the image processing algorithm to either blur
   *               or sharpen an image
   */
  private void transformColorHelper(IPModel m, double[][] matrix) {
    IPUtils utils = new IPUtils();
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
          newPixel[matrixI] = utils.capComponent(m, (int) newComponent);
        }
        newColumn.add(newPixel);
      }
      result.add(newColumn);
    }
    m.setWorkingImageData(result);
  }
}
