package controller;

import java.io.IOException;

/**
 * The Image Processor's controller interface which supports the 'run' method used to run the
 * application and transmit inputs and outputs between the view and the model.
 */
public interface IPController {

  /**
   * The main controller method which handles user inputs and reacts accordingly.
   *
   * @throws IllegalStateException when the controller is unable to successfully read the input
   *                               and/or output the value appropriately
   */
  void run() throws IllegalStateException;

  /**
   * Load an image from the specified path and refer it to henceforth in the program
   * by the given image name.
   *
   * @param imageName the name of the image
   * @param imagePath the file path for a specific image
   * @throws IOException when unable to transmit the input(s) and/or output(s) properly
   */
  void load(String imageName, String imagePath) throws IOException;

  /**
   * Save an image using its current name and new save name to the specified path.
   *
   * @param imageName  the current name of the image
   * @param saveAsName the name used to save this image as
   * @param imagePath  the file path for a specific image
   * @param extension  the desired extension and file type of the image
   * @throws IOException when unable to transmit the input(s)
   *                                  and/or output(s) properly
   */
  void save(String imageName, String saveAsName, String imagePath, String extension)
          throws IOException;
}