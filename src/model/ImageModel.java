package model;

import java.util.List;

/**
 * An implementation of the Image Processor model interface.
 */
public class ImageModel implements IPModel {
  // the current name of the image
  private String imageName;
  // the height of the image
  private int height;
  // the width of the image
  private int width;
  // the working image data displayed as a 2-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  private List<List<int[]>> workingImageData;

  /**
   * An empty Image model constructor.
   */
  public ImageModel() {
    // generates an empty image model object that can be initialized later
  }

  /**
   * An image model constructor used to create representative image objects using
   * the given image name, height, width, and a 2D array of pixels (size-3 arrays)
   * representing the image's data.
   *
   * @param imageName        the current name of the image.
   * @param height           the height of the image.
   * @param width            the width of the image.
   * @param workingImageData the working image data displayed as a 2-D list of Pixels
   *                         Each Pixel is an array of 3 integers that represent
   *                         its RGB components respectively.
   * @throws IllegalArgumentException when the image's height and/or width are negative.
   */
  public ImageModel(String imageName, int height, int width,
          List<List<int[]>> workingImageData) throws IllegalArgumentException {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Image height and width must be greater than 0.");
    }
    this.imageName = imageName;
    this.height = height;
    this.width = width;
    this.workingImageData = workingImageData;
  }

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
  public void setWorkingImageData(List<List<int[]>> workingImageData) {
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
  public List<List<int[]>> getWorkingImageData() {
    return this.workingImageData;
  }
}
