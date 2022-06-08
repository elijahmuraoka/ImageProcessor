package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Image Processor model interface.
 */
public class ImageModel implements IPModel {
  // the currrent name of the image
  String imageName;
  // the height of the image
  private int height;
  // the width of the image
  private int width;
  // the working image data displayed as a 1-D list of Pixels
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
}
