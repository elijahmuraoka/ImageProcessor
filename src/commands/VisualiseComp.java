package commands;

import java.util.NoSuchElementException;
import java.util.Scanner;
import model.IPModel;

public class VisualiseComp implements IPCommand {
  String imageName;
  String component;
  String destName;
  private int[] pixel;

  @Override
  public IPModel execute(IPModel m, Scanner scan) throws IllegalStateException {
    try {
      this.component = scan.next();
      this.destName = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("The ChangeBrightness command was not called properly.\n"
              + "Please pass in new parameters with the following format:\n"
              + "ChangeBrightness <increment> <imageName> <destName>\n");
    }

    /*
    load Koala1 images
    Visualise Koala1 red KoalaRed
     */

    System.out.println(this.imageName + "/" + this.component + "/" + this.destName);
    // for every pixel component in the working image
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        pixel = m.getWorkingImageData().get(i).get(j);
        System.out.println(pixel[0]);
        vHelper(this.component, pixel);
      }
    }
    m.setImageName(this.destName);
    return m;
  }

  private void vHelper(String s, int[] pixel) {
    int r = pixel[0];
    int g = pixel[1];
    int b = pixel[2];

    //int grey = (int)((0.2989 * r) + (0.5870 * g) + (0.1140 * b));

    switch (s) {
      case "red":
        pixel[1] = pixel[0];
        pixel[2] = pixel[0];
        break;
      case "green":
        pixel[0] = pixel[1];
        pixel[2] = pixel[1];
        break;
      case "blue":
        pixel[0] = pixel[2];
        pixel[1] = pixel[2];
        break;
      default:
        throw new IllegalArgumentException("Invalid colour parameter: " + s);
    }
  }
}
