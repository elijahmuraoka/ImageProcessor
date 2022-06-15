package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import Utils.ImageFactory;
import Utils.ImageFile;
import commands.ChangeBrightness;
import commands.GreyScale;
import commands.HorizontalFlip;
import commands.IPCommand;
import commands.VerticalFlip;
import model.IPModel;
import model.ImageModel;
import view.IPView;

/**
 * An implementation of the Image Processor controller interface used to process user inputs
 * and communicate between the model and view. Specifically, this controller supports and can
 * apply any operation provided from its list of commands. This includes:
 * - Loading an image.
 * - Saving an image.
 * - Visualizing a specific RGB component of an image.
 * - Visualizing value, intensity, or luma of an image.
 * - Flip an image horizontally or vertically.
 * - Brightening an image.
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
   * @param v  an Image Processor view.
   * @param in a Readable object.
   * @throws IllegalArgumentException when either the model and/or Readable object are null.
   */
  public IPControllerImpl(IPView v, Readable in) throws IllegalArgumentException {
    if (v == null || in == null) {
      throw new IllegalArgumentException("Either the model, view, and/or "
              + "readable object(s) are null.\nPlease try new valid parameters.\n");
    }
    this.v = v;
    this.in = in;
    this.knownImageModels = new HashMap<>();
    this.knownCommands = new HashMap<>();
    this.knownCommands.put("ChangeBrightness", new ChangeBrightness());
    this.knownCommands.put("cb", new ChangeBrightness());
    this.knownCommands.put("HorizontalFlip", new HorizontalFlip());
    this.knownCommands.put("flip-h", new HorizontalFlip());
    this.knownCommands.put("VerticalFlip", new VerticalFlip());
    this.knownCommands.put("flip-v", new VerticalFlip());
    this.knownCommands.put("GreyScale", new GreyScale());
    this.knownCommands.put("gs", new GreyScale());
    this.knownCommands.put("gs-red", new GreyScale("red"));
    this.knownCommands.put("gs-blue", new GreyScale("blue"));
    this.knownCommands.put("gs-green", new GreyScale("green"));
    this.knownCommands.put("gs-value", new GreyScale("value"));
    this.knownCommands.put("gs-intensity", new GreyScale("intensity"));
    this.knownCommands.put("gs-luma", new GreyScale("luma"));
  }

  @Override
  public void run() throws IllegalStateException {
    Scanner scan = new Scanner(this.in);
    boolean quit = false;
    String errorIOMessage = "Error: Invalid input and/or output(s)";
    this.printHelpMenu();
    while (!quit && scan.hasNext()) {
      String userInput;
      try {
        userInput = scan.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Error: There are no more inputs.");
      }
      switch (userInput.toLowerCase()) {
        case "q":
          quit = true;
          try {
            this.v.renderMessage("Quitting the Image Processor Application now...");
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);
          }
          break;
        case "help":
        case "menu":
          this.printHelpMenu();
          break;
        case "commands":
          this.printCommands();
          break;
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
            String saveAsName = scan.next();
            String imagePath = scan.next();
            String extension = scan.next();
            this.save(imageName, saveAsName, imagePath, extension);
          } catch (IOException e) {
            throw new IllegalStateException(errorIOMessage);//break;
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
   * Prints and displays both a welcome message and a help menu for the user to reference whenever
   * they would like.
   *
   * @throws IllegalStateException when unable to transmit the input(s) and/or output(s) properly
   */
  private void printHelpMenu() throws IllegalStateException {
    try {
      this.v.renderMessage("Welcome to our Image Processor Program!\n"
              + "Press `q` or 'Q' to quit the program at any time.\n"
              + "Type 'commands' to see the available list of image processing commands.\n"
              + "Type 'help' or 'menu' if you would like to see this information again.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: Invalid input and/or output(s)");
    }
  }

  /**
   * Prints and displays an extensive list of available commands for the user to use when
   * editing and processing their images.
   *
   * @throws IllegalStateException when unable to transmit the input(s) and/or output(s) properly
   */
  private void printCommands() throws IllegalStateException {
    try {
      this.v.renderMessage("Listed below are some basic commands you can execute on an image:\n\n"
              + "(1) Load and store an image in this application.\n"
              + "    Input Format Example(s):\n"
              + "       load <imageName> <imagePath>\n"
              + "(2) Save any (un)modified images to this device.\n"
              + "    Input Format Example(s):\n"
              + "       save <imageName> <saveAsName> <saveLocation> <extension> (jpg, png, etc.)\n"
              + "(3) Change the brightness of an image.\n"
              + "    Input Format Example(s):\n"
              + "       ChangeBrightness <imageName> <increment> <destName>\n"
              + "       cb <imageName> <increment> <destName>\n"
              + "(4) Flip an image horizontally.\n"
              + "    Input Format Example(s):\n"
              + "       HorizontalFlip <imageName> <destName>\n"
              + "       flip-h <imageName> <destName>\n"
              + "(4) Flip an image vertically.\n"
              + "    Input Format Example(s):\n"
              + "       VerticalFlip <imageName> <destName>\n"
              + "       flip-v <imageName> <destName>\n"
              + "(5) Create a greyscale that visualizes the red, green, blue,\n"
              + "    value, intensity, or luma component of an image.\n"
              + "    Input Format Example(s):\n"
              + "       Greyscale <imageName> <visType> <destName>\n"
              + "       gs <imageName> <visType> <destName>\n"
              + "       gs-red <imageName> <destName>\n"
              + "       gs-blue <imageName> <destName>\n"
              + "       gs-green <imageName> <destName>\n"
              + "       gs-value <imageName> <destName>\n"
              + "       gs-intensity <imageName> <destName>\n"
              + "       gs-luma <imageName> <destName>\n");
    } catch (IOException e) {
      throw new IllegalStateException("Error: Invalid input and/or output(s)");
    }
  }

  /**
   * Process the user's inputs and apply some command to the given image model
   * as long as the inputs are valid and sufficient.
   * Required: The command input must be contained in the list of known commands.
   * Required: The image name input must be recognizable to retrieve its corresponding model.
   *
   * @param userInput the user's input to the controller.
   * @param scan      the scanner used to read the user's inputs.
   * @throws IOException when unable to transmit the input(s) and/or output(s) properly.
   */
  private void processCommand(String userInput, Scanner scan) throws IOException {
    //System.out.println(this.knownImageModels);
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
          // this.v.renderMessage("Editing " + imageName + " now...\n");
          IPModel newM = m;
          newM = cmd.execute(newM, scan);
          this.v.renderMessage("Successfully executed the command: " + userInput + "\n");
          this.knownImageModels.put(newM.getImageName(), newM);
        } catch (IllegalStateException e) {
          this.v.renderMessage(e.getMessage());
        }
      }
    }
  }

  @Override
  public void load(String imageName, String imagePath) throws IOException {
    try {
      this.v.renderMessage("Currently loading image...\n");
      IPModel m = new ImageModel();
      // read the image file passed in
      ImageFactory factory = new ImageFactory(imagePath);
      ImageFile i = factory.createImageFile();
      i.read(this.v);
      // make a copy of the image data in this model
      m.setImageName(imageName);
      m.setWidth(i.getWidth());
      m.setHeight(i.getHeight());
      m.setWorkingImageData(i.getWorkingImageData());
      this.knownImageModels.put(m.getImageName(), m);
    } catch (IllegalStateException e) {
      this.v.renderMessage(e.getMessage());
    }
  }

  @Override
  public void save(String imageName, String saveAsName, String imagePath, String extension) throws IOException {
    IPModel m = this.knownImageModels.getOrDefault(imageName, null);
    if (m == null) {
      this.v.renderMessage("The image name, " + imageName
              + ", is not recognized. Please try again.\n");
    } else {
      this.v.renderMessage("Saving image to " + imagePath + " now...\n");
      String header = "P3\n" + m.getWidth() + " " + m.getHeight() + "\n255\n";
      StringBuilder imageData = new StringBuilder();
      for (List<int[]> row : m.getWorkingImageData()) {
        for (int[] pixel : row) {
          for (int component : pixel) {
            imageData.append(component).append(" ");
          }
        }
      }
      BufferedWriter bw = null;
      try {
        ImageFactory factory = new ImageFactory("." + extension);
        ImageFile i = factory.createImageFile();
        bw = new BufferedWriter(new FileWriter(
                i.generateFileName(saveAsName, imagePath)));
      } catch (IllegalStateException e) {
        this.v.renderMessage(e.getMessage());
        return;
      }
      try {
        Objects.requireNonNull(bw);
        bw.write(header);
        bw.write(imageData.toString());
        this.v.renderMessage("Successfully saved " + saveAsName + " to " + imagePath
                + " as a " + extension + " image!\n");
        bw.close();
      } catch (IOException e) {
        this.v.renderMessage("Invalid file path. Please input new values and try again.\n");
      }
    }
  }
}
