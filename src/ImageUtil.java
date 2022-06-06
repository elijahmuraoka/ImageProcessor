import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import controller.ImageController;
import controller.ImageControllerImpl;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 * as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    for (int i = 0; i < height; i++) {
      ArrayList<int[]> pixelData = new ArrayList<int[]>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        int[] cols = {r, g, b};
        pixelData.add(cols);
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("No arguments given.");
    }

    String filename;
    ImageController controller = new ImageControllerImpl(new InputStreamReader(System.in));
    Scanner scan = new Scanner(System.in);

    switch (args[0]) {
      case "load":
        try {
          System.out.println("Enter an image path: ");
          String imgPath = scan.next();
          System.out.println("Enter the image name you want to load: ");
          String imgName = scan.next();
          controller.load(imgPath, imgName);
          break;
        }
        catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Illegal args passed " + e.toString());
        }
      case "save":
        try {
          String imgPath = scan.nextLine();
          String imgName = scan.nextLine();
          controller.save(imgPath, imgName);
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }
      case "redComp":
        try {
          String imgName = scan.nextLine();
          String destName = scan.nextLine();
          controller.redComponent(imgName, destName);
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }
      case "flipH":
        try {
          String imgName = scan.nextLine();
          String destName = scan.nextLine();
          controller.horizontalFlip(imgName, destName);
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }
      case "flipV":
        try {
          String imgName = scan.nextLine();
          String destName = scan.nextLine();
          controller.verticalFlip(imgName, destName);
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }
      case "bright":
        try {
          int incr = scan.nextInt();
          String imgName = scan.nextLine();
          String destName = scan.nextLine();
          controller.brighten(incr, imgName, destName);
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }

      case "test":
        try {
          String dest = scan.nextLine();
          String name = scan.nextLine();;
          //fos.write(new String(matrix).getBytes());
          break;
        }
        catch (Exception e) {
          throw new IllegalArgumentException("Illegal args passed");
        }
      default:
        throw new RuntimeException("Runtime Exception.");
    }
  }

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

