package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;

public class Sepia implements IPCommand {
  private double[][] matrix;

  public Sepia() {
    this.matrix = new double[][]{
            new double[]{0.393, 0.769, 0.189},
            new double[]{0.349, 0.686, 0.168},
            new double[]{0.272, 0.534, 0.131},
    };
  }

  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The GreyScale command was not called properly.\n"
              + "Please pass in new parameters with the correct format.\n"
              + "\nHere is an example:\n"
              + "GreyScale <imageName> <visType> <destName>\n");
    }
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        int[] current = m.getWorkingImageData().get(i).get(j);
        sepiaHelper(current, this.matrix);
      }
    }
    m.setImageName(destName);
    return m;
  }

  private void sepiaHelper(int[] pixel, double[][] matrix) {
    double[] sum = {0.0, 0.0, 0.0};

    //for each component value
    for (int i = 0; i < pixel.length; i++) {

      //for each matrix element
      for (int j = 0; j < matrix.length; j++) {
        for (int k = 0; k < matrix.length; k++) {
          // multiply this pixel component value by every element in the matrix.
          // then add that value to a summation matrix
          sum[i] += pixel[i] * matrix[j][k];
        }
      }
    }

    pixel[0] = (int) (sum[0]);
    pixel[1] = (int) (sum[1]);
    pixel[2] = (int) (sum[2]);
  }
}

/*

import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
import commands.HorizontalFlip;
 */