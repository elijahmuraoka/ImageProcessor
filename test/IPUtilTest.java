import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IPUtil;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor utility class.
 */
public class IPUtilTest {
  IPUtil i;
  Appendable a;
  IPView v;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.i = new IPUtil();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
  }


  @Test
  public void readPPM() {
    try {
      assertEquals("", this.a.toString());
      this.i.readPPM("testFiles/PPM1", this.v);
      assertEquals("Width of image: 3\n"
              + "Height of image: 3\n"
              + "Maximum value of a color in this file (usually 255): 245\n"
              + "Successfully loaded image: testFiles/PPM1\n", this.a.toString());
      assertEquals(3, this.i.getHeight());
      assertEquals(3, this.i.getWidth());

      List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
          Arrays.asList(new int[]{86, 213, 93}, new int[]{116, 203, 163},
              new int[]{171, 228, 66}))), (new ArrayList<>(Arrays.asList(new int[]{0, 57, 166},
                  new int[]{71, 101, 127}, new int[]{240, 15, 80}))), (new ArrayList<>(
                      Arrays.asList(new int[]{64, 241, 185}, new int[]{13, 241, 245},
                          new int[]{97, 72, 170})))));
      // compare each element of the two array lists
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          for (int k = 0; k < 3; k++) {
            assertEquals(expectedImageData.get(i).get(j)[k],
                this.i.getWorkingImageData().get(i).get(j)[k]);
          }
        }
      }
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
  }

  @Test
  public void getWidth() {
    try {
      assertNotEquals(1, this.i.getWidth());
      this.i.readPPM("testFiles/PPM2", this.v);
      assertEquals(1, this.i.getWidth());
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
  }

  @Test
  public void getHeight() {
    try {
      assertNotEquals(this.i.getHeight(), 4);
      this.i.readPPM("testFiles/PPM2", this.v);
      assertEquals(this.i.getHeight(), 4);
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
  }

  @Test
  public void getWorkingImageData() {
    try {
      List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
          Arrays.asList(new int[]{23, 22, 145}))), (new ArrayList<>(Arrays.asList(
              new int[]{249, 99, 243}))), (new ArrayList<>(Arrays.asList(
                  new int[]{172, 181, 50}))), (
                      new ArrayList<>(Arrays.asList(new int[]{163, 29, 242})))));
      assertNull(this.i.getWorkingImageData());
      assertNotEquals(expectedImageData, this.i.getWorkingImageData());
      this.i.readPPM("testFiles/PPM2", this.v);
      // compare each element of the two array lists
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 1; j++) {
          for (int k = 0; k < 3; k++) {
            assertEquals(expectedImageData.get(i).get(j)[k],
                this.i.getWorkingImageData().get(i).get(j)[k]);
          }
        }
      }
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
  }
}