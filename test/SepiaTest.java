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
import commands.Sepia;
import model.IPModel;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.*;

/**
 * This class represents all tests needed for the Sepia command class.
 */
public class SepiaTest {
  Sepia s;
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
    this.factory = new ImageFactory("testFiles/PPM4.ppm");
    this.file = this.factory.createImageFile();

    try {
      this.file.read(this.v);
    } catch (IOException e) {
      fail("read threw an IOException when it was not supposed to.");
    }

    this.m = new ImageModel();
    this.m.setImageName("TestSepia");
    this.m.setWidth(this.file.getWidth());
    this.m.setHeight(this.file.getHeight());
    this.m.setWorkingImageData(this.file.getWorkingImageData());
    this.s = new Sepia();
    this.m.setMaxComponent(this.file.getMaxComponent());
  }

  @Test
  public void execute() {
    assertEquals("TestSepia", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    TestUtils.imageDataEquals(m, expectedImageData, this.file.getWorkingImageData());

    this.scan = new Scanner(new StringReader("newSepia"));
    this.s.execute(this.m, this.scan);
    // sepify the pixels by using the kernel algorithm appropriately
    List<List<int[]>> expectedSepia = new ArrayList<>(Arrays.asList((new ArrayList<>(
            Arrays.asList(new int[]{120, 107, 83}, new int[]{165, 147, 114}))), (new ArrayList<>(
            Arrays.asList(new int[]{254, 236, 184}, new int[]{215, 192, 149})))));

    // compare each element of the sepified expected array and the
    // newly modified image data array
    TestUtils.imageDataEquals(m, expectedSepia, this.m.getWorkingImageData());
  }
}