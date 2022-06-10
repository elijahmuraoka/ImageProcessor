import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.ChangeBrightness;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the ChangeBrightness command class.
 */
public class ChangeBrightnessTest {
  ChangeBrightness cb;
  Appendable a;
  IPView v;
  IPUtil i;
  IPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.i = new IPUtil();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    try {
      this.i.readPPM("testFiles/PPM2", this.v);
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
    this.m = new ImageModel();
    this.m.setImageName("TestCB");
    this.m.setWidth(this.i.getWidth());
    this.m.setHeight(this.i.getHeight());
    this.m.setWorkingImageData(this.i.getWorkingImageData());
    this.cb = new ChangeBrightness();
  }

  @Test
  public void brighten() {
    assertEquals("TestCB", this.m.getImageName());
    assertEquals(1, this.m.getWidth());
    assertEquals(4, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>
            (Arrays.asList
                    ((new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{23, 22, 145}
                                            ))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{249, 99, 243}))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{172, 181, 50}))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{163, 29, 242})))));
    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k], this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    this.scan = new Scanner(new StringReader("25 brightPPM2"));
    this.cb.execute(this.m, this.scan);
    // brighten the pixels by increasing each component appropriately
    List<List<int[]>> expectedBright = new ArrayList<>
            (Arrays.asList
                    ((new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{23, 22, 145}
                                            ))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{249, 99, 243}))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{172, 181, 50}))),
                            (new ArrayList<>
                                    (Arrays.asList
                                            (new int[]{163, 29, 242})))));
    // compare each element of the bright expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k], this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }
}