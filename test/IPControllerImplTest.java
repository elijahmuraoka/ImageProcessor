import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.IPController;
import controller.IPControllerImpl;
import model.IPModel;
import model.PPMmodel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor controller.
 */
public class IPControllerImplTest {
  IPModel m;
  IPView v;
  IPController c;
  Appendable a;
  Readable in;

  @Before
  // initializes some conditions and examples before testing
  public void init() {
    this.m = new PPMmodel();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    this.in = new StringReader("");
    this.c = new IPControllerImpl(this.m, this.v, this.in);
  }

  @Test
  public void initInvalid() {
    try {
      this.c = new IPControllerImpl(null, this.v, this.in);
      fail("Did not throw an IllegalArgumentException for a null model.");
    } catch (IllegalArgumentException e) {
      // successfully caught the IllegalArgumentException for a null model parameter
    }

    try {
      this.c = new IPControllerImpl(this.m, null, this.in);
      fail("Did not throw an IllegalArgumentException for a null view.");
    } catch (IllegalArgumentException e) {
      // successfully caught the IllegalArgumentException for a null view parameter
    }

    try {
      this.c = new IPControllerImpl(this.m, this.v, null);
      fail("Did not throw an IllegalArgumentException for a null readable.");
    } catch (IllegalArgumentException e) {
      // successfully caught the IllegalArgumentException for a null readable parameter
    }

    try {
      this.in = new BadReadable();
      this.c = new IPControllerImpl(this.m, this.v, this.in);
      c.go();
      fail("Did not throw an IOException for a bad readable.");
    } catch (IllegalStateException e) {
      // successfully caught the IOException for a bad readable parameter
    }
  }

  @Test
  public void go() {

  }
}