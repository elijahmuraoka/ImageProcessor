import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.IPController;
import controller.IPControllerImpl;
import view.IPView;
import view.IPViewImpl;

/**
 * This Image Processor application is used to load, edit, and save photos.
 */
public class ImageProcessor {

  /**
   * The main method used to run this Image Processor application.
   *
   * @param args the user's inputs represented as an array of strings.
   */
  public static void main(String... args) throws IOException {
    Readable in = null;
    IPView view = new IPViewImpl();
    if (args.length > 0) {
      for (String arg : args) {
        try {
          in = new FileReader(arg);
          IPController controller = new IPControllerImpl(view, in);
          controller.run();
        } catch (FileNotFoundException e) {
          view.renderMessage("This file does not exist. Please fix program inputs.\n");
        }
      }
    } else {
      in = new InputStreamReader(System.in);
      IPController controller = new IPControllerImpl(view, in);
      controller.run();
    }
  }
}
