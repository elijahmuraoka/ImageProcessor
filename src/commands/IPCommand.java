package commands;

import model.IPModel;

/**
 * This class represents any commands to be executed on a specific image.
 */
public interface IPCommand {

  /**
   * This method carries out a specific function which is applied to the given model.
   *
   * @param m the Image Processing model to be used
   * // @param c the controller for the Image Processor needed to pass messages to users
   */
  void execute(IPModel m);
}
