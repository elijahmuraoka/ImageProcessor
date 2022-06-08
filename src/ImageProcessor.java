import java.io.InputStreamReader;

import controller.IPController;
import controller.IPControllerImpl;
import model.IPModel;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

/**
 * This Image Processor application is used to edit photos and save them as a new file.
 */
public class ImageProcessor {

  /**
   * The main method used to run this Image Processor application.
   *
   * @param args the user's inputs represented as an array of strings
   */
  public static void main(String[] args) {
    // IPModel model = new ImageModel();
    IPView view = new IPViewImpl(System.out);
    Readable in = new InputStreamReader(System.in);
    IPController controller = new IPControllerImpl(view, in);
//    controller.load("Koala", "images");
//    controller.save("Koala2", "testImages");
    controller.go();
  }

  //  public static void main(String[] args) {
  //    if (args.length < 1) {
  //      throw new IllegalArgumentException("No arguments given.");
  //    }
  //
  //    String filename;
  //    IPController controller = new IPControllerImpl(new InputStreamReader(System.in));
  //    Scanner scan = new Scanner(System.in);
  //
  //    switch (args[0]) {
  //      case "load":
  //        try {
  //          System.out.println("Enter an image path: ");
  //          String imgPath = scan.next();
  //          System.out.println("Enter the image name you want to load: ");
  //          String imgName = scan.next();
  //          controller.load(imgPath, imgName);
  //          break;
  //        } catch (IllegalArgumentException e) {
  //          throw new IllegalArgumentException("Illegal args passed " + e.toString());
  //        }
  //      case "save":
  //        try {
  //          String imgPath = scan.nextLine();
  //          String imgName = scan.nextLine();
  //          controller.save(imgPath, imgName);
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //      case "redComp":
  //        try {
  //          String imgName = scan.nextLine();
  //          String destName = scan.nextLine();
  //          controller.redComponent(imgName, destName);
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //      case "flipH":
  //        try {
  //          String imgName = scan.nextLine();
  //          String destName = scan.nextLine();
  //          controller.horizontalFlip(imgName, destName);
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //      case "flipV":
  //        try {
  //          String imgName = scan.nextLine();
  //          String destName = scan.nextLine();
  //          controller.verticalFlip(imgName, destName);
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //      case "bright":
  //        try {
  //          int incr = scan.nextInt();
  //          String imgName = scan.nextLine();
  //          String destName = scan.nextLine();
  //          controller.ChangeBrightness(incr, imgName, destName);
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //
  //      case "test":
  //        try {
  //          String dest = scan.nextLine();
  //          String name = scan.nextLine();
  //          ;
  //          //fos.write(new String(matrix).getBytes());
  //          break;
  //        } catch (Exception e) {
  //          throw new IllegalArgumentException("Illegal args passed");
  //        }
  //      default:
  //        throw new RuntimeException("Runtime Exception.");
  //    }

  /*
  //demo main
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }

    ImageUtil.readPPM(filename);
  }
   */
}
