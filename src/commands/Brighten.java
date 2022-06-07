package commands;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import view.IPView;

/**
 * The command is used to brighten a certain image.
 * This means all RGB values increase by a set increment amount.
 */
public class Brighten implements IPCommand {
  int increment;
  String imageName;
  String destName;

  Brighten(Scanner sc, IPView v) throws IOException {
    try {
      this.increment = sc.nextInt();
      this.imageName = sc.next();
      this.destName = sc.next();
    } catch (NoSuchElementException e) {
      v.renderMessage("The Brighten command was not called properly.\n" +
              "Please pass in new parameters with the following format:\n" +
              "Brighten <increment> <imageName> <destName>");
    }
  }

  @Override
  public void execute(IPModel m) {
    int increment;
    m.brighten(this.increment, this.imageName, this.destName);
  }
}
