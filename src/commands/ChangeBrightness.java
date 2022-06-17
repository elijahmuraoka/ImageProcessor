package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import utils.IPUtils;
import model.IPModel;

/**
 * The command is used to change the brightness of a certain image.
 * This means all RGB values increase or decrease by a set increment amount.
 */
public class ChangeBrightness implements IPCommand {

  /**
   * Change the brightness of the image by the given increment to create a new image,
   * referred to henceforth by the given destination name.
   * The increment may be positive (brightening) or negative (darkening).
   *
   * @param m    the Image model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the modified image model with a new level of brightness.
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    IPUtils utils = new IPUtils();
    // how much to add to/subtract from the image's rgb values.
    int increment;
    // the new destination name representing the image
    String destName;
    try {
      increment = scan.nextInt();
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere is an example:\n"
              + "ChangeBrightness <increment> <imageName> <destName>\n");
    }
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          int component = m.getWorkingImageData().get(i).get(j)[k];
          // increase or decrease the new pixel component by the increment
          int newComponent = component + increment;
          // set the new (brighter or darker) pixel component
          m.getWorkingImageData().get(i).get(j)[k] = utils.capComponent(m, newComponent);
        }
      }
    }
    m.setImageName(destName);
    return m;
  }
  //  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
  //    try {
  //      this.increment = scan.nextInt();
  //      this.destName = scan.next();
  //    } catch (NoSuchElementException e) {
  //      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n"
  //              + "Please pass in new parameters with the following format.\n"
  //              + "\nHere is an example:\n"
  //              + "ChangeBrightness <increment> <imageName> <destName>\n");
  //    }
  //    // for every pixel component in the working image
  //    for (int i = 0; i < m.getHeight(); i++) {
  //      for (int j = 0; j < m.getWidth(); j++) {
  //        int[] current = m.getWorkingImageData().get(i).get(j);
  //        m.getWorkingImageData().get(i).set(j, this.cbHelper(current));
  //      }
  //    }
  //    m.setImageName(this.destName);
  //    return m;
  //  }
  //
  //  /**
  //   * The helper method used to change the brightness of a pixel.
  //   *
  //   * @param pixel a size-3 array of integers each representing a red, green, and blue
  //   *              component respectively
  //   * @return a new pixel with its components incremented(or decremented) appropriately
  //   */
  //  private int[] cbHelper(int[] pixel) {
  //    int r = pixel[0];
  //    int g = pixel[1];
  //    int b = pixel[2];
  //    int newR = (r + this.increment);
  //    int newG = (g + this.increment);
  //    int newB = (b + this.increment);
  //    int newIncrement = this.increment;
  //    // if any of the components plus the increment are above 255
  //    if (newR > 255 || newG > 255 || newB > 255) {
  //      newIncrement = 255 - ((Math.max(newR, Math.max(newG, newB))) - this.increment);
  //    }
  //    // if any of the components minus the increment are below 0
  //    else if (newR < 0 || newG < 0 || newB < 0) {
  //      newIncrement = this.increment - (Math.min(newR, Math.min(newG, newB)));
  //    }
  //
  //
  //    r += newIncrement;
  //    g += newIncrement;
  //    b += newIncrement;
  //    int[] newPixel = {r, g, b};
  //    return newPixel;
  //  }
}
