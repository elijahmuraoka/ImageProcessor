package controller;

import java.awt.*;

import javax.swing.*;

import model.IPModel;
import view.IPView;

/**
 * An implementation of the Image Processor controller interface used to process user inputs
 * and communicate between the model and view. Specifically this controller supports operations
 * such as...
 * - Loading an Image
 * - Saving an Image
 * - FILL IN THE REST
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
   * @param m  an Image Processor model
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

  }

  public void testMod() {
    //
  }

  // Damian, this should be in the view.
  public void show(String imageName, String imagePath) {
    JFrame frame = new JFrame(imageName + " | " + imagePath);
    JLabel label = new JLabel("This is a label");
    JPanel panel = new JPanel();

    panel.add(label);
    frame.setSize(new Dimension(400, 300));
    frame.setVisible(true);
  }
}
