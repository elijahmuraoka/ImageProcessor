import java.io.InputStreamReader;

import controller.IPController;
import controller.IPControllerImpl;
import view.IPView;
import view.IPViewImpl;

/**
 * This Image Processor application is used to edit photos and save them as a new file.
 */
public class ImageProcessor {

  /**
   * The main method used to run this Image Processor application.
   *
   * @param args the user's inputs represented as an array of strings.
   */
  public static void main(String[] args) {
    IPView view = new IPViewImpl();
    Readable in = new InputStreamReader(System.in);
    IPController controller = new IPControllerImpl(view, in);
    controller.run();
  }
}
