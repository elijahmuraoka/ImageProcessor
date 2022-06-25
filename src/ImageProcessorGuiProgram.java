import java.io.StringReader;

import controller.BetterIPControllerImpl;
import view.IPGuiView;
import view.SimpleGuiView;

public class ImageProcessorGuiProgram {

  public static void main(String... args) {
    IPGuiView view = new SimpleGuiView();
    BetterIPControllerImpl controller = new BetterIPControllerImpl(view, new StringReader(""));
    view.initialize();
  }
}
