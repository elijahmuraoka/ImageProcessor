import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import controller.IPController;
import controller.IPControllerImpl;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor controller.
 */
public class IPControllerImplTest {
  IPView v;
  IPController c;
  Appendable a;
  Readable in;

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
      c.go();
      fail("Did not throw an IOException for a bad readable.");
    } catch (IllegalStateException e) {
      // successfully caught the IOException for a bad readable parameter
    }
  }

  @Test
  public void go() {

  }

  @Test
  public void load() {
    try {
      /*
      File testFile = new File("testFiles/TestLoadImg.ppm");
      testFile.createNewFile();
       */


      c.load("dfwesfrc.ppm", "testFiles/");

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Test
  public void save() {

  }

}