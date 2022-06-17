package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import utils.IPUtils;

/**
 * This command is used to blur an image using a filtering algorithm.
 * In this class, we used a 3x3 kernel matrix to create what is known as
 * a "Gaussian" blur (imagine the values to be heights of a 3D bar graph
 * to see how they form a coarse bell-like Gaussian surface). Blurring can be done
 * by applying the filter to every channel of every pixel to produce the output image.
 */
public class Blur extends AbstractKernelFilter {
  /**
   * Generates a specific kernel matrix used to blur an image.
   *
   * @return a 2-D array representing the matrix to be used in the kernel filtering algorithm
   */
  @Override
  protected double[][] generateKernelMatrix() {
    return new double[][]{
        new double[]{1.0 / 16, 1.0 / 8, 1.0 / 16},
        new double[]{1.0 / 8, 1.0 / 4, 1.0 / 8},
        new double[]{1.0 / 16, 1.0 / 8, 1.0 / 16}};
  }
}
