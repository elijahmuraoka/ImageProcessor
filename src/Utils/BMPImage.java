package Utils;

/**
 * A BMP image file representation.
 */
public class BMPImage extends AbstractImageFile {
  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".bmp";
  }
}
