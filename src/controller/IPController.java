package controller;

/**
 * The Image Processor's controller interface which supports the 'go' method used to run the
 * application and transmit inputs and outputs between the view and the model.
 */
public interface IPController {

  /**
   * The main controller method which handles user inputs and reacts accordingly.
   *
   * @throws IllegalStateException when the controller is unable to successfully read the input
   *                               and/or output the value appropriately
   */
  void go() throws IllegalStateException;
}