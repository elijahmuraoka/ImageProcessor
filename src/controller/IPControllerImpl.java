package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import commands.ChangeBrightness;
import commands.HorizontalFlip;
import commands.IPCommand;
import commands.VisualiseComp;
import model.IPModel;
import model.IPUtil;
import model.ImageModel;
import view.IPView;

/**
 * An implementation of the Image Processor controller interface used to process user inputs
 * and communicate between the model and view. Specifically, this controller supports and can
 * apply any operation provided from its list of commands. This includes...
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
  // the Readable object which represents the user's inputs
  private final Readable in;
  // a map used to store all current working images
  private final HashMap<String, IPModel> knownImageModels;
  // a map used to store all known Image Processing commands
  private final Map<String, IPCommand> knownCommands;

  /**
   * An Image Processor controller implementation constructor that takes in
   * a view and readable object.
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
    this.knownCommands.put("HorizontalFlip", new HorizontalFlip());
    this.knownCommands.put("Visualise", new VisualiseComp());
  }

  @Override
  public void go() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    boolean quit = false;
    String errorIOMessage = "Error: Invalid input and/or output(s)";
    this.printMenu();
    while (!quit && scan.hasNext()) {
      String userInput;
      try {
        userInput = scan.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Error: There are no more inputs.");
      }
      switch (userInput) {
        case "q":
        case "Q":
          quit = true;
          try {
            this.v.renderMessage("Quitting the Image Processor Application now...");
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);
          }
          break;
        case "menu":
          this.printMenu();
        case "load": {
          try {
            String imageName = scan.next();
            String imagePath = scan.next();
            this.load(imageName, imagePath);
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);
          }
          break;
        }
        case "save": {
          try {
            String imageName = scan.next();
            String imagePath = scan.next();
            this.save(imageName, imagePath);
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);
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
    }
  }

  /**
   * Prints and displays the menu instructions to the user.
   *
   * @throws IllegalStateException when unable to transmit the input(s) and/or output(s) properly
   */
  private void printMenu() throws IllegalStateException {
    try {
      this.v.renderMessage("Welcome to our Image Processor Program!\n"
              + "Press `q` or `Q' to quit the program at any time.\n"
              + "Listed below are some basic commands you can execute on an image:\n\n"
              + "(1) Load and store an image in this application.\n"
              + "    Input Format: load <imageName> <imagePath>\n"
              + "(2) Save any (un)modified images to this device.\n"
              + "    Input Format: save <imageName> <imagePath>\n"
              + "(3) Change the brightness of an image.\n"
              + "    Input Format: ChangeBrightness <imageName> <increment> <destName>\n"
              + "(4) Flip an image horizontally.\n"
              + "    Input Format: HorizontalFlip <imageName> <destName>\n"
              + "(5) Visualise the red, green, or blue component of an image.\n"
              + "    Input Format: Visualise <imageName> <component> <destName>\n"
              + "\nType 'menu' if you would like to see this information again.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: Invalid input and/or output(s)");
    }
  }

  /**
   * Process the user's inputs and apply some command to the given image model
   * as long as the inputs are valid and sufficient.
   * Required: The command input must be contained in the list of known commands
   * Required: The image name input must be recognizable to retrieve its corresponding model
   *
   * @param userInput the user's input to the controller
   * @param scan      the scanner used to read the user's inputs
   * @throws IOException when unable to transmit the input(s) and/or output(s) properly
   */
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
          this.v.renderMessage("Successfully executed the command: " + userInput + "\n");
          this.knownImageModels.put(m.getImageName(), m);
        } catch (IllegalStateException e) {
          this.v.renderMessage(e.getMessage());
        }
      }
    }
  }

  protected String generateFileName(String imageName, String imagePath) {
    return imagePath + "/" + imageName;
  }

  @Override
  public void load(String imageName, String imagePath) throws IOException {
    // how do you verify a correct computer path??
    // read the PPM file passed in
    IPUtil util = new IPUtil();
    util.readPPM(imagePath, this.v);
    // make a copy of the PPM image data in this model
    IPModel m = new ImageModel();
    m.setImageName(imageName);
    m.setWidth(util.getWidth());
    m.setHeight(util.getHeight());
    m.setWorkingImageData(util.getWorkingImageData());
    this.knownImageModels.put(m.getImageName(), m);
  }

  @Override
  public void save(String imageName, String imagePath) throws IOException {
    IPModel m = this.knownImageModels.getOrDefault(imageName, null);
    if (m == null) {
      this.v.renderMessage("This image name is not recognized. Please try again.\n");
    } else {
      String Header = "P3\n" + m.getWidth() + " " + m.getHeight() + "\n255\n";
      StringBuilder imageData = new StringBuilder();
      for (List<int[]> row : m.getWorkingImageData()) {
        for (int[] pixel : row) {
          for (int component : pixel) {
            imageData.append(component).append(" ");
          }
        }
      }
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(this.generateFileName(imageName,
                imagePath)));
        bw.write(Header);
        bw.write(imageData.toString());
        this.v.renderMessage("Successfully saved " + imageName + " to " + imagePath + "\n");
        bw.close();
      } catch (IOException e) {
        this.v.renderMessage("Invalid file path. Please input new values and try again.\n");
      }
    }
  }
}
