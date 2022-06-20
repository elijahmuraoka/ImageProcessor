import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.GreyScale;
import model.IPModel;
import model.ImageFactory;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the GreyScale command class.
 */
public class GreyScaleTest {
  GreyScale gs;
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
    this.factory = new ImageFactory("testFiles/PPM4.ppm");
    this.m = this.factory.createImageModel();
    try {
      this.m.read();
    } catch (IllegalStateException e) {
      fail("read threw an IllegalStateException when it was not supposed to.");
    }
    this.gs = new GreyScale();
    this.m.setImageName("TestGS");

    // confirm initial values
    assertEquals("TestGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.m.getWorkingImageData());
  }

  @Test
  public void gsRed() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the red greyscale command
    this.scan = new Scanner(new StringReader("red gs-red"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGsRed = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{188, 188, 188}, new int[]{220, 220, 220}))), (new ArrayList<>(Arrays.asList(
            new int[]{203, 203, 203}, new int[]{135, 135, 135})))));

    // compare each element of the red greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGsRed, this.m.getWorkingImageData());
  }

  @Test
  public void gsGreen() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the green greyscale command
    this.scan = new Scanner(new StringReader("green gs-green"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGsGreen = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{44, 44, 44}, new int[]{86, 86, 86}))), (new ArrayList<>(
            Arrays.asList(new int[]{185, 185, 185}, new int[]{149, 149, 149})))));

    // compare each element of the green greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGsGreen, this.m.getWorkingImageData());
  }

  @Test
  public void gsBlue() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the blue greyscale command
    this.scan = new Scanner(new StringReader("blue gs-blue"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGSBlue = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{68, 68, 68}, new int[]{68, 68, 68}))), (new ArrayList<>(Arrays.asList(
            new int[]{231, 231, 231}, new int[]{254, 254, 254})))));

    // compare each element of the blue greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGSBlue, this.m.getWorkingImageData());
  }

  @Test
  public void gsLuma() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the luma greyscale command
    this.scan = new Scanner(new StringReader("luma gs-luma"));
    this.gs.execute(this.m, this.scan);
    // "lumify" each pixel component
    List<List<int[]>> expectedGsLuma = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
            new int[]{76, 76, 76}, new int[]{113, 113, 113}))), (new ArrayList<>(Arrays.asList(
            new int[]{192, 192, 192}, new int[]{153, 153, 153})))));
    // compare each element of the luma greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGsLuma, this.m.getWorkingImageData());
  }

  @Test
  public void gsIntensity() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the intensity greyscale command
    this.scan = new Scanner(new StringReader("intensity gs-intensity"));
    this.gs.execute(this.m, this.scan);
    // "intensify" each pixel component
    List<List<int[]>> expectedGsIntensity = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{100, 100, 100}, new int[]{124, 124, 124}))), (new ArrayList<>(
            Arrays.asList(new int[]{206, 206, 206}, new int[]{179, 179, 179})))));

    // compare each element of the intensity greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGsIntensity, this.m.getWorkingImageData());
  }

  @Test
  public void gsValue() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the value greyscale command
    this.scan = new Scanner(new StringReader("value gs-value"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component to its maximum component value
    List<List<int[]>> expectedGsValue = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{188, 188, 188}, new int[]{220, 220, 220}))), (new ArrayList<>(
            Arrays.asList(new int[]{231, 231, 231}, new int[]{254, 254, 254})))));

    // compare each element of the value greyscale expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedGsValue, this.m.getWorkingImageData());
  }

  @Test(expected = IllegalStateException.class)
  public void gsInvalidInput() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the greyscale command with an invalid visType
    this.scan = new Scanner(new StringReader("pokemon gs-pokemon"));
    this.gs.execute(this.m, this.scan);
  }

  @Test(expected = IllegalStateException.class)
  public void gsEmptyInput() {
    assertEquals("TestGS", this.m.getImageName());
    // execute the greyscale command with an empty visType/destName
    this.scan = new Scanner(new StringReader("gs-pokemon"));
    this.gs.execute(this.m, this.scan);
  }
}