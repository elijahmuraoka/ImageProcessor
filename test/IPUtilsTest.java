import org.junit.Before;
import org.junit.Test;

import model.IPModel;
import model.PPMImage;
import utils.IPUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionality of all methods in the IPUtils class.
 */
public class IPUtilsTest {
  IPModel model;
  IPUtils utils;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.model = new PPMImage("PPMImage");
    this.utils = new IPUtils();
    this.model.setWidth(5);
    this.model.setHeight(7);
    this.model.setMaxComponent(244);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInit() {
    // Shouldn't have negative width
    this.model.setWidth(-50);
    this.model.setHeight(-70);
    this.model.setMaxComponent(-500);

    assertNotEquals(-50, this.model.getWidth());
    assertNotEquals(-70, this.model.getHeight());
    assertNotEquals(-500, this.model.getMaxComponent());
  }

  @Test
  public void isWithinDimensions() {
    // tests if the given row is within the model's height
    assertTrue(this.utils.isWithinDimensions(this.model, 0, 0));
    assertTrue(this.utils.isWithinDimensions(this.model, 6, 0));
    assertFalse(this.utils.isWithinDimensions(this.model, 7, 0));
    assertFalse(this.utils.isWithinDimensions(this.model, 8, 0));

    // tests if the given column is within the model's width
    assertTrue(this.utils.isWithinDimensions(this.model, 0, 4));
    assertFalse(this.utils.isWithinDimensions(this.model, 0, 5));
    assertFalse(this.utils.isWithinDimensions(this.model, 0, 6));
  }

  @Test
  public void capComponent() {
    // caps the component at 255 since it goes over
    assertEquals(255, this.utils.capComponent(5320));
    // leaves the component untouched because it is between 0 and 255
    assertEquals(200, this.utils.capComponent(200));
    // caps the component at 0 since it goes under (components cannot be negative)
    assertEquals(0, this.utils.capComponent(-85));
  }
}