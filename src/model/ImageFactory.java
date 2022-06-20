package model;

/**
 * This Image Factory is used to dynamically create unique IPModel objects
 * according to a specific file path and its extension type (e.g. JPG, PNG, BMP, PPM, etc.).
 */
public class ImageFactory {
  private final String fileName;

  /**
   * An ImageFactory constructor used to initialize the fileName field when given one.
   *
   * @param fileName the name of the file, which must specifically include the file extension type
   */
  public ImageFactory(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Creates the appropriate image model object based on the file name, but more specifically,
   * its extension.
   *
   * @return the ImageModel object matching the given image path
   * @throws IllegalStateException when there is no valid extension found
   */
  public IPModel createImageModel() throws IllegalStateException {
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
