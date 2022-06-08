package controller;

/**
 * The Image Processor's controller interface which supports the 'go' method used to run the
 * application and transmit inputs and outputs between the view and the model.
 */
public interface IPController {

  /**
   * The main controller method which handles user inputs and reacts accordingly.
   *
   * @throws IllegalStateException when the controller is unable to successfully read the input
   *                               and/or output the value appropriately
   */
  void go() throws IllegalStateException;

  /**
   * Create and return any image's file name by appending its image path to its name.
   *
   * @param imageName the name of the image
   * @param imagePath the file path for a specific image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the image path is unrecognized/invalid
   */
  String generateFileName(String imageName, String imagePath) throws IllegalArgumentException;

  /**
   * Load an image from the specified path and refer it to henceforth in the program
   * by the given image name.
   *
   * @param imageName the name of the image
   * @param imagePath the file path for a specific image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the image path is unrecognized/invalid
   */
  void load(String imageName, String imagePath) throws IllegalArgumentException;

  /**
   * Save the image with the given name to the specified path which should
   * include the name of the file.
   *
   * @param imageName the name of the image
   * @param imagePath the file path for a specific image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the image path is unrecognized/invalid
   */
  void save(String imageName, String imagePath) throws IllegalArgumentException;
}