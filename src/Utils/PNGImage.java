package Utils;

/**
 * A PNG image file representation.
 */
public class PNGImage extends AbstractImageFile {
  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".png";
  }
}
