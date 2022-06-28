package view;

import java.io.IOException;

import controller.BetterIPController;

/**
 * The GUI interface which supports all necessary operations such as
 * displaying the screens, rendering messages to the user, or refreshing the screen.
 */
public interface IPGuiView {
  /**
   * Initializes the GUI view and all its necessary fields.
   * Must be called after initializing this view's controller respective controller.
   */
  void initialize();

  /**
   * Make the main image processor panel visible to the client.
   *
   * @param imageName the name of the main image
   * @throws IllegalStateException when the main image cannot be created properly
   */
  void showMainPanel(String imageName) throws IllegalStateException;

  /**
   * Refresh the screen when something is updated to show the new changes made.
   *
   * @throws IllegalStateException when the main image cannot be created properly
   */
  void refresh() throws IllegalStateException;

  /**
   * Renders a specific message to the client.
   *
   * @param message the message to be transmitted.
   */
  void renderMessage(String message) throws IOException;

  /**
   * Sets the given controller as this view's controller field.
   *
   * @param controller the controller parameter
   */
  void setController(BetterIPController controller);
}
