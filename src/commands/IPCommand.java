package commands;

import java.util.Scanner;

import model.IPModel;

/**
 * This class represents any command that can be executed on a specific image.
 */
public interface IPCommand {

  /**
   * This method carries out a specific function, applying it to the given model.
   *
   * @param m    the Image Processor model to be used
   * @param scan the scanner used to read user inputs and execute this command
   */
  IPModel execute(IPModel m, Scanner scan);
}
