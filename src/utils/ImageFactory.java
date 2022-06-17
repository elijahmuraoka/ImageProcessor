package utils;

/**
 * This Image Factory is used to create different types of Image Files
 * according to a specific file path.
 */
public class ImageFactory {
  private final String fileName;

  /**
   * An ImageFactory constructor used to initialize the imagePath field when given one.
   *
   * @param fileName the path of the file
   */
  public ImageFactory(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Creates the appropriate image file based on the image path and specifically, its extension.
   *
   * @return the ImageFile object matching the given image path
   * @throws IllegalStateException when there is no valid extension found
   */
  public ImageFile createImageFile() throws IllegalStateException {
    String reverseImgPath = new StringBuilder(this.fileName).reverse().toString();
    String[] splitImgPath = reverseImgPath.split("\\.");
    String reverseExtension = splitImgPath[0];
    String extension = new StringBuilder(reverseExtension).reverse().toString();

    switch (extension.toLowerCase()) {
      case "ppm":
        return new PPMImage(this.fileName);
      case "jpeg":
      case "jpg":
        return new JPGImage(this.fileName);
      case "png":
        return new PNGImage(this.fileName);
      case "bmp":
        return new BMPImage(this.fileName);
      default:
        throw new IllegalStateException("No valid extension found. Please try again.\n");
    }
  }
}
