package commands;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import view.IPView;

/**
 * The command is used to change the brightness of a certain image.
 * This means all RGB values increase by a set increment amount.
 */
public class changeBrightness implements IPCommand {
  int increment;
  String imageName;
  String destName;

  /**
   * A changeBrightness command constructor that takes in a scanner and view.
   *
   * @param sc the scanner
   * @param v  the Image Processor view
   * @throws IOException when the input(s) and/or output(s) cannot be transmitted properly
   */
  changeBrightness(Scanner sc, IPView v) throws IOException {
    try {
      this.increment = sc.nextInt();
      this.imageName = sc.next();
      this.destName = sc.next();
    } catch (NoSuchElementException e) {
      v.renderMessage("The changeBrightness command was not called properly.\n" +
              "Please pass in new parameters with the following format:\n" +
              "changeBrightness <increment> <imageName> <destName>");
    }
  }

  @Override
  public void execute(IPModel m) {
    m.changeBrightness(this.increment, this.imageName, this.destName);
  }
}
