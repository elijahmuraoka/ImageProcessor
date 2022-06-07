import java.io.IOException;

/**
 * This class represents a bad (invalid) appendable object to be used in testing.
 * Using this BadAppendable object should always throw an IOException.
 */
public class BadAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("BadAppendable has successfully thrown an IOException.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("BadAppendable has successfully thrown an IOException.");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("BadAppendable has successfully thrown an IOException.");
  }
}
