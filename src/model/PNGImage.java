package model;

import java.util.List;

/**
 * A PNG image file and model representation.
 */
public class PNGImage extends BetterAbstractImageModel {
  /**
   * The abstract image file constructor used to create an Image File object and store its
   * file name.
   *
   * @param fileName the (desired) name of the file
   */
  public PNGImage(String fileName) {
    super(fileName);
  }

  /**
   * A super constructor that takes in all fields and creates a PNG image object.
   *
   * @param imageName        the name of the image
   * @param height           the height of the image
   * @param width            the width of the image
   * @param maxComponent     the maximum color component
   * @param workingImageData the 2D array list of pixels representing the image's data
   * @param fileName         the name of the image file
   * @throws IllegalArgumentException when the width, height, or maximum color component is
   *                                  less than zero
   */
  public PNGImage(String imageName, int height, int width, int maxComponent,
                  List<List<int[]>> workingImageData, String fileName)
          throws IllegalArgumentException {
    super(imageName, height, width, maxComponent, workingImageData, fileName);
  }

  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".png";
  }
}
