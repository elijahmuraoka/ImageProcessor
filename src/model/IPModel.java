package model;

/**
 * The interface for a generic Image Processor model which supports various operations that
 * can be applied to a specific image.
 */
public interface IPModel {

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
  void load(String imagePath, String imageName) throws IllegalArgumentException;

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
  void save(String imagePath, String imageName) throws IllegalArgumentException;

  /**
   * Create a greyscale image with the red-component of the image with the given name,
   * and refer to it henceforth in the program by the given destination name.
   * <p>
   * For example, if red is specified, then grey out everything and visualize the red components
   * by darkening every pixel closest to the color red, and whitening everything else.
   *
   * @param color     an array of 3 integers representing the component
   * @param imageName the name of the image
   * @param destName  the new destination name representing the image
   * @throws IllegalArgumentException when...
   *                                  - the pixel's array list of integers (components) is not
   *                                  equal to three.
   *                                  - the image name is unrecognized/invalid.
   *                                  - the destination name has been used already.
   */
  void visualizeComponent(int[] color, String imageName, String destName)
          throws IllegalArgumentException;

  /**
   * Create images that visualize the value, intensity or luma of an image as defined below.
   *
   * @param visType   a string value that must be one of:
   *                  - value: the maximum value of the three components for each pixel
   *                  - intensity: the average of the three components for each pixel
   *                  - luma: the weighted sum (0.2126 * R) + (0.7152 * G) + (0.0722 * B)
   * @param imageName the name of the image
   * @param destName  the new destination name representing the image
   * @throws IllegalArgumentException when...
   *                                  - the visualisation type is not one of the three options.
   *                                  - the image name is unrecognized/invalid.
   *                                  - the destination name has been used already.
   */
  void visualizeComponent(String visType, String imageName, String destName)
          throws IllegalArgumentException;

  /**
   * Flip an image horizontally to create a new image, referred to henceforth by the given
   * destination name.
   *
   * @param imageName the name of the image
   * @param destName  the new destination name representing the image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the destination name has been used already.
   */
  void horizontalFlip(String imageName, String destName) throws IllegalArgumentException;

  /**
   * Flip an image vertically to create a new image, referred to henceforth by the given destination
   * name.
   *
   * @param imageName the name of the image
   * @param destName  the new destination name representing the image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the destination name has been used already.
   */
  void verticalFlip(String imageName, String destName)
          throws IllegalArgumentException;

  /**
   * brighten the image by the given increment to create a new image, referred to henceforth by the
   * given destination name. The increment may be positive (brightening) or negative (darkening).
   *
   * @param increment how much to add to/subtract from the image's rgb values.
   * @param imageName the name of the image
   * @param destName  the new destination name representing the image
   * @throws IllegalArgumentException when...
   *                                  - the image name is unrecognized/invalid
   *                                  - the destination name has been used already.
   */
  void brighten(int increment, String imageName, String destName)
          throws IllegalArgumentException;
}

/*
#load koala.ppm and call it 'koala'
load images/koala.ppm koala

#brighten koala by adding 10
brighten 10 koala koala-brighter

#flip koala vertically
vertical-flip koala koala-vertical

#flip the vertically flipped koala horizontally
horizontal-flip koala-vertical koala-vertical-horizontal

#create a greyscale using only the value component, as an image koala-greyscale
value-component koala koala-greyscale

#save koala-brighter
save images/koala-brighter.ppm koala-brighter

#save koala-greyscale
save images/koala-gs.ppm koala-greyscale

#overwrite koala with another file
load images/upper.ppm koala
 */