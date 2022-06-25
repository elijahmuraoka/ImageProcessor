package commands;

import java.util.Scanner;

import model.BetterIPModel;

/**
 * This class represents any command that can be executed on a specific image.
 */
public interface IPCommand {

  /**
   * This method carries out a specific function, applying it to the given model.
   *
   * @param m    the Image represented model to be used and acted on
   * @param scan the scanner used to read and retrieve user inputs
   * @return the modified Image model
   * @throws IllegalStateException when the image data retrieved from the scanner does
   *                               not meet the required command arguments needed
   */
  BetterIPModel execute(BetterIPModel m, Scanner scan) throws IllegalStateException;
}
