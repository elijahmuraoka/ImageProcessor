import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.ChangeBrightness;
import model.IPModel;
import model.ImageFactory;
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
  ImageFactory factory;
  IPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.factory = new ImageFactory("testFiles/PPM2.ppm");
    this.m = this.factory.createImageModel();
    try {
      this.m.read();
    } catch (IllegalStateException e) {
      fail("read threw an IllegalStateException when it was not supposed to.");
    }
    this.m.setImageName("TestCB");
    this.cb = new ChangeBrightness();
  }

  @Test
  public void brighten() {
    assertEquals("TestCB", this.m.getImageName());
    assertEquals(1, this.m.getWidth());
    assertEquals(4, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{23, 22, 145}))), (new ArrayList<>(Arrays.asList(
            new int[]{249, 99, 243}))), (new ArrayList<>(Arrays.asList(
            new int[]{172, 181, 50}))), (new ArrayList<>(Arrays.asList(
            new int[]{163, 29, 242})))));

    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.m.getWorkingImageData());

    this.scan = new Scanner(new StringReader("50 brightPPM2"));
    this.cb.execute(this.m, this.scan);
    // brighten the pixels by increasing each component appropriately
    List<List<int[]>> expectedBright = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{150, 149, 255}))), (new ArrayList<>(Arrays.asList(
            new int[]{255, 226, 255}))), (new ArrayList<>(Arrays.asList(
            new int[]{255, 255, 177}))), (new ArrayList<>(Arrays.asList(
            new int[]{255, 156, 255})))));

    // compare each element of the bright expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedBright, this.m.getWorkingImageData());
  }

  @Test
  public void darken() {
    assertEquals("TestCB", this.m.getImageName());
    assertEquals(1, this.m.getWidth());
    assertEquals(4, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{23, 22, 145}))), (new ArrayList<>(Arrays.asList(
            new int[]{249, 99, 243}))), (new ArrayList<>(Arrays.asList(new int[]{172, 181, 50}))), (
            new ArrayList<>(Arrays.asList(new int[]{163, 29, 242})))));

    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.m.getWorkingImageData());

    this.scan = new Scanner(new StringReader("-50 brightPPM2"));
    this.cb.execute(this.m, this.scan);
    // brighten the pixels by increasing each component appropriately
    List<List<int[]>> expectedDark = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{0, 0, 17}))), (new ArrayList<>(Arrays.asList(
            new int[]{121, 0, 115}))), (new ArrayList<>(Arrays.asList(new int[]{44, 53, 0}))), (
            new ArrayList<>(Arrays.asList(new int[]{35, 0, 114})))));

    // compare each element of the bright expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedDark, this.m.getWorkingImageData());
  }

  @Test(expected = IllegalStateException.class)
  public void cbInvalidInput() {
    assertEquals("TestCB", this.m.getImageName());
    // execute the ChangeBrightness command with an invalid increment
    this.scan = new Scanner(new StringReader("dog newDog"));
    this.cb.execute(this.m, this.scan);
  }

  @Test(expected = IllegalStateException.class)
  public void cbEmptyIncrementInput() {
    assertEquals("TestCB", this.m.getImageName());
    // execute the ChangeBrightness command with an empty increment
    this.scan = new Scanner(new StringReader("newDog"));
    this.cb.execute(this.m, this.scan);
  }

  @Test(expected = IllegalStateException.class)
  public void cbEmptyDestNameInput() {
    assertEquals("TestCB", this.m.getImageName());
    // execute the ChangeBrightness command with an empty destination name
    this.scan = new Scanner(new StringReader("59"));
    this.cb.execute(this.m, this.scan);
  }
}