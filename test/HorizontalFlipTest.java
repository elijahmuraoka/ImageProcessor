import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.HorizontalFlip;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the HorizontalFlip command class.
 */
public class HorizontalFlipTest {
  HorizontalFlip hf;
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
      this.i.readPPM("testFiles/PPM1", this.v);
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
    this.m = new ImageModel();
    this.m.setImageName("TestHF");
    this.m.setWidth(this.i.getWidth());
    this.m.setHeight(this.i.getHeight());
    this.m.setWorkingImageData(this.i.getWorkingImageData());
    this.hf = new HorizontalFlip();
  }

  @Test
  public void execute() {
    // confirm initial values
    assertEquals("TestHF", this.m.getImageName());
    assertEquals(3, this.m.getWidth());
    assertEquals(3, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((
            new ArrayList<>(Arrays.asList(new int[]{86, 213, 93}, new int[]{116, 203, 163},
                    new int[]{171, 228, 66}))), (new ArrayList<>(Arrays.asList (
                            new int[]{0, 57, 166}, new int[]{71, 101, 127},
            new int[]{240, 15, 80}))), (new ArrayList<>(Arrays.asList(
                    new int[]{64, 241, 185}, new int[]{13, 241, 245},
            new int[]{97, 72, 170})))));
    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k], this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the horizontal flip command
    this.scan = new Scanner(new StringReader("FlippedH"));
    this.hf.execute(this.m, this.scan);
    // swap each pixel with their horizontal counterpart
    List<List<int[]>> expectedHF = new ArrayList<>(Arrays.asList ((
            new ArrayList<> (Arrays.asList(
                    new int[]{171, 228, 66},
                    new int[]{116, 203, 163},
                    new int[]{86, 213, 93}))), (new ArrayList<> (Arrays.asList (
                                    new int[]{240, 15, 80},
                            new int[]{71, 101, 127},
                            new int[]{0, 57, 166}))), (new ArrayList<>(Arrays.asList(
                                    new int[]{97, 72, 170},
                                    new int[]{13, 241, 245},
                                    new int[]{64, 241, 185})))));
    // compare each element of the horizontally flipped expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedHF.get(i).get(j)[k], this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }
}