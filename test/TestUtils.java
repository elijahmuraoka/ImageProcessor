import java.util.List;

import model.IPModel;

import static org.junit.Assert.assertEquals;

/**
 * This class provides utility methods used to reduce duplicate code and simplify tests.
 */
public class TestUtils {

  /**
   * Utility helper test method used to compare two 2-D array pixel array lists, representing
   * the image data of two different objects.
   *
   * @param m        the given Image Processor Model
   * @param expected the expected image data
   * @param actual   the actual image data
   */
  public static void imageDataEquals(IPModel m, List<List<int[]>> expected,
                                     List<List<int[]>> actual) {
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expected.get(i).get(j)[k], actual.get(i).get(j)[k]);
        }
      }
    }
  }
}
