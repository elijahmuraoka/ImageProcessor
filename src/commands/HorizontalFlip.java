package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.BetterIPModel;

/**
 * The command is used to flip an image horizontally.
 */
public class HorizontalFlip implements IPCommand {

  /**
   * Flip an image horizontally to create a new image, referred to henceforth by the given
   * destination name.
   *
   * @param m    the Image represented model to be used and acted on.
   * @param scan the scanner used to read and retrieve user inputs.
   * @return the modified IPModel that is now flipped horizontally.
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed.
   */
  @Override
  public BetterIPModel execute(BetterIPModel m, Scanner scan) throws IllegalStateException {
    // the new destination name representing the image
    String destName;
    try {
      destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The HorizontalFlip command was not called properly.\n"
              + "Please pass in new parameters with the correct format.\n"
              + "\nHere is an example:\n"
              + "HorizontalFlip <imageName> <destName>\n");
    }
    // for all rows
    for (int i = 0; i < m.getHeight(); i++) {
      // for the first half of the image's columns
      for (int j = 0; j < m.getWidth() / 2; j++) {
        for (int k = 0; k < 3; k++) {
          int oppositeX = m.getWidth() - j - 1;
          int component1 = m.getWorkingImageData().get(i).get(j)[k];
          int component2 = m.getWorkingImageData().get(i).get(oppositeX)[k];
          m.getWorkingImageData().get(i).get(j)[k] = component2;
          m.getWorkingImageData().get(i).get(oppositeX)[k] = component1;
        }
      }
    }
    m.setImageName(destName);
    return m;
  }
}
