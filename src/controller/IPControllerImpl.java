package controller;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

import model.IPModel;
import model.IPUtil;
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
  private final IPModel m;
  // the Readable object representing the user's inputs
  private final Readable in;

  /**
   * An Image Processor controller implementation constructor that takes in a model, view, and
   * readable object.
   *
   * @param m  an Image Processor model
   * @param v  an Image Processor view
   * @param in a Readable object
   * @throws IllegalArgumentException when either the model and/or Readable object are null
   */
  public IPControllerImpl(IPModel m, IPView v, Readable in) throws IllegalArgumentException {
    if (m == null || v == null || in == null) {
      throw new IllegalArgumentException("Either the model, view, and/or " +
              "readable object(s) are null.\nPlease try new valid parameters.");
    }
    this.m = m;
    this.v = v;
    this.in = in;
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
    this.fileName = this.generateFileName(imageName, imagePath);

    // how do you verify a correct computer path??
    // read the PPM file passed in
    IPUtil util = new IPUtil();
    util.readPPM(this.fileName);
    // make a copy of the PPM image data in this model
    this.width = util.width;
    this.height = util.height;
    this.workingImage = util.workingImage;
  }

  @Override
  public void save(String imageName, String imagePath) throws IllegalArgumentException {
    String Header = "P3\n" + this.width + " " + this.height + "\n255\n";
    StringBuilder imageData = new StringBuilder();
    for (int[] pixel : this.workingImage) {
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
