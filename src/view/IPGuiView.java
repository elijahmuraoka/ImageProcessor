package view;

import java.io.IOException;

import controller.BetterIPController;

public interface IPGuiView {

  void initialize();

  void showMainPanel(String imageName);

  /**
   * Refresh the screen when something is updated to show the new changes made.
   */
  void refresh();

  /**
   * Renders a specific message to the client.
   *
   * @param message the message to be transmitted.
   */
  void renderMessage(String message) throws IOException;

  void setController(BetterIPController controller);
}
