package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;

/**
 * The command is used to flip an image vertically.
 */
public class VerticalFlip implements IPCommand {

  /**
   * Flip an image vertically to create a new image, referred to henceforth by the given
   * destination name.
   *
   * @param m    the Image represented model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the modified IPModel that is now flipped vertically.
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The VerticalFlip command was not called properly.\n"
              + "Please pass in new parameters with the following format.\n"
              + "\nHere is an example:\n"
              + "VerticalFlip <imageName> <destName>\n");
    }

    // for the first half of the image's rows
    for (int i = 0; i < m.getHeight() / 2; i++) {
      // for all columns
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          int oppositeY = m.getHeight() - i - 1;
          int component1 = m.getWorkingImageData().get(i).get(j)[k];
          int component2 = m.getWorkingImageData().get(oppositeY).get(j)[k];
          m.getWorkingImageData().get(i).get(j)[k] = component2;
          m.getWorkingImageData().get(oppositeY).get(j)[k] = component1;
        }
      }
    }
    m.setImageName(destName);
    return m;
  }
}
