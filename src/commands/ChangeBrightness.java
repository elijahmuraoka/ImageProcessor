package commands;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;

/**
 * The command is used to change the brightness of a certain image.
 * This means all RGB values increase by a set increment amount.
 */
public class ChangeBrightness implements IPCommand {
  // how much to add to/subtract from the image's rgb values.
  int increment;
  // the name of the image
  String imageName;
  // the new destination name representing the image
  String destName;

  /**
   * A ChangeBrightness command constructor that takes in a scanner and view.
   *
   * @param sc the scanner
   * @throws IOException when the input(s) and/or output(s) cannot be transmitted properly
   */
//  public ChangeBrightness(Scanner sc) {
//    try {
//      this.increment = sc.nextInt();
//      this.destName = sc.next();
//    } catch (NoSuchElementException e) {
//      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n" +
//              "Please pass in new parameters with the following format:\n" +
//              "ChangeBrightness <increment> <imageName> <destName>");
//    }
//  }

  /**
   * Change the brightness of the image by the given increment to create a new image, referred to henceforth by the
   * given destination name. The increment may be positive (brightening) or negative (darkening).
   *
   * @param m
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the destination name has been used already.
   */
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    try {
      this.increment = scan.nextInt();
      this.destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n" +
              "Please pass in new parameters with the following format:\n" +
              "ChangeBrightness <increment> <imageName> <destName>");
    }

    // for every pixel component in the working image
    for (int i = 0; i < m.getWorkingImageData().size(); i++) {
      for (int j = 0; j < 3; j++) {
        int component = m.getWorkingImageData().get(i)[j];
        // increase or decrease the new pixel component by the increment
        int newComponent = component + this.increment;
        if (newComponent > 255) {
          newComponent = 255;
        }
        if (newComponent < 0) {
          newComponent = 0;
        }
        // set the new (brighter or darker) pixel component
        m.getWorkingImageData().get(i)[j] = newComponent;
      }
    }
    m.setImageName(this.destName);
    return m;
  }
}
