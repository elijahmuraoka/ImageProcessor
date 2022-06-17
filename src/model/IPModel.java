package model;

import java.util.List;

/**
 * The interface for a generic Image model which supports various operations that
 * can be used to retrieve and set key pieces of image data.
 */
public interface IPModel {

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
   */
  void setWidth(int width) throws IllegalArgumentException;

  /**
   * Sets the height field for this model using the given value.
   *
   * @param height the height of the image.
   */
  void setHeight(int height) throws IllegalArgumentException;

  /**
   * Sets the height field for this model using the given value.
   *
   * @param maxComponent the maximum color component
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
   * @return the image field
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
}