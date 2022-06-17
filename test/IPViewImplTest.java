import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the Image Processor view implementation class.
 */
public class IPViewImplTest {
  Appendable a;
  IPView v;

  @Before
  // initializes some conditions and examples before testing
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
  }

  // test that IPViewImpl successfully throws an IllegalStateException when its appendable
  // object parameter is null
  @Test(expected = IllegalArgumentException.class)
  public void initInvalid() {
    this.v = new IPViewImpl(null);
  }

  @Test
  public void renderMessage() {
    // tests an invalid appendable object as a constructor parameter
    try {
      this.v = new IPViewImpl(new BadAppendable());
      v.renderMessage("Celtics in Game 6");
      fail("Did not throw an IOException when using the Bad Appendable as a parameter.");
    } catch (IOException e) {
      // successfully caught the IOException thrown by the Bad Appendable object
    }
    this.v = new IPViewImpl(this.a);
    // checks that the current appendable object is empty
    assertEquals("", this.a.toString());

    // checks that the user can sucessfully add string messages to the appendable objec
    try {
      this.v.renderMessage("This is a test.");
    } catch (IOException e) {
      fail("renderMessage threw an IOException when it was not supposed to.");
    }
    assertEquals("This is a test.", this.a.toString());

    try {
      this.v.renderMessage(" Please work.");
    } catch (IOException e) {
      fail("renderMessage threw an IOException when it was not supposed to.");
    }
    assertEquals("This is a test. Please work.", this.a.toString());
  }
}