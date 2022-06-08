import org.junit.Before;
import org.junit.Test;

import model.IPModel;
import model.ImageModel;

import static org.junit.Assert.assertEquals;

/**
 * This class represents all tests needed for the PPM model.
 */
public class ImageModelTest {
  String koalaName;
  String koalaPath;
  IPModel m;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.koalaName = "Koala";
    this.koalaPath = "images";
    this.m = new ImageModel();
  }

  // tests the generateFileName method
  @Test
  public void generateFileName() {
    String expectedKoalaFileName = "images/Koala.ppm";
    assertEquals(expectedKoalaFileName,
            this.m.generateFileName(this.koalaName, this.koalaPath));
  }

  // tests the load method
  @Test
  public void load() {
  }

  // tests the save method
  @Test
  public void save() {
  }

  // tests the visualizeComponent method which visualizes
  // individual RGB components
  @Test
  public void testVisualizeComponentRGB() {
  }

  // tests the visualizeComponent method which visualizes
  // value, intensity, or luma
  @Test
  public void testVisualizeComponent() {
  }

  // tests the horizontalFlip method
  @Test
  public void horizontalFlip() {
  }

  // tests the verticalFlip method
  @Test
  public void verticalFlip() {
  }

  // tests the changeBrightness method
  @Test
  public void brighten() {
  }
}