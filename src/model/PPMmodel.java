package model;

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
    return imagePath + "/" + imageName;
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
  public void save(String imagePath, String imageName) throws IllegalArgumentException {

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
  public void brighten(int increment, String imageName, String destName)
          throws IllegalArgumentException {

  }
}
