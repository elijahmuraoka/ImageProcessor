package controller;

public interface ImageController {

  /**
   * Load an image from the specified path and refer it to henceforth in the program by the given
   * image name.
   *
   * @param imagePath image path.
   * @param imageName image name.
   */
  void load(String imagePath, String imageName);

  /**
   * Save the image with the given name to the specified path which should include the name of the
   * file.
   *
   * @param imagePath image path.
   * @param imageName image name.
   */
  void save(String imagePath, String imageName);

  /**
   * Create a greyscale image with the red-component of the image with the given name, and refer to
   * it henceforth in the program by the given destination name.
   * Similar commands for green, blue, value, luma, intensity components should be supported.
   *
   * So basically grey out everything and keep the reds.
   *
   * @param imageName image name.
   * @param destName image path.
   */
  void redComponent(String imageName, String destName);

  /**
   * Flip an image horizontally to create a new image, referred to henceforth by the given
   * destination name.
   *
   * @param imageName image name.
   * @param imagePath image path.
   */
  void horizontalFlip(String imageName, String imagePath);

  /**
   * Flip an image vertically to create a new image, referred to henceforth by the given destination
   * name.
   *
   * @param imageName image name.
   * @param imagePath image path.
   */
  void verticalFlip(String imageName, String imagePath);

  /**
   * brighten the image by the given increment to create a new image, referred to henceforth by the
   * given destination name. The increment may be positive (brightening) or negative (darkening).
   *
   * @param increment how much to add to/subtract from the image's rgb values.
   * @param imageName image name.
   * @param imagePath image path.
   */
  void brighten(int increment, String imageName, String imagePath);
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