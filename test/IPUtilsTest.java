import org.junit.Before;
import org.junit.Test;

import model.IPModel;
import model.ImageModel;
import utils.IPUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IPUtilsTest {
  IPModel model;
  IPUtils utils;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.model = new ImageModel();
    this.utils = new IPUtils();
    this.model.setWidth(5);
    this.model.setHeight(7);
    this.model.setMaxComponent(500);
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
    // caps the component at 500 since it goes over
    assertEquals(500, this.utils.capComponent(this.model, 5320));
    // leaves the component untouched because it is between 0 and 500
    assertEquals(200, this.utils.capComponent(this.model, 200));
    // caps the component at 0 since it goes under (components cannot be negative)
    assertEquals(0, this.utils.capComponent(this.model, -85));
  }
}