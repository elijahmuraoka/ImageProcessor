import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.ImageFile;
import utils.JPGImage;
import utils.PNGImage;
import utils.PPMImage;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class tests all methods using various types of image files.
 */
public class ImageFilesTest {
  ImageFile file;
  IPView view;
  Appendable a;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.view = new IPViewImpl(this.a);
  }

  @Test
  public void read() throws IOException {
    this.file = new PPMImage("testFiles/PPM1.ppm");
    this.file.read(this.view);
    int actualWidth = this.file.getWidth();
    int actualHeight = this.file.getHeight();
    int actualComp = this.file.getMaxComponent();
    List<List<int[]>> actualData = this.file.getWorkingImageData();

    List<List<int[]>> testImageData = new ArrayList<>();

    List<int[]> row0 = new ArrayList<int[]>();
    row0.add(new int[]{86, 213, 93});
    row0.add(new int[]{116, 203, 163});
    row0.add(new int[]{171, 228, 66});

    List<int[]> row1 = new ArrayList<int[]>();
    row1.add(new int[]{0, 57, 166});
    row1.add(new int[]{71, 101, 127});
    row1.add(new int[]{240, 15, 80});

    List<int[]> row2 = new ArrayList<int[]>();
    row2.add(new int[]{64, 241, 185});
    row2.add(new int[]{13, 241, 245});
    row2.add(new int[]{97, 72, 170});

    testImageData.add(row0);
    testImageData.add(row1);
    testImageData.add(row2);

    assertEquals(3, actualWidth);
    assertEquals(3, actualHeight);
    assertEquals(245, actualComp);

    for (int i = 0; i < actualData.size(); i++) {
      for (int j = 0; j < actualData.get(i).size(); j++) {
        int[] actualPix = actualData.get(i).get(j);
        assertArrayEquals(actualPix, testImageData.get(i).get(j));
      }
    }
    assertEquals("Width of image: 3\n"
            + "Height of image: 3\n"
            + "Maximum value of a color in this file (usually 255): 245\n"
            + "Successfully loaded image: testFiles/PPM1.ppm", this.a.toString());

    try {
      this.file = new JPGImage("damian.jpg");
      this.file.read(this.view);
      assertTrue(this.a.toString().contains("File damian.jpg not found!\n"));
    } catch (IOException e) {
      fail("Did not catch the invalid input exception when passing in a non-existent file.");
    }
  }

  @Test
  public void generateFileName() {
    this.file = new PNGImage("");
    assertEquals("res/newImage.png",
            this.file.generateFileName("newImage", "res"));
  }

  @Test
  public void getWidth() {
    try {
      this.file = new JPGImage("images/bucketboi.jpg");
      this.file.read(this.view);
      assertEquals(750, this.file.getWidth());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      this.file = new PPMImage("testFiles/PPM3.ppm");
      this.file.read(this.view);
      assertEquals(2, this.file.getWidth());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getHeight() {
    try {
      this.file = new JPGImage("images/bucketboi.jpg");
      this.file.read(this.view);
      assertEquals(1000, this.file.getHeight());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      this.file = new PPMImage("testFiles/PPM3.ppm");
      this.file.read(this.view);
      assertEquals(6, this.file.getHeight());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getWorkingImageData() {
    this.file = new PPMImage("testFiles/PPM1.ppm");
    List<List<int[]>> testImageData = new ArrayList<>();
    List<int[]> row0 = new ArrayList<int[]>();
    row0.add(new int[]{86, 213, 93});
    row0.add(new int[]{116, 203, 163});
    row0.add(new int[]{171, 228, 66});
    List<int[]> row1 = new ArrayList<int[]>();
    row1.add(new int[]{0, 57, 166});
    row1.add(new int[]{71, 101, 127});
    row1.add(new int[]{240, 15, 80});

    List<int[]> row2 = new ArrayList<int[]>();
    row2.add(new int[]{64, 241, 185});
    row2.add(new int[]{13, 241, 245});
    row2.add(new int[]{97, 72, 170});

    testImageData.add(row0);
    testImageData.add(row1);
    testImageData.add(row2);

    try {
      this.file.read(view);
      List<List<int[]>> actualData = this.file.getWorkingImageData();
      for (int i = 0; i < actualData.size(); i++) {
        for (int j = 0; j < actualData.get(i).size(); j++) {
          int[] actualPix = actualData.get(i).get(j);
          assertArrayEquals(actualPix, testImageData.get(i).get(j));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}