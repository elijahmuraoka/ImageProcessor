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
import commands.ChangeBrightness;
import model.IPModel;
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
  ImageFactory factory;
  ImageFile file;
  IPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.factory = new ImageFactory("testFiles/PPM2.ppm");
    this.file = this.factory.createImageFile();
    try {
      this.file.read(this.v);
    } catch (IOException e) {
      fail("read threw an IOException when it was not supposed to.");
    }
    this.m = new ImageModel();
    this.m.setImageName("TestCB");
    this.m.setWidth(this.file.getWidth());
    this.m.setHeight(this.file.getHeight());
    this.m.setWorkingImageData(this.file.getWorkingImageData());
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
    TestUtils.imageDataEquals(m, expectedImageData, this.file.getWorkingImageData());

    this.scan = new Scanner(new StringReader("50 brightPPM2"));
    this.cb.execute(this.m, this.scan);
    // brighten the pixels by increasing each component appropriately
    List<List<int[]>> expectedBright = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{73, 72, 195}))), (new ArrayList<>(Arrays.asList(new int[]{255, 149, 255}))), (
            new ArrayList<>(Arrays.asList(new int[]{222, 231, 100}))), (new ArrayList<>(
            Arrays.asList(new int[]{213, 79, 255})))));

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
    TestUtils.imageDataEquals(m, expectedImageData, this.file.getWorkingImageData());

    this.scan = new Scanner(new StringReader("-50 brightPPM2"));
    this.cb.execute(this.m, this.scan);
    // brighten the pixels by increasing each component appropriately
    List<List<int[]>> expectedDark = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{0, 0, 95}))), (new ArrayList<>(Arrays.asList(new int[]{199, 49, 193}))), (
            new ArrayList<>(Arrays.asList(new int[]{122, 131, 0}))), (new ArrayList<>(
            Arrays.asList(new int[]{113, 0, 192})))));

    // compare each element of the bright expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedDark, this.m.getWorkingImageData());
  }
}