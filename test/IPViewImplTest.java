import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.fail;

public class IPViewImplTest {
  Appendable a;
  IPView v;

  @Before
  // initializes some conditions and examples before testing
  public void init() {
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
  }


  @Test
  public void renderMessage() {
    try {
      this.v = new IPViewImpl(new BadAppendable());
      v.renderMessage("Celtics in Game 6");
      fail("Did not throw an IOException when using the Bad Appendable as a parameter.");
    } catch (IOException e) {
      // successfully caught the IOException thrown by the Bad Appendable object
    }
  }
}