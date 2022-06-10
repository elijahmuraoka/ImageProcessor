import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.ChangeBrightness;
import controller.IPController;
import controller.IPControllerImpl;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor controller.
 */
public class IPControllerImplTest {
  IPUtil i;
  IPView v;
  IPController c;
  Appendable a;
  Readable in;
  List<List<int[]>> imageData;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.in = new StringReader("");
    this.c = new IPControllerImpl(this.v, this.in);
    this.i = new IPUtil();
  }

  @Test
  public void initInvalid() {

    try {
      this.c = new IPControllerImpl(null, this.in);
      fail("Did not throw an IllegalArgumentException for a null view.");
    } catch (IllegalArgumentException e) {
      // successfully caught the IllegalArgumentException for a null view parameter
    }

    try {
      this.c = new IPControllerImpl(this.v, null);
      fail("Did not throw an IllegalArgumentException for a null readable.");
    } catch (IllegalArgumentException e) {
      // successfully caught the IllegalArgumentException for a null readable parameter
    }

    try {
      this.in = new BadReadable();
      this.c = new IPControllerImpl(this.v, this.in);
      c.run();
      fail("Did not throw an IOException for a bad readable.");
    } catch (IllegalStateException e) {
      // successfully caught the IOException for a bad readable parameter
    }

    try {
      this.in = new StringReader("ayo");
      this.v = new IPViewImpl(new BadAppendable());
      this.c = new IPControllerImpl(this.v, this.in);
      c.run();
      fail("Did not throw an IOException for a bad appendable.");
    } catch (IllegalStateException e) {
      // successfully caught the IOException for a bad appendable object
      // passed to the view
    }
  }

  // Tests the run method in the controller implementation class
  @Test
  public void run() {

  }

  // Tests the load method in the controller implementation class
  @Test
  public void load() {
    try {
      assertNotEquals(1, this.i.getWidth());
      assertNotEquals(1, this.i.getWidth());
      assertNull(this.i.getWorkingImageData());
      this.c.load("PPM2", "testFiles/PPM2");
      // load successfully read the image file and created a corresponding
      // image model representation
    } catch (IOException e) {
      fail("load threw an IOException when it was not supposed to.");
    }
    assertEquals(1, this.i.getWidth());
    assertEquals(4, this.i.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>
            (Arrays.asList
                    ((new ArrayList<>
                                    (List.of(new int[]{23, 22, 145}
                                    ))),
                            (new ArrayList<>
                                    (List.of(new int[]{249, 99, 243}))),
                            (new ArrayList<>
                                    (List.of(new int[]{172, 181, 50}))),
                            (new ArrayList<>
                                    (List.of(new int[]{163, 29, 242})))));
    // compare each element of the two array lists
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 1; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
                  this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    try {
      this.c.save("PPM2", "newPPM2", "testFiles");
      fail("load threw an IOException when it was not supposed to.");
    } catch (IOException e) {
      // saved newPPM2 successfully to the correct image path!
    }
    File tempFile = new File("textFiles/newPPM2");
    boolean existsPPM2 = tempFile.exists();
    assertTrue(existsPPM2);
    //    try {
    //      File testFile = new File("testFiles/TestLoadImg.ppm");
    //      testFile.createNewFile();
    //
    //      // c.load("dfwesfrc.ppm", "testFiles/TestLoadImg.ppm");
    //      assertEquals(true, testFile.exists());
    //    } catch (IOException e) {
    //      throw new RuntimeException(e);
    //    }
  }

  // Tests the save method in the controller implementation class
  /*
  STEPS:
  load an img
  modify it
  save it
  check if file.exists()
   */
  @Test
  public void save() {
//    this.c =
//    c.run();
    try {
      this.imageData = new ArrayList<>();
      List<int[]> row1 = new ArrayList<int[]>(Arrays.asList(new int[]{86, 213, 93},
              new int[]{116, 203, 163}, new int[]{171, 228, 66}));

      List<int[]> row2 = new ArrayList<>(Arrays.asList(new int[]{0, 57, 166},
              new int[]{71, 101, 127}, new int[]{240, 15, 80}));

      List<int[]> row3 = new ArrayList<>(Arrays.asList(new int[]{64, 241, 185},
              new int[]{13, 241, 245}, new int[]{97, 72, 170}));

      this.imageData.add(row1);
      this.imageData.add(row2);
      this.imageData.add(row3);

      IPModel im = new ImageModel("PPM1", 3, 3, imageData);

      c.load("PPM1", "testFiles");

      ChangeBrightness cb = new ChangeBrightness();

      cb.execute(im, new Scanner(new StringReader("50 PPM21")));
      c.save("PPM1", "PPM21", "testFiles/");

      File testFile = new File("testFiles/PPM21");
      assertEquals(true, testFile.exists());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}