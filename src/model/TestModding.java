package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Classes for testing the File libraries in java
class Main {
  public static void main(String[] args) throws IOException {
    String fileName = "images/Koala1.ppm";
    int height;
    int width;
    List<int[]> workingImage;

    IPUtil util = new IPUtil();
    util.readPPM(fileName);
    workingImage = util.workingImage;

    /*
    File file = new File("images/Koala1.ppm");
    FileWriter fw = new FileWriter("images/Koala1.ppm");
    file.createNewFile();

    fw.write("P3\n# Koala1.ppm\n" +  + util.width + " " + util.height + "\n255\n");
     */

    FileWriter fw = new FileWriter("images/Koala1.ppm");

    for (int i = 0; i < util.height; i++) {
      for (int j = 0; j < util.width; j++) {
        if (Arrays.equals(workingImage.get(i), new int[]{123, 116, 90})) {
          workingImage.set(i, new int[]{0, 255, 0});
          System.out.println("Found one.");
        }
        else {
          System.out.println("Get == " + workingImage.get(i)[0] + "," + workingImage.get(i)[1] + "," + workingImage.get(i)[2]);
        }
        /*
        // MAKES EVERYTHING CYAN
        workingImage.set(i, new int[]{0, 255, 255});
        int[] pixCol = workingImage.get(i);
        String pixel = pixCol[0] + "\n" + pixCol[1] + "\n" + pixCol[2] + "\n";
        fw.write(pixel);
         */
      }
    }

    //fw.close();
  }
}

public class TestModding {
  int height;
  int width;
  List<int[]> workingImage = new ArrayList<>();

  public void blacken(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
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
    this.width = sc.nextInt();
    System.out.println("Width of image: " + width);
    this.height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        int[] cols = {r, g, b};
        this.workingImage.add(cols);
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }
}
