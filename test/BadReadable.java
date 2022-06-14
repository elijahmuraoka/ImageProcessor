import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This class represents a bad (invalid) readable object to be used in testing.
 * Using this BadReadable object should always throw an IOException.
 */
public class BadReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("BadReadable has successfully thrown an IOException.");
  }
}
