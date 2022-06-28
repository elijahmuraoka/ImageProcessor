package commands;

/**
 * This Command Factory is used to dynamically create unique Command objects
 * according to a specific string command parameter.
 */
public class CommandFactory {
  private final String command;

  /**
   * A CommandFactory constructor used to initialize the command field when given one.
   *
   * @param command the user command input represented as a string
   */
  public CommandFactory(String command) {
    this.command = command; // the command represented as a string
  }

  /**
   * Creates the appropriate IPCommand object based on the user's input.
   *
   * @return the IPCommand object created from the current command field
   * @throws IllegalStateException when the user's input does not match any valid command
   */
  public IPCommand createCommand() throws IllegalStateException {
    // switch cases for all valid user command inputs
    switch (this.command.toLowerCase()) {
      case "changebrightness":
      case "cb":
        return new ChangeBrightness();
      case "horizontalflip":
      case "flip-h":
      case "horizontal":
        return new HorizontalFlip();
      case "verticalflip":
      case "flip-v":
      case "vertical":
        return new VerticalFlip();
      case "gs":
      case "greyscale":
        return new GreyScale();
      case "gs-red":
        return new GreyScale("red");
      case "gs-green":
        return new GreyScale("green");
      case "gs-blue":
        return new GreyScale("blue");
      case "gs-luma":
        return new GreyScale("luma");
      case "gs-intensity":
        return new GreyScale("intensity");
      case "gs-value":
        return new GreyScale("value");
      case "blur":
        return new Blur();
      case "sharpen":
        return new Sharpen();
      case "sepia":
        return new Sepia();
      default:
        throw new IllegalStateException("Not a valid command. Please try again.\n");
    }
  }
}
