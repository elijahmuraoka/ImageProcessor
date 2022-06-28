package model;

import java.awt.Image;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A better abstract image model class that can be used to store and undo changes on this
 * specific image model.
 */
public abstract class BetterAbstractImageModel extends AbstractImageModel implements BetterIPModel {
  private List<String> commandList = new ArrayList<>();

  private List<Integer> undoCounter = new ArrayList<>();

  private Image image = null;


  /**
   * An image model constructor used to create representative image objects using
   * the given image name, height, width, and a 2D array of pixels (size-3 arrays)
   * representing the image's data.
   *
   * @param imageName        the current name of the image
   * @param height           the height of the image
   * @param width            the width of the image
   * @param maxComponent     the maximum color component
   * @param workingImageData the working image data displayed as a 2-D list of Pixels
   *                         Each Pixel is an array of 3 integers that represent
   *                         its RGB components respectively
   * @param fileName         the name of the image file
   * @throws IllegalArgumentException when the image's height and/or width are negative
   */
  public BetterAbstractImageModel(String imageName, int height, int width, int maxComponent,
                                  List<List<int[]>> workingImageData, String fileName)
          throws IllegalArgumentException {
    super(imageName, height, width, maxComponent, workingImageData, fileName);
  }

  /**
   * The abstract image file constructor used to create an Image File object and store its
   * file name.
   *
   * @param fileName the (desired) name of the file
   */
  public BetterAbstractImageModel(String fileName) {
    super(fileName);
  }

  @Override
  public abstract String generateFileName(String saveAsName, String imagePath);

  @Override
  public List<String> getCommandList() {
    return this.commandList;
  }

  @Override
  public void addToCommandList(String command) {
    this.commandList.add(command);
  }

  @Override
  public Readable make() {
    int counter = 0;
    StringBuilder result = new StringBuilder();
    for (String command : this.commandList) {
      counter++;
      System.out.println("Command " + counter + ":" + command);
      result.append(command).append(" ");
    }
    return new StringReader(result.toString());
  }

  @Override
  public void undo() throws IllegalStateException {
    // should not undo the load command
    if (this.commandList.size() > 1) {
      // removes the most recent command
      this.commandList.remove(this.commandList.size() - 1);
    } else {
      throw new IllegalStateException("There are no more changes to undo.");
    }
  }

  @Override
  public Image getImage() {
    return this.image;
  }

  @Override
  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public void setCommandList(List<String> commandList) {
    this.commandList = commandList;
  }

  @Override
  public List<Map<Integer, Integer>> generateHistograms() {
    List<Map<Integer, Integer>> allHistograms = new ArrayList<>();
    Map<Integer, Integer> redHistogram = new HashMap<>();
    Map<Integer, Integer> greenHistogram = new HashMap<>();
    Map<Integer, Integer> blueHistogram = new HashMap<>();
    Map<Integer, Integer> intensityHistogram = new HashMap<>();
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        int[] pixel = this.workingImageData.get(i).get(j);
        this.addToHistogram(redHistogram, pixel[0]);
        this.addToHistogram(greenHistogram, pixel[1]);
        this.addToHistogram(blueHistogram, pixel[2]);
        int intensity = (pixel[0] + pixel[1] + pixel[2]) / 3;
        this.addToHistogram(intensityHistogram, intensity);
      }
    }
    allHistograms.add(redHistogram);
    allHistograms.add(greenHistogram);
    allHistograms.add(blueHistogram);
    allHistograms.add(intensityHistogram);
    return allHistograms;
  }

  @Override
  public void addToUndoCounter(int i) {
    this.undoCounter.add(i);
  }

  @Override
  public void setUndoCounter(List<Integer> undoCounter) {
    this.undoCounter = undoCounter;
  }

  @Override
  public List<Integer> getUndoCounter() {
    return this.undoCounter;
  }

  private void addToHistogram(Map<Integer, Integer> histogram, Integer value) {
    if (!histogram.containsKey(value)) {
      histogram.put(value, 1);
    } else {
      histogram.put(value, histogram.get(value) + 1);
    }
  }
}
