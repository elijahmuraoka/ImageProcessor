package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;

/**
 * The command is used to change the brightness of a certain image.
 * This means all RGB values increase or decrease by a set increment amount.
 */
public class ChangeBrightness implements IPCommand {
  // how much to add to/subtract from the image's rgb values.
  int increment;
  // the new destination name representing the image
  String destName;

  /**
   * Change the brightness of the image by the given increment to create a new image,
   * referred to henceforth by the given destination name.
   * The increment may be positive (brightening) or negative (darkening).
   *
   * @param m    the Image model to be used and acted on
   * @param scan the scanner used to read and retrieve user inputs
   * @return the modified image model with a new level of brightness
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed
   */
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    try {
      this.increment = scan.nextInt();
      this.destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n" +
              "Please pass in new parameters with the following format:\n" +
              "ChangeBrightness <increment> <imageName> <destName>\n");
    }
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++)
        for (int k = 0; k < 3; k++) {
          int component = m.getWorkingImageData().get(i).get(j)[k];
          // increase or decrease the new pixel component by the increment
          int newComponent = component + this.increment;
          if (newComponent > 255) {
            newComponent = 255;
          }
          if (newComponent < 0) {
            newComponent = 0;
          }
          // set the new (brighter or darker) pixel component
          m.getWorkingImageData().get(i).get(j)[k] = newComponent;
        }
    }
    m.setImageName(this.destName);
    return m;
  }
}
