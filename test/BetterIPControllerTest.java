import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.BetterIPController;
import controller.BetterIPControllerImpl;
import model.BetterIPModel;
import model.ImageFactory;
import view.IPGuiView;
import view.SimpleGuiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the necessary methods for the BetterIPController class.
 */
public class BetterIPControllerTest {
  private ImageFactory factory;
  private IPGuiView v;
  private BetterIPController c;
  private Appendable a;
  private Readable in;
  private BetterIPModel m;
  private ArrayList<String> knownImageModelNames;
  private Map<String, BetterIPModel> knownImageModels;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.a = new StringBuilder();
    this.v = new SimpleGuiView();
    this.in = new StringReader("");
    this.c = new BetterIPControllerImpl(this.v);
    this.v.initialize();
    this.v.setController(c);
    this.knownImageModelNames = new ArrayList<String>();
    this.knownImageModels = new HashMap<String, BetterIPModel>();

    // the following tests will be based on the results of running this script file using this
    // new MVC design
    try {
      this.in = new FileReader("testFiles/ScriptCommandExamples2");
    } catch (FileNotFoundException e) {
      fail("Error: This file does not exist.");
    }
    //this.c.run();
  }

  // Tests the run method in the better controller implementation class
  // to make sure you can load an image, execute a command on it,
  // and save it to a new directory with a new name
  @Test
  public void run() {
    this.a = new StringBuilder();
    this.v = new SimpleGuiView();
    this.c = new BetterIPControllerImpl(this.v);
    this.v.initialize();
    this.c.run();

    try {
      this.in = new FileReader("testFiles/ScriptCommandExamples2");
    } catch (FileNotFoundException e) {
      fail("Error: This file does not exist.");
    }

    File newImage = new File("res/new-bucketboi.ppm");
    boolean existsNewImage = newImage.exists();
    assertTrue(existsNewImage);
  }

  @Test
  public void load() {
    try {
      this.c.load("images/togo.jpeg", "images/togo.jpeg");
    } catch (IOException e) {
      fail("Error: This file does not exist.");
    }

    BetterIPModel oldM = this.knownImageModels.getOrDefault("images/togo.jpeg", null);
    ImageFactory factory = new ImageFactory("images/togo.jpeg");
    BetterIPModel m = factory.createImageModel();
    m.read();
    m.setImageName("images/togo.jpeg");

    if (oldM != null) {
      m.setCommandList(oldM.getCommandList());
      m.setImage(oldM.getImage());
      m.setUndoCounter(oldM.getUndoCounter());
    }

    this.knownImageModels.put(m.getImageName(), m);
    String loadCommand = "load\nimages/togo.jpeg\nimages/togo.jpeg\n";

    if (!m.getCommandList().contains(loadCommand)) {
      this.c.getKnownImageModels().get("images/togo.jpeg").addToCommandList(loadCommand);
    }
    this.v.showMainPanel("images/togo.jpeg");
    this.v.refresh();

    assertEquals(this.knownImageModels.get(m.getImageName()).getImageName(),
            this.c.getKnownImageModels().get(m.getImageName()).getImageName());
  }

  @Test
  public void getKnownImageModels() {
    try {
      this.c.load("images/togo.jpeg", "images/togo.jpeg");
      this.c.load("images/bucketboi.jpg", "images/bucketboi.jpg");
    } catch (IOException e) {
      fail("Error: This file does not exist.");
    }

    this.knownImageModelNames.add("images/togo.jpeg");
    this.knownImageModelNames.add("images/bucketboi.jpg");

    int count = 0;
    for (Map.Entry<String, BetterIPModel> element : this.c.getKnownImageModels().entrySet()) {
      assertEquals(this.knownImageModelNames.get(count), element.getKey());
      assertEquals(this.knownImageModelNames.get(count), element.getValue().getImageName());
      count = count + 1;
    }

    assertEquals(knownImageModelNames.size(), this.c.getKnownImageModels().size());
  }

  @Test
  public void setReadable() {

  }
}