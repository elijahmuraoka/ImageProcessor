package view;

import java.io.IOException;

/**
 * The view interface. ADD MORE LATER.
 */
public interface IPView {

  /**
   * Transmits and outputs the given message to a later specified destination.
   *
   * @param message the string message to be passed on to the user
   *                through the view's appendable object.
   * @throws IOException when the message does not transmit/output correctly/
   */
  void renderMessage(String message) throws IOException;
}
