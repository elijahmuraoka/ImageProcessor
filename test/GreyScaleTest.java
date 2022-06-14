import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import commands.GreyScale;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;
import view.IPViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents all tests needed for the GreyScale command class.
 */
public class GreyScaleTest {
  GreyScale gs;
  Appendable a;
  IPView v;
  IPUtil i;
  IPModel m;
  Scanner scan;

  // initializes some conditions and examples before testing
  @Before
  public void init() {
    this.i = new IPUtil();
    this.a = new StringBuilder();
    this.v = new IPViewImpl(this.a);
    try {
      this.i.readPPM("testFiles/PPM4", this.v);
    } catch (IOException e) {
      fail("readPPM threw an IOException when it was not supposed to.");
    }
    this.m = new ImageModel();
    this.m.setImageName("testGS");
    this.m.setWidth(this.i.getWidth());
    this.m.setHeight(this.i.getHeight());
    this.m.setWorkingImageData(this.i.getWorkingImageData());
    this.gs = new GreyScale();
  }

  @Test
  public void gsRed() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
                  this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the red greyscale command
    this.scan = new Scanner(new StringReader("red gs-red"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGsRed = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
        new int[]{188, 188, 188}, new int[]{220, 220, 220}))), (new ArrayList<>(Arrays.asList(
            new int[]{203, 203, 203}, new int[]{135, 135, 135})))));

    // compare each element of the red greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGsRed.get(i).get(j)[k],
                  this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }

  @Test
  public void gsGreen() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
              this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the green greyscale command
    this.scan = new Scanner(new StringReader("green gs-green"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGsGreen = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{44, 44, 44}, new int[]{86, 86, 86}))), (new ArrayList<>(
            Arrays.asList(new int[]{185, 185, 185}, new int[]{149, 149, 149})))));

    // compare each element of the green greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGsGreen.get(i).get(j)[k],
              this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }

  @Test
  public void gsBlue() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
              this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the blue greyscale command
    this.scan = new Scanner(new StringReader("blue gs-blue"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component equal to its green component value
    List<List<int[]>> expectedGSBlue = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
        new int[]{68, 68, 68}, new int[]{68, 68, 68}))), (new ArrayList<>(Arrays.asList(
            new int[]{231, 231, 231}, new int[]{254, 254, 254})))));

    // compare each element of the blue greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGSBlue.get(i).get(j)[k],
              this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }

  @Test
  public void gsLuma() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));
    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
              this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the luma greyscale command
    this.scan = new Scanner(new StringReader("luma gs-luma"));
    this.gs.execute(this.m, this.scan);
    // "lumify" each pixel component
    List<List<int[]>> expectedGsLuma = new ArrayList<>(Arrays.asList((new ArrayList<>(Arrays.asList(
        new int[]{76, 76, 76}, new int[]{113, 113, 113}))), (new ArrayList<>(Arrays.asList(
            new int[]{192, 192, 192}, new int[]{153, 153, 153})))));
    // compare each element of the luma greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGsLuma.get(i).get(j)[k],
              this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }

  @Test
  public void gsIntensity() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));

    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
              this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the intensity greyscale command
    this.scan = new Scanner(new StringReader("intensity gs-intensity"));
    this.gs.execute(this.m, this.scan);
    // "intensify" each pixel component
    List<List<int[]>> expectedGsIntensity = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{76, 76, 76}, new int[]{124, 124, 124}))), (new ArrayList<>(
            Arrays.asList(new int[]{206, 206, 206}, new int[]{179, 179, 179})))));

    // compare each element of the intensity greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGsIntensity.get(i).get(j)[k],
              this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }

  @Test
  public void gsValue() {
    // confirm initial values
    assertEquals("testGS", this.m.getImageName());
    assertEquals(2, this.m.getWidth());
    assertEquals(2, this.m.getHeight());
    List<List<int[]>> expectedImageData = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 44, 68}, new int[]{220, 86, 68}))), (new ArrayList<>(
            Arrays.asList(new int[]{203, 185, 231}, new int[]{135, 149, 254})))));
    // compare each element of the two array lists
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedImageData.get(i).get(j)[k],
              this.i.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
    // execute the value greyscale command
    this.scan = new Scanner(new StringReader("value gs-value"));
    this.gs.execute(this.m, this.scan);
    // set each pixel component to its maximum component value
    List<List<int[]>> expectedGsValue = new ArrayList<>(Arrays.asList((new ArrayList<>(
        Arrays.asList(new int[]{188, 188, 188}, new int[]{220, 220, 220}))), (new ArrayList<>(
            Arrays.asList(new int[]{231, 231, 231}, new int[]{254, 254, 254})))));

    // compare each element of the value greyscale expected array and the
    // newly modified image data array
    for (int i = 0; i < m.getHeight(); i++) {
      for (int j = 0; j < m.getWidth(); j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedGsValue.get(i).get(j)[k],
              this.m.getWorkingImageData().get(i).get(j)[k]);
        }
      }
    }
  }
}