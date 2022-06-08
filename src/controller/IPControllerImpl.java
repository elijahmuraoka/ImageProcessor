package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.IPCommand;
import commands.changeBrightness;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;

/**
 * An implementation of the Image Processor controller interface used to process user inputs
 * and communicate between the model and view. Specifically, this controller supports and can
 * apply any operation provided from its model. This includes...
 * - Loading an image
 * - Saving an image
 * - Visualizing a specific RGB component of an image
 * - Visualizing value, intensity, or luma of an image
 * - Flip an image horizontally or vertically
 * - Brightening an image
 */
public class IPControllerImpl implements IPController {
  // the view used by this controller to process and display
  // system-generated outputs
  private final IPView v;
  // the model which this controller will process and use to run operations
  // private final IPModel m;
  // the Readable object representing the user's inputs
  private final Readable in;
  // the map used to store all current working images
  private final List<IPModel> knownImageModels;
  private final Map<String, Function<Scanner, IPCommand>> knownCommands;

  /**
   * An Image Processor controller implementation constructor that takes in a model, view, and
   * readable object.
   *
   * @param v  an Image Processor view
   * @param in a Readable object
   * @throws IllegalArgumentException when either the model and/or Readable object are null
   */
  public IPControllerImpl(IPModel m, IPView v, Readable in) throws IllegalArgumentException {
    if (v == null || in == null) {
      throw new IllegalArgumentException("Either the model, view, and/or " +
              "readable object(s) are null.\nPlease try new valid parameters.");
    }
    this.v = v;
    this.in = in;
    this.knownImageModels = new ArrayList<>();
    this.knownCommands = new HashMap<>();
    this.knownCommands.put("changeBrightness", scan -> new changeBrightness(scan));
  }

  @Override
  public void go() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    scan.next();
  }


  @Override
  public String generateFileName(String imageName, String imagePath)
          throws IllegalArgumentException {
    return imagePath + "/" + imageName + ".ppm";
  }

  @Override
  public void load(String imageName, String imagePath)
          throws IllegalArgumentException {
    // generates the fileName to initialize the image that this model will be working on
    String fileName = this.generateFileName(imageName, imagePath);

    // how do you verify a correct computer path??
    // read the PPM file passed in
    IPUtil util = new IPUtil();
    util.readPPM(fileName);
    // make a copy of the PPM image data in this model
    IPModel m = new ImageModel();
    m.setWidth(util.getWidth());
    m.setHeight(util.getHeight());
    m.setWorkingImageData(util.getWorkingImageData());
    this.knownImageModels.add(m);
  }

  @Override
  public void save(String imageName, String imagePath) throws IllegalArgumentException {
    IPModel m = null;
    for (int i = 0; i < this.knownImageModels.size(); i++) {
      if (this.knownImageModels.get(i).getImageName().equals(imageName)) {
        m = this.knownImageModels.get(i);
        break;
      }
    }
    String Header = "P3\n" + m.getWidth() + " " + m.getHeight() + "\n255\n";
    StringBuilder imageData = new StringBuilder();
    for (int[] pixel : m.getWorkingImageData()) {
      for (int component : pixel) {
        imageData.append(component).append(" ");
      }
    }

    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(this.generateFileName(imageName,
              imagePath)));
      bw.write(Header);
      bw.write(imageData.toString());
      bw.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid parameter(s), cannot save image file\n"
              + e.getMessage());
    }
  }
}
