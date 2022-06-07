package model;

import java.util.ArrayList;
import java.util.List;

/**
 * FILL IN HERE.
 */
public class IPModelImpl implements IPModel {
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
  public String generateFileName(String imageName, String imagePath) throws IllegalArgumentException {
    return imagePath + "/" + imageName;
  }

  @Override
  public void load(String imageName, String imagePath) throws IllegalArgumentException {
    // generates the fileName to initialize the image that this model will be working on
    this.fileName = this.generateFileName(imageName, imagePath);

    // how to verify a correct computer path??
    IPUtil util = new IPUtil();
    util.readPPM(this.fileName);
    this.width = util.width;
    this.height = util.height;
    this.workingImage = util.workingImage;
  }

  @Override
  public void save(String imagePath, String imageName) {
    /*
    FileOutputStream fos = new FileOutputStream("" + imagePath + imageName);
    fos.write(new String(matrix).getBytes());
     */
    // create new image and copy into a new file destination?
  }

  @Override
  public void visualizeComponent(int[] color, String imageName, String destName) throws IllegalArgumentException {

  }

  @Override
  public void visualizeComponent(String visType, String imageName, String destName) throws IllegalArgumentException {

  }

  @Override
  public void horizontalFlip(String imageName, String imagePath) {

  }

  @Override
  public void verticalFlip(String imageName, String imagePath) {

  }

  @Override
  public void brighten(int increment, String imageName, String imagePath) {

  }
}
