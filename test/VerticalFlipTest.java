import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.VerticalFlip;
import model.IPModel;
import model.ImageModel;
import utils.ImageFactory;
import utils.ImageFile;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the VerticalFlip command class.
 */
public class VerticalFlipTest {
  VerticalFlip vf;
  Appendable a;
  IPView v;
  ImageFactory factory;
  ImageFile file;
  IPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.factory = new ImageFactory("testFiles/PPM1.ppm");
    this.file = this.factory.createImageFile();
    try {
      this.file.read(this.v);
    } catch (IOException e) {
      fail("read threw an IOException when it was not supposed to.");
    }
    this.m = new ImageModel();
    this.m.setImageName("TestVF");
    this.m.setWidth(this.file.getWidth());
    this.m.setHeight(this.file.getHeight());
    this.m.setWorkingImageData(this.file.getWorkingImageData());
    this.vf = new VerticalFlip();
  }

  @Test
  public void execute() {
    // confirm initial values
    assertEquals("TestVF", this.m.getImageName());
    assertEquals(3, this.m.getWidth());
    assertEquals(3, this.m.getHeight());

    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{86, 213, 93}, new int[]{116, 203, 163},
                    new int[]{171, 228, 66}))), (new ArrayList<>(Arrays.asList(
            new int[]{0, 57, 166}, new int[]{71, 101, 127},
            new int[]{240, 15, 80}))), (new ArrayList<>(Arrays.asList(
            new int[]{64, 241, 185}, new int[]{13, 241, 245}, new int[]{97, 72, 170})))));
    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.file.getWorkingImageData());

    // execute the vertical flip command
    this.scan = new Scanner(new StringReader("FlippedV"));
    this.vf.execute(this.m, this.scan);
    // swap each pixel with their vertical counterpart
    List<List<int[]>> expectedVF = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{64, 241, 185}, new int[]{13, 241, 245}, new int[]{97, 72, 170}))), (
            new ArrayList<>(Arrays.asList(new int[]{0, 57, 166}, new int[]{71, 101, 127},
                    new int[]{240, 15, 80}))), (new ArrayList<>(
            Arrays.asList(new int[]{86, 213, 93},
                    new int[]{116, 203, 163}, new int[]{171, 228, 66})))));

    // compare each element of the vertically flipped expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedVF, this.m.getWorkingImageData());

    List<List<int[]>> actualData = this.m.getWorkingImageData();

    // for each row
    for (int i = 0; i < actualData.size(); i++) {
      // for each pixel in every column
      for (int j = 0; j < actualData.get(i).size(); j++) {
        // does the actual pixel equal the text pixel and at the same coordinate
        int[] actualPix = actualData.get(i).get(j);
        assertArrayEquals(actualPix, expectedVF.get(i).get(j));
      }
    }
  }
}