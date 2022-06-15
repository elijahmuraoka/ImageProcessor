package Utils;

/**
 * This Image Factory is used to create different types of Image Files
 * according to a specific file path.
 */
public class ImageFactory {
  private final String imagePath;

  /**
   * An ImageFactory constructor used to initialize the imagePath field when given one.
   *
   * @param imagePath the path of the file
   */
  public ImageFactory(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Creates the appropriate image file based on the image path and specifically, its extension.
   *
   * @return the ImageFile object matching the given image path
   * @throws IllegalStateException when there is no valid extension found
   */
  public ImageFile createImageFile() throws IllegalStateException {
    String reverseImgPath = new StringBuilder(this.imagePath).reverse().toString();
    String[] splitImgPath = reverseImgPath.split("\\.");
    String reverseExtension = splitImgPath[0];
    String extension = new StringBuilder(reverseExtension).reverse().toString();

    switch (extension.toLowerCase()) {
      case "ppm":
        return new PPMImage();
      case "jpeg":
      case "jpg":
        return new JPGImage();
      case "png":
        return new PNGImage();
      case "bmp":
        return new BMPImage();
      default:
        throw new IllegalStateException("No valid extension found. Please try again.\n");
    }
  }
}
