import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Random;
import java.util.Scanner;

import controller.BetterIPControllerImpl;
import controller.IPController;
import controller.IPControllerImpl;
import view.IPGuiView;
import view.IPView;
import view.IPViewImpl;
import view.SimpleGuiView;

/**
 * This Image Processor application is used to load, edit, and save photos.
 * This main method can respond to program arguments in 3 different ways...
 * - Passing in no arguments results in the creation of the Image Processor GUI Program
 * - Passing in "-text" results in the creation of the Image Processor Text-Based Program
 * - Passing in "-file" followed by a valid script file path allows the script to be read
 * and run through our application smoothly using various commands (You can also string together
 * multiple files to run at once. E.g. -file file1Path -file file2Path -file file3Path ...
 */
public class ImageProcessor {

  /**
   * The main method used to run this Image Processor application.
   *
   * @param args the user's inputs represented as an array of strings.
   */
  public static void main(String... args) throws IOException {
    Readable in;
    IPView view;
    StringBuilder result = new StringBuilder();
    if (args.length == 0) {
      IPGuiView guiView = new SimpleGuiView();
      BetterIPControllerImpl controller = new BetterIPControllerImpl(guiView);
      guiView.initialize();
    } else {
      for (String arg : args) {
        result.append(arg).append(" ");
      }
      Readable reader = new StringReader(result.toString());
      Scanner scan = new Scanner(reader);
      while (scan.hasNext()) {
        String current = scan.next();
        switch (current.toLowerCase()) {
          case "-file": {
            try {
              view = new IPViewImpl();
              in = new FileReader(scan.next());
              IPController controller = new IPControllerImpl(view, in);
              controller.run();
            } catch (FileNotFoundException e) {
              System.out.println("This file does not exist. Please fix program inputs.\n");
            }
            break;
          }
          case "-text": {
            view = new IPViewImpl();
            in = new InputStreamReader(System.in);
            IPController controller = new IPControllerImpl(view, in);
            controller.run();
            return;
          }
          default:
        }
      }
    }
  }
}
