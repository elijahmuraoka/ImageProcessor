package Utils;

/**
 * A JPG image file representation.
 */
public class JPGImage extends AbstractImageFile {

  @Override
  public String generateFileName(String saveAsName, String imagePath) {
    return imagePath + "/" + saveAsName + ".jpg";
  }
}
