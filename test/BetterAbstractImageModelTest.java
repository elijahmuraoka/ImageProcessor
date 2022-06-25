import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BetterIPModel;
import model.ImageFactory;

import static org.junit.Assert.assertEquals;

public class BetterAbstractImageModelTest {
  BetterIPModel m;

  @Before
  public void init() {
    ImageFactory factory = new ImageFactory("testFiles/PPM5.ppm.ppm");
    this.m = factory.createImageModel();
  }

  @Test
  public void generateFileName() {
  }

  @Test
  public void getCommandList() {
  }

  @Test
  public void addToCommandList() {
  }

  @Test
  public void make() {
  }

  @Test
  public void undo() {
  }

  @Test
  public void getImage() {
  }

  @Test
  public void setImage() {
  }

  @Test
  public void setCommandList() {
  }

  @Test
  public void generateHistograms() {
     /*
    P3
    # PPM5.ppm
    2 2
    254
    188 44 68 220 44 68
    203 44 231 220 149 254
    */
    // all expected histograms
    List<Map<Integer, Integer>> expected = new ArrayList<>();
    // make the expected red histogram
    Map<Integer, Integer> redHistogram = new HashMap<>();
    redHistogram.put(188, 1);
    redHistogram.put(220, 2);
    redHistogram.put(203, 1);
    expected.add(redHistogram);
    // make the expected green histogram
    Map<Integer, Integer> greenHistogram = new HashMap<>();
    greenHistogram.put(44, 3);
    greenHistogram.put(149, 1);
    expected.add(greenHistogram);
    // make the expected blue histogram
    Map<Integer, Integer> blueHistogram = new HashMap<>();
    blueHistogram.put(68, 2);
    blueHistogram.put(231, 1);
    blueHistogram.put(254, 1);
    expected.add(blueHistogram);

    Map<Integer, Integer> redExpected = expected.get(0);
    Map<Integer, Integer> greenExpected = expected.get(1);
    Map<Integer, Integer> blueExpected = expected.get(2);

    try {
      int redExpectedFail = redExpected.get(94);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the red map
    }
    int redExpected188 = redExpected.get(188);
    assertEquals(1, redExpected188);
    int redExpected220 = redExpected.get(220);
    assertEquals(2, redExpected220);
    int redExpected203 = redExpected.get(203);
    assertEquals(1, redExpected203);

    try {
      int greenExpectedFail = greenExpected.get(43);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the green map
    }
    int greenExpected44 = greenExpected.get(44);
    assertEquals(3, greenExpected44);
    int greenExpected149 = greenExpected.get(149);
    assertEquals(1, greenExpected149);

    try {
      int blueExpectedFail = redExpected.get(0);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the blue map
    }
    int blueExpected68 = blueExpected.get(68);
    assertEquals(2, blueExpected68);
    int blueExpected231 = blueExpected.get(231);
    assertEquals(1, blueExpected231);
    int blueExpected254 = blueExpected.get(254);
    assertEquals(1, blueExpected254);
  }
}