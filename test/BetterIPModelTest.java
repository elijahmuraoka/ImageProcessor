import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.BetterIPModel;
import model.ImageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BetterIPModelTest {
  BetterIPModel m;

  @Before
  public void init() {
    ImageFactory factory = new ImageFactory("testFiles/PPM5.ppm");
    this.m = factory.createImageModel();
    this.m.setCommandList(new ArrayList<>(Arrays.asList("gs potato blue potato")));
    BufferedImage img = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);
    img.setRGB(0, 0, new Color(10, 20, 30).getRGB());
    img.setRGB(1, 0, new Color(33, 200, 158).getRGB());
    this.m.setImage(img);
    List<Integer> undoCounter = new ArrayList<>();
    undoCounter.add(2);
    undoCounter.add(1);
    undoCounter.add(5);
    this.m.setUndoCounter(undoCounter);
  }


  @Test
  public void getCommandList() {
    List<String> fail = new ArrayList<>();
    fail.add("gs dog blue seaTurtle");
    assertNotEquals(fail, this.m.getCommandList());
    List<String> expected = new ArrayList<>();
    expected.add("gs potato blue potato");
    assertEquals(expected, this.m.getCommandList());
  }

  @Test
  public void addToCommandList() {
    // check initial values
    List<String> expected = new ArrayList<>();
    expected.add("gs potato blue potato");
    assertEquals(expected, this.m.getCommandList());
    expected.add("spam city brahhh");
    assertNotEquals(expected, this.m.getCommandList());

    // add to the model's command list and check results
    this.m.addToCommandList("spam city brahhh");
    assertEquals(expected, this.m.getCommandList());
  }

  @Test
  public void setCommandList() {
    // check initial values
    List<String> expected = new ArrayList<>();
    expected.add("ayooo\n");
    expected.add("fdm fdm fdm");
    assertNotEquals(expected, this.m.getCommandList());
    // set the command list
    this.m.setCommandList(expected);
    assertEquals(expected, this.m.getCommandList());
  }

  @Test
  public void make() {
    this.m.addToCommandList("penguin on a chair");
    Scanner scan = new Scanner(this.m.make());
    StringReader readerExpected = new StringReader("gs potato blue potato penguin on a chair");
    Scanner scanExpected = new Scanner(readerExpected);
    while (scan.hasNext() && scanExpected.hasNext()) {
      assertEquals(scan.next(), scanExpected.next());
    }
  }

  @Test
  public void undo() {
    try {
      this.m.undo();
    } catch (IllegalStateException e) {
      // successfully caught the IAE thrown when attempting to undo with only one command in the
      // list
    }
    this.m.addToCommandList("chair on 7 penguin");
    List<String> expected = new ArrayList<>();
    expected.add("gs potato blue potato");
    expected.add("chair on 7 penguin");
    assertEquals(expected, this.m.getCommandList());
    // check results before and after running the undo command
    this.m.undo();
    assertNotEquals(expected, this.m.getCommandList());
    expected.remove(1);
    assertEquals(expected, this.m.getCommandList());
  }

  @Test
  public void setImage() {
    BufferedImage expected = new BufferedImage(1, 2, BufferedImage.TYPE_INT_ARGB);
    expected.setRGB(0, 0, new Color(10, 20, 30).getRGB());
    expected.setRGB(0, 1, new Color(33, 200, 158).getRGB());

    BufferedImage actualImg = (BufferedImage) this.m.getImage();
    assertEquals(1, actualImg.getHeight());
    assertEquals(2, actualImg.getWidth());
    this.m.setImage(expected);
    actualImg = (BufferedImage) this.m.getImage();
    assertEquals(2, actualImg.getHeight());
    assertEquals(1, actualImg.getWidth());
    try {
      assertEquals(0, actualImg.getRGB(0, 5));
    } catch (ArrayIndexOutOfBoundsException e) {
      // successfully catches the exception thrown when retrieving a non-existent RGB value
    }
    assertEquals(-16116706, actualImg.getRGB(0, 0));
    assertEquals(-14563170, actualImg.getRGB(0, 1));
  }

  @Test
  public void getImage() {
    BufferedImage actualImg = (BufferedImage) this.m.getImage();
    assertEquals(-16116706, actualImg.getRGB(0, 0));
    assertEquals(-14563170, actualImg.getRGB(1, 0));
    try {
      assertEquals(0, actualImg.getRGB(2, 0));
    } catch (ArrayIndexOutOfBoundsException e) {
      // successfully catches the exception thrown when retrieving a non-existent RGB value
    }
    assertEquals(1, actualImg.getHeight());
    assertEquals(2, actualImg.getWidth());
  }

  @Test
  public void generateHistograms() {
     /*
    P3
    # PPM5.ppm
    2 2
    254
    188 44 68 220 44 68
    203 44 231 220 149 109
    */
    // all expected histograms
    List<Map<Integer, Integer>> expected = new ArrayList<>();
    // make the expected red histogram
    Map<Integer, Integer> redHistogram = new HashMap<>();
    redHistogram.put(188, 1);
    redHistogram.put(220, 2);
    redHistogram.put(203, 1);
    expected.add(redHistogram);
    // make the expected green histogram
    Map<Integer, Integer> greenHistogram = new HashMap<>();
    greenHistogram.put(44, 3);
    greenHistogram.put(149, 1);
    expected.add(greenHistogram);
    // make the expected blue histogram
    Map<Integer, Integer> blueHistogram = new HashMap<>();
    blueHistogram.put(68, 2);
    blueHistogram.put(231, 1);
    blueHistogram.put(109, 1);
    expected.add(blueHistogram);
    // make the expected intensity histogram
    Map<Integer, Integer> intensityHistogram = new HashMap<>();
    intensityHistogram.put(100, 1);
    intensityHistogram.put(110, 1);
    intensityHistogram.put(159, 2);
    expected.add(intensityHistogram);

    Map<Integer, Integer> redExpected = expected.get(0);
    Map<Integer, Integer> greenExpected = expected.get(1);
    Map<Integer, Integer> blueExpected = expected.get(2);
    Map<Integer, Integer> intensityExpected = expected.get(3);

    try {
      int redExpectedFail = redExpected.get(94);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the red map
    }
    int redExpected188 = redExpected.get(188);
    assertEquals(1, redExpected188);
    int redExpected220 = redExpected.get(220);
    assertEquals(2, redExpected220);
    int redExpected203 = redExpected.get(203);
    assertEquals(1, redExpected203);

    try {
      int greenExpectedFail = greenExpected.get(43);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the green map
    }
    int greenExpected44 = greenExpected.get(44);
    assertEquals(3, greenExpected44);
    int greenExpected149 = greenExpected.get(149);
    assertEquals(1, greenExpected149);

    try {
      int blueExpectedFail = redExpected.get(0);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the blue map
    }
    int blueExpected68 = blueExpected.get(68);
    assertEquals(2, blueExpected68);
    int blueExpected231 = blueExpected.get(231);
    assertEquals(1, blueExpected231);
    int blueExpected109 = blueExpected.get(109);
    assertEquals(1, blueExpected109);

    try {
      int intensityFail = intensityExpected.get(0);
    } catch (NullPointerException e) {
      // successfully caught the NullPointerException thrown when
      // attempting to retrieve invalid values from the intensity map
    }
    int intensityExpected100 = intensityExpected.get(100);
    assertEquals(1, intensityExpected100);
    int intensityExpected110 = intensityExpected.get(110);
    assertEquals(1, intensityExpected110);
    int intensityExpected159 = intensityExpected.get(159);
    assertEquals(2, intensityExpected159);
  }

  @Test
  public void addToUndoCounter() {
    assertEquals(3, this.m.getUndoCounter().size());
    this.m.addToUndoCounter(43);
    assertEquals(4, this.m.getUndoCounter().size());
    int addedNumber = this.m.getUndoCounter().get(3);
    assertEquals(43, addedNumber);
  }

  @Test
  public void setUndoCounter() {
    assertEquals(3, this.m.getUndoCounter().size());
    List<Integer> expected = new ArrayList<>(List.of(32));
    this.m.setUndoCounter(expected);
    assertEquals(1, this.m.getUndoCounter().size());
    int undoValue = this.m.getUndoCounter().get(0);
    assertEquals(32, undoValue);
  }

  @Test
  public void getUndoCounter() {
    assertEquals(3, this.m.getUndoCounter().size());
    int item0 = this.m.getUndoCounter().get(0);
    assertEquals(2, item0);
    int item1 = this.m.getUndoCounter().get(1);
    assertEquals(1, item1);
    int item2 = this.m.getUndoCounter().get(2);
    assertEquals(5, item2);
  }
}