package view;

import java.io.IOException;

/**
 * An implementation of the Image Processor View interface. This implementation
 * supports the ability to render messages, ADD ON.
 */
public class IPViewImpl implements IPView {
  private final Appendable a;

  /**
   * ADD METHOD PURPOSE STATEMENT;
   *
   * @param a when the appendable object
   * @throws IllegalArgumentException when the appendable object is null
   */
  public IPViewImpl(Appendable a) throws IllegalArgumentException {
    if (a == null) {
      throw new IllegalArgumentException("The appendable object cannot be null." +
              "\nPlease try a new valid parameter");
    }
    this.a = a;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.a.append(message);
  }
}
