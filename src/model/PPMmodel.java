package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Image Processor model interface.
 * This particular class takes in and manipulates PPM files (type of image).
 */
public class PPMmodel implements IPModel {
  String fileName;
  // the height of the PPM image
  int height;
  // the width of the PPM image
  int width;
  // the working image displayed as a 1-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  List<int[]> workingImage = new ArrayList<>();

  @Override
  public String generateFileName(String imageName, String imagePath)
          throws IllegalArgumentException {
    return imagePath + "/" + imageName + ".ppm";
  }

  @Override
  public void load(String imageName, String imagePath)
          throws IllegalArgumentException {
    // generates the fileName to initialize the image that this model will be working on
    this.fileName = this.generateFileName(imageName, imagePath);

    // how do you verify a correct computer path??
    // read the PPM file passed in
    IPUtil util = new IPUtil();
    util.readPPM(this.fileName);
    // make a copy of the PPM image data in this model
    this.width = util.width;
    this.height = util.height;
    this.workingImage = util.workingImage;
  }

  @Override
  public void save(String imageName, String imagePath) throws IllegalArgumentException {
    String Header = "P3\n" + this.width + " " + this.height + "\n255\n";
    StringBuilder imageData = new StringBuilder();
    for (int[] pixel : this.workingImage) {
      for (int component : pixel) {
        imageData.append(component).append(" ");
      }
    }

    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(this.generateFileName(imageName,
              imagePath)));
      bw.write(Header);
      bw.write(imageData.toString());
      bw.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid parameter(s), cannot save image file\n"
              + e.getMessage());
    }
  }

  @Override
  public void visualizeComponent(int[] color, String imageName, String destName)
          throws IllegalArgumentException {
  }

  @Override
  public void visualizeComponent(String visType, String imageName, String destName)
          throws IllegalArgumentException {

  }

  @Override
  public void horizontalFlip(String imageName, String destName)
          throws IllegalArgumentException {

  }

  @Override
  public void verticalFlip(String imageName, String destName)
          throws IllegalArgumentException {

  }

  @Override
  public void changeBrightness(int increment, String imageName, String destName)
          throws IllegalArgumentException {
    // LOAD THE APPROPRIATE IMAGE HERE FIRST?

    // for every pixel component in the working image
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        int component = this.workingImage.get(i)[j];
        // if the pixel component (RGB) is equal to or less than 245
        if (component <= 245) {
          // increase or decrease the new pixel component by the increment
          int newComponent = component + increment;
          // set the new (brighter or darker) pixel component
          this.workingImage.get(i)[j] = newComponent;
        }
      }
    }
  }
}
