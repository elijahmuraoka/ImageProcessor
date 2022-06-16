import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import Utils.ImageFactory;
import Utils.ImageFile;
import controller.IPController;
import controller.IPControllerImpl;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor controller.
 */
public class IPControllerImplTest {
  ImageFactory factory;
  ImageFile file;
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
      // fail("Did not throw an IOException for a bad readable.");
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
  // to make sure you can load an image, execute a command on it,
  // and save it to a new directory with a new name
  @Test
  public void run() {
    // load bucketboi.jpg, make a intensity greyscale version of Thanos, and save the new
    // version of bucketboi as a ppm image to th res folder
    try {
      this.in = new FileReader("testFiles/ScriptCommandExamples");
    } catch (FileNotFoundException e) {
      fail("Error: This file does not exist.");
    }
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.c = new IPControllerImpl(this.v, this.in);
    this.c.run();
    System.out.println(this.a.toString());
    // assertEquals("", this.a.toString());
    File newImage = new File("res/new-bucketboi.ppm");
    boolean existsNewImage = newImage.exists();
    assertTrue(existsNewImage);
  }

  // Tests the load method in the controller implementation class
  @Test
  public void loadAndSave() {
    this.factory = new ImageFactory("testFiles/PPM2.ppm");
    this.file = this.factory.createImageFile();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.in = new StringReader("");
    this.c = new IPControllerImpl(this.v, this.in);
    try {
      this.c.load("PPM2.ppm", "testFiles/PPM2.ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotEquals(1, this.file.getWidth());
    assertNotNull(this.file.getWorkingImageData());
    // load successfully read the image file and created a corresponding
    // image model representation
    try {
      this.c.save("PPM2.ppm", "newPPM2",
              "testFiles", "ppm");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //fail("load threw an IOException when it was not supposed to.");
    File newPPM2 = new File("testFiles/newPPM2.ppm");
    boolean existsPPM2 = newPPM2.exists();
    assertTrue(existsPPM2);
    try {
      BufferedReader load = new BufferedReader(new FileReader("testFiles/PPM2.ppm"));
      BufferedReader save = new BufferedReader(new FileReader("testFiles/newPPM2.ppm"));
      assertEquals(load.read(), save.read());
    } catch (FileNotFoundException e) {
      fail("Error: These files do not exist.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}