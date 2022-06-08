package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import commands.ChangeBrightness;
import commands.IPCommand;
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
  private final HashMap<String, IPModel> knownImageModels;
  private final Map<String, IPCommand> knownCommands;

  /**
   * An Image Processor controller implementation constructor that takes in a model, view, and
   * readable object.
   *
   * @param v  an Image Processor view
   * @param in a Readable object
   * @throws IllegalArgumentException when either the model and/or Readable object are null
   */
  public IPControllerImpl(IPView v, Readable in) throws IllegalArgumentException {
    if (v == null || in == null) {
      throw new IllegalArgumentException("Either the model, view, and/or " +
              "readable object(s) are null.\nPlease try new valid parameters.\n");
    }
    this.v = v;
    this.in = in;
    this.knownImageModels = new HashMap<>();
    this.knownCommands = new HashMap<>();
    this.knownCommands.put("ChangeBrightness", new ChangeBrightness());
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
    m.setImageName(imageName);
    m.setWidth(util.getWidth());
    m.setHeight(util.getHeight());
    m.setWorkingImageData(util.getWorkingImageData());
    this.knownImageModels.put(m.getImageName(), m);
  }

  @Override
  public void save(String imageName, String imagePath) throws IllegalArgumentException {
    IPModel m = this.knownImageModels.getOrDefault(imageName, null);
    if (m == null) {
      throw new IllegalArgumentException("This image name is not recognized. Please try again.\n");
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

  @Override
  public void go() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    boolean quit = false;
    String errorIOMessage = "Error: Invalid input and/or output(s)";
    this.printMenu();
    while (!quit && scan.hasNext()) {
      String userInput = scan.next();
      switch (userInput) {
        case "q":
        case "Q":
          quit = true;
          break;
        case "menu":
          this.printMenu();
        case "load": {
          try {
            String imageName = scan.next();
            String imagePath = scan.next();
            this.load(imageName, imagePath);
          } catch (IllegalArgumentException e1) {
            try {
              this.v.renderMessage(e1.getMessage());
            } catch (IOException e2) {
              throw new IllegalStateException(errorIOMessage);
            }
          }
          break;
        }
        case "save": {
          try {
            String imageName = scan.next();
            String imagePath = scan.next();
            this.save(imageName, imagePath);
            try {
              this.v.renderMessage("Successfully saved "
                      + imageName + " to " + imagePath + "!\n");
            } catch (IOException e) {
              throw new IllegalStateException(errorIOMessage);
            }
          } catch (IllegalArgumentException e1) {
            try {
              this.v.renderMessage(e1.getMessage());
            } catch (IOException e2) {
              throw new IllegalStateException(errorIOMessage);
            }
          }
          break;
        }
        default:
          try {
            this.processCommand(userInput, scan);
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);
          }
          break;
      }
//      if (userInput.equalsIgnoreCase("q")) {
//        quit = true;
//        // this.printFarewellMessage
//      } else if (userInput.equalsIgnoreCase("menu")) {
//        this.printMenu();
//      }
//      else {
//        this.processCommand(userInput, scan);
//      }
    }
  }

  private void printMenu() throws IllegalStateException {
    try {
      this.v.renderMessage("Welcome to our Image Processor Program!\n"
              + "Listed below are some basic commands you can execute on an image\n"
              + "(1) ChangeBrightness <imageName> <increment> <destName>\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: Invalid input and/or output(s)");
    }
  }

  private void processCommand(String userInput, Scanner scan) throws IOException {
    IPModel m;
    IPCommand cmd = this.knownCommands.getOrDefault(userInput, null);
    if (cmd == null) {
      this.v.renderMessage("Not a valid command. Please try again.\n");
    } else {
      String imageName = scan.next();
      m = this.knownImageModels.getOrDefault(imageName, null);
      if (m == null) {
        this.v.renderMessage("This image name is not recognized. Please try again.\n");
      } else {
        try {
          m = cmd.execute(m, scan);
          this.v.renderMessage("Sucessfully executed the command: " + userInput + "\n");
          this.knownImageModels.put(m.getImageName(), m);
        } catch (IllegalStateException e) {
          this.v.renderMessage(e.getMessage());
        }
      }
    }
  }
}
