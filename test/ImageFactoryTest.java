import org.junit.Test;

import model.IPModel;
import model.ImageFactory;
import model.JPGImage;

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
    IPModel expectedJPG = new JPGImage("dog.jpg");
    assertEquals(expectedJPG.getWidth(), factory.createImageModel().getWidth());
    assertEquals(expectedJPG.getHeight(), factory.createImageModel().getHeight());
    assertEquals(expectedJPG.getHeight(), factory.createImageModel().getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void createInvalidImageFile() {
    ImageFactory badFactory = new ImageFactory("dogjpg");
    IPModel expectedJPG = new JPGImage("dog.jpg");
    assertNotEquals(expectedJPG.getWidth(), badFactory.createImageModel().getWidth());
  }
}