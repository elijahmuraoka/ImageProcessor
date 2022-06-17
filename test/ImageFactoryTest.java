import org.junit.Test;

import utils.ImageFactory;
import utils.ImageFile;
import utils.JPGImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class tests the Image Factory class and its creation of different image files.
 */
public class ImageFactoryTest {
  ImageFactory factory;

  @Test
  public void createImageFile() {
    this.factory = new ImageFactory("dog.jpg");
    ImageFile expectedJPG = new JPGImage("dog.jpg");
    assertEquals(expectedJPG.getWidth(), factory.createImageFile().getWidth());
    assertEquals(expectedJPG.getHeight(), factory.createImageFile().getHeight());
    assertEquals(expectedJPG.getHeight(), factory.createImageFile().getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void createInvalidImageFile() {
    ImageFactory badFactory = new ImageFactory("dogjpg");
    ImageFile expectedJPG = new JPGImage("dog.jpg");
    assertNotEquals(expectedJPG.getWidth(), badFactory.createImageFile().getWidth());
  }
}