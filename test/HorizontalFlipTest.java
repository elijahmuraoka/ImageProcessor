import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Utils.ImageFactory;
import Utils.ImageFile;
import commands.HorizontalFlip;
import model.IPModel;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the HorizontalFlip command class.
 */
public class HorizontalFlipTest {
  HorizontalFlip hf;
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
    this.m.setImageName("TestHF");
    this.m.setWidth(this.file.getWidth());
    this.m.setHeight(this.file.getHeight());
    this.m.setWorkingImageData(this.file.getWorkingImageData());
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
                    new int[]{171, 228, 66}))), (new ArrayList<>(Arrays.asList(
            new int[]{0, 57, 166}, new int[]{71, 101, 127},
            new int[]{240, 15, 80}))), (new ArrayList<>(Arrays.asList(
            new int[]{64, 241, 185}, new int[]{13, 241, 245},
            new int[]{97, 72, 170})))));
    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.file.getWorkingImageData());

    // execute the horizontal flip command
    this.scan = new Scanner(new StringReader("FlippedH"));
    this.hf.execute(this.m, this.scan);
    // swap each pixel with their horizontal counterpart
    List<List<int[]>> expectedHF = new ArrayList<>(Arrays.asList((
            new ArrayList<>(Arrays.asList(
                    new int[]{171, 228, 66},
                    new int[]{116, 203, 163},
                    new int[]{86, 213, 93}))), (new ArrayList<>(Arrays.asList(
            new int[]{240, 15, 80},
            new int[]{71, 101, 127},
            new int[]{0, 57, 166}))), (new ArrayList<>(Arrays.asList(
            new int[]{97, 72, 170},
            new int[]{13, 241, 245},
            new int[]{64, 241, 185})))));
    // compare each element of the horizontally flipped expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedHF, this.m.getWorkingImageData());
  }
}