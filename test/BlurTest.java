import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.Blur;
import model.BetterIPModel;
import model.ImageFactory;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Blur command class.
 */
public class BlurTest {
  Blur b;
  Appendable a;
  IPView v;
  ImageFactory factory;
  BetterIPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.factory = new ImageFactory("testFiles/PPM4.ppm");
    this.m = this.factory.createImageModel();
    try {
      this.m.read();
    } catch (IllegalStateException e) {
      fail("read threw an IllegalStateException when it was not supposed to.");
    }
    this.m.setImageName("TestBlur");
    this.b = new Blur();
  }

  @Test
  public void execute() {
    assertEquals("TestBlur", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.m.getWorkingImageData());

    this.scan = new Scanner(new StringReader("newBlur"));
    this.b.execute(this.m, this.scan);
    // blur the pixels by using the kernel algorithm appropriately
    List<List<int[]>> expectedBlur = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{108, 54, 70}, new int[]{108, 57, 71}))), (new ArrayList<>(
            Arrays.asList(new int[]{104, 75, 102}, new int[]{98, 73, 105})))));

    // compare each element of the blurred expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedBlur, this.m.getWorkingImageData());
  }

  @Test(expected = IllegalStateException.class)
  public void blurEmptyInput() {
    assertEquals("TestBlur", this.m.getImageName());
    // execute the Blur command with an empty destination name input
    this.scan = new Scanner(new StringReader(""));
    this.b.execute(this.m, this.scan);
  }
}
