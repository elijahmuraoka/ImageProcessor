package model;

import java.util.List;

/**
 * This is a utility interface used to handle various image files.
 * The interface for a generic Image model which supports various operations that
 * can be used to retrieve and set key pieces of image data.
 */
public interface IPModel {
  /**
   * Read any image file and store its data.
   *
   * @throws IllegalStateException when the file cannot be read properly.
   */
  void read() throws IllegalStateException;

  /**
   * Appends the appropriate extension and makes an appropriate file name
   * given a name and image path.
   *
   * @param saveAsName the name to save this image as
   * @param imagePath  the path of the file.
   * @return the new full image file name
   */
  String generateFileName(String saveAsName, String imagePath);

  /**
   * Sets the image name field for this model using the given value.
   *
   * @param imageName the name of the image
   */
  void setImageName(String imageName);

  /**
   * Sets the width field for this model using the given value.
   *
   * @param width the width of the image.
   * @throws IllegalArgumentException when the width is negative or equal to zero
   */
  void setWidth(int width) throws IllegalArgumentException;

  /**
   * Sets the height field for this model using the given value.
   *
   * @param height the height of the image.
   * @throws IllegalArgumentException when the height is negative or equal to zero
   */
  void setHeight(int height) throws IllegalArgumentException;

  /**
   * Sets the max component field for this model using the given value.
   *
   * @param maxComponent the maximum color component
   * @throws IllegalArgumentException when the maximum color component is negative or equal to zero
   */
  void setMaxComponent(int maxComponent) throws IllegalArgumentException;

  /**
   * Sets the workingImageData field for this model using the given value.
   *
   * @param workingImageData a copy of the original image's pixel data represented as an array list
   *                         of size-3 integer arrays representing a single pixel and its
   *                         RGB values respectively.
   */
  void setWorkingImageData(List<List<int[]>> workingImageData);

  /**
   * Retrieve the name of this image model.
   *
   * @return the image name field
   */
  String getImageName();

  /**
   * Retrieve the width of this image model.
   *
   * @return the width field
   */
  int getWidth();

  /**
   * Retrieve the height of this image model.
   *
   * @return the height field
   */
  int getHeight();

  /**
   * Retrieve the max color component of this image model.
   *
   * @return the max color component
   */
  int getMaxComponent();

  /**
   * Retrieve the pixel data of this image model.
   *
   * @return the workingImageData field
   */
  List<List<int[]>> getWorkingImageData();

  /**
   * Retrieve the file name of this image model.
   *
   * @return the file name of this image model.
   */
  String getFileName();
}
