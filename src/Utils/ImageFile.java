package Utils;

import java.io.IOException;
import java.util.List;

import view.IPView;

/**
 * This is a utility interface used to handle various image files.
 */
public interface ImageFile {
  /**
   * Read any image file and store its data.
   *
   * @param v the Image Processor's view to render necessary message
   * @throws IOException when either the input(s) and/or output(s) are invalid
   */
  void read(IPView v) throws IOException;

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
   * Retrieves the width of the image file.
   *
   * @return the width of the image
   */
  int getWidth();

  /**
   * Retrieves the height of the image file.
   *
   * @return the height of the image
   */
  int getHeight();

  /**
   * Retrieve the max color component of this image model.
   *
   * @return the max color component
   */
  int getMaxComponent();

  /**
   * Retrieves the image data of the image file.
   *
   * @return the 2-D array list of pixels representing the image's data
   */
  List<List<int[]>> getWorkingImageData();
}
