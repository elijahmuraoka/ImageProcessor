import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import controller.IPController;
import controller.IPControllerImpl;
import model.IPUtil;
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
    // load Thanos, make a intensity greyscale version of Thanos, and save the new
    // version of Thanos to testFiles folder
    try {
      this.in = new FileReader("testFiles/ScriptCommandExamples");
    } catch (FileNotFoundException e) {
      fail("Error: This file does not exist.");
    }
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.c = new IPControllerImpl(this.v, this.in);
    this.c.run();
    assertEquals("Welcome to our Image Processor Program!\n"
            + "Press `q` or `Q' to quit the program at any time.\n"
            + "Listed below are some basic commands you can execute on an image:\n"
            + "\n" + "(1) Load and store an image in this application.\n"
            + "    Input Format Example(s):\n" + "       load <imageName> <imagePath>\n"
            + "(2) Save any (un)modified images to this device.\n"
            + "    Input Format Example(s):\n"
            + "       save <imageName> <saveAsName> <imagePath>\n"
            + "(3) Change the brightness of an image.\n" + "    Input Format Example(s):\n"
            + "       ChangeBrightness <imageName> <increment> <destName>\n"
            + "       cb <imageName> <increment> <destName>\n" + "(4) Flip an image horizontally.\n"
            + "    Input Format Example(s):\n" + "       HorizontalFlip <imageName> <destName>\n"
            + "       flip-h <imageName> <destName>\n" + "(4) Flip an image vertically.\n"
            + "    Input Format Example(s):\n" + "       VerticalFlip <imageName> <destName>\n"
            + "       flip-v <imageName> <destName>\n"
            + "(5) Create a greyscale that visualizes the red, green, blue,\n"
            + "    value, intensity, or luma component of an image.\n"
            + "    Input Format Example(s):\n"
            + "       Greyscale <imageName> <visType> <destName>\n"
            + "       gs <imageName> <visType> <destName>\n"
            + "       gs-red <imageName> <destName>\n"
            + "       gs-blue <imageName> <destName>\n"
            + "       gs-green <imageName> <destName>\n"
            + "       gs-value <imageName> <destName>\n"
            + "       gs-intensity <imageName> <destName>\n"
            + "       gs-luma <imageName> <destName>\n" + "\n"
            + "Type 'menu' if you would like to see this information again.\n"
            + "Not a valid command. Please try again.\n"
            + "Currently loading image...\n"
            + "Width of image: 4000\n"
            + "Height of image: 6000\n"
            + "Maximum value of a color in this file (usually 255): 255\n"
            + "Successfully loaded image: images/Thanos.ppm\n"
            + "Successfully executed the command: gs-intensity\n"
            + "Successfully executed the command: flip-v\n" + "Saving image to res now...\n"
            + "Successfully saved new-thanos to res!\n"
            + "Welcome to our Image Processor Program!\n"
            + "Press `q` or `Q' to quit the program at any time.\n"
            + "Listed below are some basic commands you can execute on an image:\n" + "\n"
            + "(1) Load and store an image in this application.\n"
            + "    Input Format Example(s):\n" + "       load <imageName> <imagePath>\n"
            + "(2) Save any (un)modified images to this device.\n"
            + "    Input Format Example(s):\n"
            + "       save <imageName> <saveAsName> <imagePath>\n"
            + "(3) Change the brightness of an image.\n"
            + "    Input Format Example(s):\n"
            + "       ChangeBrightness <imageName> <increment> <destName>\n"
            + "       cb <imageName> <increment> <destName>\n"
            + "(4) Flip an image horizontally.\n"
            + "    Input Format Example(s):\n"
            + "       HorizontalFlip <imageName> <destName>\n"
            + "       flip-h <imageName> <destName>\n"
            + "(4) Flip an image vertically.\n"
            + "    Input Format Example(s):\n"
            + "       VerticalFlip <imageName> <destName>\n"
            + "       flip-v <imageName> <destName>\n"
            + "(5) Create a greyscale that visualizes the red, green, blue,\n"
            + "    value, intensity, or luma component of an image.\n"
            + "    Input Format Example(s):\n"
            + "       Greyscale <imageName> <visType> <destName>\n"
            + "       gs <imageName> <visType> <destName>\n"
            + "       gs-red <imageName> <destName>\n"
            + "       gs-blue <imageName> <destName>\n"
            + "       gs-green <imageName> <destName>\n"
            + "       gs-value <imageName> <destName>\n"
            + "       gs-intensity <imageName> <destName>\n"
            + "       gs-luma <imageName> <destName>\n" + "\n"
            + "Type 'menu' if you would like to see this information again.\n"
            + "Quitting the Image Processor Application now...", this.a.toString());
    File newThanos = new File("res/new-thanos.ppm");
    boolean existsNewThanos = newThanos.exists();
    assertTrue(existsNewThanos);
  }

  // Tests the load method in the controller implementation class
  @Test
  public void loadAndSave() {
    this.i = new IPUtil();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.in = new StringReader("");
    this.c = new IPControllerImpl(this.v, this.in);
    try {
      this.c.load("PPM2", "testFiles/PPM2");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertNotEquals(1, this.i.getWidth());
    assertNotEquals(1, this.i.getWidth());
    assertNull(this.i.getWorkingImageData());
    // load successfully read the image file and created a corresponding
    // image model representation
    try {
      this.c.save("PPM2", "newPPM2", "testFiles");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    //fail("load threw an IOException when it was not supposed to.");
    File newPPM2 = new File("testFiles/newPPM2.ppm");
    boolean existsPPM2 = newPPM2.exists();
    assertTrue(existsPPM2);
    try {
      BufferedReader load = new BufferedReader(new FileReader("testFiles/PPM2"));
      BufferedReader save = new BufferedReader(new FileReader("testFiles/newPPM2.ppm"));
      assertEquals(load.read(), save.read());
    } catch (FileNotFoundException e) {
      fail("Error: These files do not exist.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}