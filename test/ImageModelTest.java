import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.ImageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for an Image model.
 */
public class ImageModelTest {
  ImageModel m;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    int[] pixelR = {255, 0, 0};
    int[] pixelG = {0, 255, 0};
    int[] pixelB = {0, 0, 255};

    List<int[]> row1 = new ArrayList<int[]>(Arrays.asList(pixelR, pixelG, pixelB));
    List<List<int[]>> workingImageData = new ArrayList<>();

    workingImageData.add(row1);

    this.m = new ImageModel("TestModel", 3, 4, workingImageData);
  }

  // tests any exceptions needing to be thrown when constructing an invalid co
  @Test
  public void invalidInit() {
    // tests for an invalid negative height passed to an Image Model
    try {
      List<List<int[]>> test = new ArrayList<>();
      this.m = new ImageModel("Bob's Selfie", -32, 50, test);
      fail("Did not throw an IllegalArgumentException when given a negative height.");
    } catch (IllegalArgumentException e) {
      // successfully caught an IAE when given a negative height
    }
    // tests for an invalid negative width passed to an Image Model
    try {
      List<List<int[]>> test = new ArrayList<>();
      this.m = new ImageModel("Bob's Selfie", 502, -4320, test);
      fail("Did not throw an IllegalArgumentException when given a negative height.");
    } catch (IllegalArgumentException e) {
      // successfully caught an IAE when given a negative width
    }
  }

  @Test
  public void setImageName() {
    assertNotEquals("Timothy", this.m.getImageName());
    this.m.setImageName("Timothy");
    assertEquals("Timothy", this.m.getImageName());
  }

  @Test
  public void setWidth() {
    assertNotEquals(5, this.m.getWidth());
    this.m.setWidth(5);
    assertEquals(5, this.m.getWidth());
  }

  @Test
  public void setHeight() {
    assertNotEquals(17, this.m.getHeight());
    this.m.setHeight(17);
    assertEquals(17, this.m.getHeight());
  }

  @Test
  public void setWorkingImageData() {
    List<List<int[]>> expected = new ArrayList<>(new ArrayList<>
            (Arrays.asList(new ArrayList<>
                    (Arrays.asList(new int[]{1, 50, 47}, new int[]{84, 130, 68})))));
    assertNotEquals(expected, this.m.getWorkingImageData());
    this.m.setWorkingImageData(expected);
    assertEquals(expected, this.m.getWorkingImageData());
  }

  @Test
  public void getImageName() {
    assertEquals("TestModel", this.m.getImageName());
    assertNotEquals("TestModelFail", this.m.getImageName());
  }

  @Test
  public void getWidth() {
    assertEquals(4, this.m.getWidth());
    assertNotEquals(32, this.m.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(3, this.m.getHeight());
    assertNotEquals(1040, this.m.getHeight());
  }

  @Test
  public void getWorkingImageData() {
    int[] pixelR = {255, 0, 0};
    int[] pixelG = {0, 255, 0};
    int[] pixelB = {0, 0, 255};

    List<int[]> row1 = new ArrayList<int[]>(Arrays.asList(pixelR, pixelG, pixelB));
    List<List<int[]>> expected = new ArrayList<>();

    expected.add(row1);

    ArrayList<String> allPixExpected = new ArrayList<>();
    ArrayList<String> allPix = new ArrayList<>();

    for (List<int[]> row : expected) {
      for (int[] pixel : row) {
        allPixExpected.add(Arrays.toString(pixel));
      }
    }

    for (List<int[]> row : this.m.getWorkingImageData()) {
      for (int[] pixel : row) {
        allPix.add(Arrays.toString(pixel));
      }
    }
    assertEquals(allPixExpected, allPix);

    List<List<int[]>> fail = new ArrayList<>(new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(new int[]{1, 50, 47}, new int[]{84, 130, 68})))));

    assertNotEquals(fail, this.m.getWorkingImageData());
  }
}