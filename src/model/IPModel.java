package model;

import java.util.List;

/**
 * The interface for a generic Image Processor model which supports various operations that
 * can be applied to a specific image.
 */
public interface IPModel {

  void setImageName(String imageName);

  void setWidth(int width);

  void setHeight(int height);

  void setWorkingImageData(List<int[]> workingImageData);

  String getImageName();

  int getWidth();

  int getHeight();

  List<int[]> getWorkingImageData();

}