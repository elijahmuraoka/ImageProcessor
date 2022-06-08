package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Image Processor model interface.
 */
public class ImageModel implements IPModel {
  String imageName;
  // the height of the PPM image
  private int height;
  // the width of the PPM image
  private int width;
  // the working image displayed as a 1-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  private List<int[]> workingImageData;

  @Override
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public void setWorkingImageData(List<int[]> workingImageData) {
    this.workingImageData = workingImageData;
  }

  @Override
  public String getImageName() {
    return this.imageName;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public List<int[]> getWorkingImageData() {
    return this.workingImageData;
  }

  ////////////////////////

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
        int component = this.workingImageData.get(i)[j];
        // if the pixel component (RGB) is equal to or less than 245
        if (component <= 245) {
          // increase or decrease the new pixel component by the increment
          int newComponent = component + increment;
          // set the new (brighter or darker) pixel component
          this.workingImageData.get(i)[j] = newComponent;
        }
      }
    }
  }
}
