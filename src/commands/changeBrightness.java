package commands;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import view.IPView;

/**
 * The command is used to change the brightness of a certain image.
 * This means all RGB values increase by a set increment amount.
 */
public class changeBrightness implements IPCommand {
  // how much to add to/subtract from the image's rgb values.
  int increment;
  // the name of the image
  String imageName;
  // the new destination name representing the image
  String destName;

  /**
   * A changeBrightness command constructor that takes in a scanner and view.
   *
   * @param sc the scanner
   * @param v  the Image Processor view
   * @throws IOException when the input(s) and/or output(s) cannot be transmitted properly
   */
  changeBrightness(Scanner sc, IPView v) throws IOException {
    try {
      this.increment = sc.nextInt();
      this.imageName = sc.next();
      this.destName = sc.next();
    } catch (NoSuchElementException e) {
      v.renderMessage("The changeBrightness command was not called properly.\n" +
              "Please pass in new parameters with the following format:\n" +
              "changeBrightness <increment> <imageName> <destName>");
    }
  }

  /**
   * Change the brightness of the image by the given increment to create a new image, referred to henceforth by the
   * given destination name. The increment may be positive (brightening) or negative (darkening).
   *
   * @param m
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the destination name has been used already.
   */
  public IPModel execute(IPModel m) {
    // LOAD THE APPROPRIATE IMAGE HERE FIRST?

    // for every pixel component in the working image
    for (int i = 0; i < m.getWidth(); i++) {
      for (int j = 0; j < m.getHeight(); j++) {
        int component = m.getWorkingImageData().get(i)[j];
        // if the pixel component (RGB) is equal to or less than 245
        if (component <= 245) {
          // increase or decrease the new pixel component by the increment
          int newComponent = component + this.increment;
          // set the new (brighter or darker) pixel component
          m.getWorkingImageData().get(i)[j] = newComponent;
        }
      }
    }
    m.setImageName(this.destName);
  }
}
