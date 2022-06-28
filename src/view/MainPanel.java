package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import actionListeners.LoadActionListener;
import controller.BetterIPController;
import model.BetterIPModel;

/**
 * The main panel class used for loading, editing, and saving images altogether.
 * This main panel has the capability to interact and execute all commands within the command
 * package as seen through its east panel and various buttons. It can also load and store
 * multiple images so the user can work on multiple at once. A key feature of this class
 * is the ability to call the undo method through this class's menu bar as well.
 */
public class MainPanel extends JPanel {
  private final int width;
  private final int height;
  private BetterIPModel model;
  private final JPanel southPanel;
  private JProgressBar progressBar;
  private final JPanel loadedImagesPanel;
  private final JPanel eastPanel;
  private final JPanel westPanel;
  private final JPanel centerPanel;
  private final JLabel mainImgLabel;
  private final IPGuiView v;
  private final BetterIPController c;

  /**
   * The Main Panel constructor used to generate a standard main panel object to produce the
   * desired editing view.
   * @param width the width of the main panel
   * @param height the height of the main panel
   * @param v the view for this main panel
   * @param c the controller which this class communicates with
   */
  public MainPanel(int width, int height, IPGuiView v, BetterIPController c) {
    this.width = width;
    this.height = height;
    this.v = v;
    this.c = c;
    this.mainImgLabel = new JLabel();
    this.setPreferredSize(new Dimension(this.width, this.height));
    this.setBackground(Color.BLACK);
    this.setLayout(new BorderLayout(this.width / 360, this.height / 300));

    //    // create the north panel
    //    this.northPanel = new JPanel();
    //    this.northPanel.setPreferredSize(new Dimension(this.width,
    //            this.height / 10));
    //    this.northPanel.setBackground(Color.PINK);
    //    this.northPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    //    this.northPanel.setLayout(new GridBagLayout());
    //    this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    //    JLabel title = new JLabel("Image Processor Application");
    //    title.setFont(new Font("Plain", Font.BOLD, this.height / 20));
    //    title.setHorizontalAlignment(JLabel.CENTER);
    //    title.setVerticalAlignment(JLabel.CENTER);
    //    title.setBorder(BorderFactory.createLineBorder(Color.RED));
    //    title.setBackground(Color.WHITE);
    //    title.setOpaque(true);
    //    this.northPanel.add(title);
    //    this.add(this.northPanel, BorderLayout.NORTH);
    //    this.northPanel.setLayout(new GridBagLayout());
    this.createMenuBar();

    // create the center panel
    this.centerPanel = new JPanel();
    this.centerPanel.setPreferredSize(new Dimension(this.width * 3 / 5,
            this.height * 3 / 5));
    this.centerPanel.setBackground(Color.LIGHT_GRAY);
    this.centerPanel.setBorder(BorderFactory.createTitledBorder("Preview"));
    this.centerPanel.setLayout(new GridBagLayout());
    this.add(this.centerPanel, BorderLayout.CENTER); // add the center panel

    // create the south panel
    this.southPanel = new JPanel();
    this.southPanel.setPreferredSize(new Dimension(this.width, this.height / 5));
    this.southPanel.setBackground(Color.DARK_GRAY);
    this.southPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    this.southPanel.setLayout(new BorderLayout());
    this.loadedImagesPanel = new JPanel(new GridLayout(1, 0, this.width / 200,
            this.height / 80));
    this.southPanel.add(this.loadedImagesPanel, BorderLayout.CENTER);
    JLabel loadedImagesLabel = new JLabel("Loaded Images Gallery:");
    loadedImagesLabel.setForeground(Color.WHITE);
    loadedImagesLabel.setFont(new Font("Plain", Font.BOLD, this.height / 50));
    loadedImagesLabel.setHorizontalAlignment(JLabel.CENTER);
    this.southPanel.add(loadedImagesLabel, BorderLayout.PAGE_START);
    this.add(this.southPanel, BorderLayout.SOUTH);

    this.createProgressBar();

    // create the west panel
    this.westPanel = new JPanel();
    this.westPanel.setPreferredSize(new Dimension(270, this.height));
    this.westPanel.setBackground(Color.DARK_GRAY);
    this.westPanel.setBorder(this.createTitledBorder("Histograms"));
    this.add(this.westPanel, BorderLayout.WEST);
    this.westPanel.setLayout(new GridLayout(0, 1));

    // create the east panel
    this.eastPanel = new JPanel();
    this.eastPanel.setPreferredSize(new Dimension(this.width / 5, this.height));
    this.eastPanel.setBackground(Color.DARK_GRAY);
    this.eastPanel.setBorder(this.createTitledBorder("Edit"));
    this.add(this.eastPanel, BorderLayout.EAST);
    this.eastPanel.setLayout(new GridLayout(0, 1));
    this.makeCommandButtons();
  }

  // Creates a titled border given a specific title string.
  private TitledBorder createTitledBorder(String title) {
    TitledBorder westBorder = new TitledBorder(title);
    westBorder.setTitleColor(Color.WHITE);
    return westBorder;
  }

  // Creates the progress bar that is added at the bottom of the page.
  // STILL IN WORKS
  private void createProgressBar() {
    this.progressBar = new JProgressBar();
    this.progressBar.setValue(0);
    this.progressBar.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    this.progressBar.setFont(new Font("Plain", Font.BOLD, this.height / 50));
    this.progressBar.setString("Image Processing Bar");
    this.progressBar.setBorderPainted(true);
    this.progressBar.setStringPainted(true);
    this.southPanel.add(this.progressBar, BorderLayout.PAGE_END);
  }

  // *Temporary* helper method to set the progress bar to a specific amount with
  // the "Loading..." string, letting the client know that the application is not finished
  // doing its delegated tasks yet.
  // This method also sets a wait cursor when running slower commands.
  private void loadingProgressBar() {
    this.progressBar.setValue(50);
    this.progressBar.setString("Loading...");
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  }

  // *Temporary* helper method to set the progress bar to full, with
  // the "Done!" string, letting the client know that the application has finished
  // doing its delegated tasks.
  // This method also sets a wait cursor back to its default settings.
  private void completeProgressBar() {
    progressBar.setValue(100);
    progressBar.setString("Done!");
    setCursor(Cursor.getDefaultCursor());
  }

  // Creates the menu bar at the top of the page
  // Contains 4 key menu items...
  // - help
  // - load
  // - undo
  // - save
  private void createMenuBar() throws IllegalStateException {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu"); // create a menu page
    JMenuItem help = new JMenuItem("help");
    help.addActionListener(e -> {
      try {
        this.v.renderMessage("Help Menu:\n"
                + "---------------------------------------------------------------------------\n"
                + "HOW TO LOAD AN IMAGE\n"
                + "     1. Click on the 'menu' icon in the top left corner\n"
                + "     2. Click on the 'load' item\n"
                + "     3. Select the image you would like to use "
                + "(must load a .ppm, .jpg, .png, or .bmp file)\n"
                + "HOW TO EDIT AN IMAGE\n"
                + "     1. Navigate to the east (right) panel titled 'Edit'\n"
                + "     NOTE: All labeled buttons will automatically call the command once "
                + "clicked\n"
                + "                Set any sliders and/or select options beforehand\n"
                + "     2. Click on the desired action button\n"
                + "     3. Wait for the program to make its changes on the main image displayed\n"
                + "HOW TO UNDO\n"
                + "     1. Click on the 'menu' icon in the top left corner\n"
                + "     2. Click on the 'undo' item\n"
                + "     3. Wait for the program to make its changes on the main image displayed\n"
                + "HOW TO SAVE AN IMAGE\n"
                + "     1. Click on the 'menu' icon in the top left corner\n"
                + "     2. Click on the 'save' item\n"
                + "     3. Enter a valid name "
                + "(must add a .ppm, .jpg, .png, or .bmp file extension at the end)\n"
                + "     4. Select the desired destination to save your image");
      } catch (IOException ex) {
        throw new IllegalStateException(ex.getMessage());
      }
    });
    JMenuItem load = new JMenuItem("load");
    load.addActionListener(new LoadActionListener(this.c, this));
    JMenuItem save = new JMenuItem("save");
    save.addActionListener(e -> {
      if (this.model != null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose the image you would like to save!");
        int saveDialogue = fileChooser.showSaveDialog(this);
        if (saveDialogue == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          String imagePath = file.getAbsolutePath();
          try {
            this.c.save(this.model.getImageName(), imagePath,
                    imagePath, null);
          } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage());
          }
        }
      }
    });
    JMenuItem undo = new JMenuItem("undo");
    undo.addActionListener(e -> {
      System.out.println("Attempting to Undo...");
      BetterIPModel current = this.c.getKnownImageModels().get(this.model.getImageName());
      this.loadingProgressBar();
      if (current.getUndoCounter().size() == 0) {
        this.c.setReadable(new StringReader("undo " + current.getImageName() + " "));
        this.c.run();
      } else {
        int undoCounter = current.getUndoCounter().remove(
                current.getUndoCounter().size() - 1);
        while (undoCounter > 0) {
          System.out.println("Undoing Change: " + current.getUndoCounter());
          this.c.setReadable(new StringReader("undo " + current.getImageName()));
          this.c.run();
          undoCounter--;
        }
      }
      this.c.setReadable(current.make());
      this.c.run();
      this.completeProgressBar();
    });
    menu.add(help);
    menu.add(load);
    menu.add(undo);
    menu.add(save);
    menuBar.add(menu); // add the menu page to the bar
    this.add(menuBar, BorderLayout.PAGE_START);
  }

  private void makeCommandButtons() {
    // add ChangeBrightness Panel
    // (consisting of the button and slider)
    JPanel cbPanel = new JPanel();
    cbPanel.setLayout(new GridLayout(0, 1));
    JButton cbButton = new JButton("Change Brightness");
    cbPanel.add(cbButton);
    JSlider cbSlider = new JSlider(-100, 100);
    cbSlider.setMajorTickSpacing(25);
    cbSlider.setMinorTickSpacing(5);
    cbSlider.setPaintTicks(true);
    cbSlider.setPaintLabels(true);
    cbPanel.add(cbSlider);
    cbButton.addActionListener(e -> {
      this.loadingProgressBar();
      BetterIPModel current = this.c.getKnownImageModels().get(this.model.getImageName());
      current.addToUndoCounter(1);
      String percentage = Integer.toString(cbSlider.getValue());
      String cbCommand = "cb "
              + current.getImageName() + " "
              + percentage + " "
              + current.getImageName();
      current.addToCommandList(cbCommand);
      this.c.setReadable(current.make());
      this.c.run();
      this.completeProgressBar();
    });
    this.eastPanel.add(cbPanel);

    // add Blur Panel
    // (consisting of the button and slider)
    JPanel blurPanel = new JPanel();
    blurPanel.setLayout(new GridLayout(0, 1));
    JButton blurButton = new JButton("Blur");
    blurPanel.add(blurButton);
    JSlider blurSlider = new JSlider(SwingConstants.HORIZONTAL, 10);
    blurSlider.setMajorTickSpacing(2);
    blurSlider.setMinorTickSpacing(1);
    blurSlider.setPaintTicks(true);
    blurSlider.setPaintLabels(true);
    blurPanel.add(blurSlider);
    blurButton.addActionListener(e -> {
      this.loadingProgressBar();
      Map<String, BetterIPModel> knownImgModels = this.c.getKnownImageModels();
      BetterIPModel current = knownImgModels.get(this.model.getImageName());
      int layers = blurSlider.getValue();
      String blurCommand = "blur " +
              current.getImageName() + " " +
              current.getImageName() + " ";
      int undoCounter = 0;
      for (int i = 0; i < layers; i += 1) {
        knownImgModels.get(this.model.getImageName()).addToCommandList(blurCommand);
        undoCounter++;
      }
      current.addToUndoCounter(undoCounter);
      this.c.setReadable(this.model.make());
      this.c.run();
      this.completeProgressBar();
    });
    this.eastPanel.add(blurPanel);

    // add Sharpen Panel
    // (consisting of the button and slider)
    JPanel sharpenPanel = new JPanel();
    sharpenPanel.setLayout(new GridLayout(0, 1));
    JButton sharpenButton = new JButton("Sharpen");
    sharpenPanel.add(sharpenButton);
    JSlider sharpenSlider = new JSlider(SwingConstants.HORIZONTAL, 10);
    sharpenSlider.setMajorTickSpacing(2);
    sharpenSlider.setMinorTickSpacing(1);
    sharpenSlider.setPaintTicks(true);
    sharpenSlider.setPaintLabels(true);
    sharpenPanel.add(sharpenSlider);
    sharpenButton.addActionListener(e -> {
      this.loadingProgressBar();
      Map<String, BetterIPModel> knownImgModels = this.c.getKnownImageModels();
      BetterIPModel current = knownImgModels.get(this.model.getImageName());
      int layers = blurSlider.getValue();
      String sharpenCommand = "sharpen " +
              current.getImageName() + " " +
              current.getImageName() + " ";
      int undoCounter = 0;
      for (int i = 0; i < layers; i += 1) {
        undoCounter++;
        knownImgModels.get(this.model.getImageName()).addToCommandList(sharpenCommand);
      }
      current.addToUndoCounter(undoCounter);
      this.c.setReadable(this.model.make());
      this.c.run();
      this.completeProgressBar();
    });
    this.eastPanel.add(sharpenPanel);

    // add GreyScale Panel
    JPanel gsPanel = new JPanel();
    JButton gsButton = new JButton("Grey Scale");
    String[] gsOptions = {"--None--", "Red", "Green", "Blue", "Luma", "Intensity", "Value"};
    JComboBox<String> gsComboBox = new JComboBox<>(gsOptions);
    gsPanel.setLayout(new GridLayout(0, 1));
    gsButton.addActionListener(e -> {
      if (!Objects.requireNonNull(gsComboBox.getSelectedItem()).toString().equals("--None--")) {
        this.loadingProgressBar();
        BetterIPModel current = this.c.getKnownImageModels().get(this.model.getImageName());
        current.addToUndoCounter(1);
        String gsCommand = "gs " + current.getImageName() + " "
                + gsComboBox.getSelectedItem().toString() + " "
                + current.getImageName();
        current.addToCommandList(gsCommand);
        this.c.setReadable(this.model.make());
        this.c.run();
        this.completeProgressBar();
      }
    });
    gsPanel.add(gsButton);
    gsPanel.add(gsComboBox);
    this.eastPanel.add(gsPanel);

    // add Sepia Command Button
    JButton sepiaButton = new JButton("Sepia");
    sepiaButton.addActionListener(e -> {
      this.loadingProgressBar();
      BetterIPModel current = this.c.getKnownImageModels().get(this.model.getImageName());
      current.addToUndoCounter(1);
      String sepiaCommand = "sepia"
              + " " + current.getImageName()
              + " " + current.getImageName();
      current.addToCommandList(sepiaCommand);
      this.c.setReadable(current.make());
      this.c.run();
      this.completeProgressBar();
    });
    this.eastPanel.add(sepiaButton);

    // add Flip Panel
    JPanel flipPanel = new JPanel();
    JButton flipButton = new JButton("Flip");
    String[] flipOptions = {"--None--", "vertical", "horizontal"};
    JComboBox<String> flipComboBox = new JComboBox<>(flipOptions);
    flipPanel.setLayout(new GridLayout(0, 1));
    flipButton.addActionListener(e -> {
      String optionSelected = Objects.requireNonNull(flipComboBox.getSelectedItem()).toString();
      if (!optionSelected.equals("--None--")) {
        this.loadingProgressBar();
        BetterIPModel current = this.c.getKnownImageModels().get(this.model.getImageName());
        current.addToUndoCounter(1);
        String flipCommand = optionSelected + " " + current.getImageName()
                + " " + current.getImageName();
        current.addToCommandList(flipCommand);
        this.c.setReadable(this.model.make());
        this.c.run();
        this.completeProgressBar();
      }
    });
    flipPanel.add(flipButton);
    flipPanel.add(flipComboBox);
    this.eastPanel.add(flipPanel);
  }

  // Renders all the current model's histograms and displays them on the west panel
  private void createHistograms() {
    JPanel allHistograms = new JPanel(new GridLayout(0, 1));
    Map<Integer, Integer> redHistogram = this.model.generateHistograms().get(0);
    Map<Integer, Integer> greenHistogram = this.model.generateHistograms().get(1);
    Map<Integer, Integer> blueHistogram = this.model.generateHistograms().get(2);
    Map<Integer, Integer> intensityHistogram = this.model.generateHistograms().get(3);
    int hWidth = this.westPanel.getWidth();
    System.out.println(hWidth);
    int hHeight = this.height / 6;
    //    for (Map.Entry<Integer, Integer> entry : redHistogram.entrySet()) {
    //      System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
    //    }
    Color redBackground = new Color(255, 225, 225);
    JPanel redHistoPanel = new Histogram(hHeight, Color.RED, redBackground, redHistogram);
    redHistoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //redHistoPanel.setPreferredSize(new Dimension(hWidth, hHeight));
    Color greenBackground = new Color(225, 255, 225);
    JPanel greenHistoPanel = new Histogram(hHeight, Color.GREEN, greenBackground, greenHistogram);
    greenHistoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //greenHistoPanel.setPreferredSize(new Dimension(hWidth, hHeight));
    Color blueBackground = new Color(225, 225, 255);
    JPanel blueHistoPanel = new Histogram(hHeight, Color.BLUE, blueBackground, blueHistogram);
    blueHistoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //blueHistoPanel.setPreferredSize(new Dimension(hWidth, hHeight));
    Color orangeBackground = new Color(255, 235, 225);
    JPanel intensityHistoPanel = new Histogram(hHeight, Color.ORANGE, orangeBackground,
            intensityHistogram);
    intensityHistoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //intensityHistoPanel.setPreferredSize(new Dimension(hWidth, hHeight));
    redHistoPanel.repaint();
    greenHistoPanel.repaint();
    blueHistoPanel.repaint();
    intensityHistoPanel.repaint();
    allHistograms.add(redHistoPanel);
    allHistograms.add(greenHistoPanel);
    allHistograms.add(blueHistoPanel);
    allHistograms.add(intensityHistoPanel);
    allHistograms.setBackground(Color.DARK_GRAY);
    this.westPanel.add(allHistograms);
  }

  /**
   * This method is used to set the main image model of the current view
   *
   * @param model the model to be prioritized and edited
   */
  public void setMainImage(BetterIPModel model) {
    this.model = model;
  }

  /**
   * Turns the current main image model into an image display which a user can apply changes to and
   * visually see the results.
   *
   * @throws IOException when the message is unable to render/transmit properly.
   */
  public void showMainImageModel() throws IOException {
    this.centerPanel.setBorder(BorderFactory.createTitledBorder("Preview: "
            + this.model.getImageName()));
    System.out.println("Show Main Image Model Method: " + this.model.getImageName());
    try {
      BufferedImage mainImg = new BufferedImage(this.model.getWidth(), this.model.getHeight(),
              BufferedImage.TYPE_INT_ARGB);
      for (int i = 0; i < this.model.getHeight(); i++) {
        for (int j = 0; j < this.model.getWidth(); j++) {
          int[] pixel = this.model.getWorkingImageData().get(i).get(j);
          int r = pixel[0];
          int g = pixel[1];
          int b = pixel[2];
          mainImg.setRGB(j, i, new Color(r, g, b).getRGB());
        }
      }
      double widthToHeight = 1.0 * this.model.getWidth() / this.model.getHeight();
      model.setImage(mainImg);
      int newHeight = this.height * 3 / 5;
      int newWidth = (int) (newHeight * widthToHeight);
      Image mainImgScaled = mainImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      this.mainImgLabel.setIcon(new ImageIcon(mainImgScaled));
      // this.mainImgLabel.setText("Current Image: " + model.getImageName());
      this.mainImgLabel.setVerticalTextPosition(JLabel.BOTTOM);
      this.mainImgLabel.setHorizontalAlignment(JLabel.CENTER);
      this.centerPanel.add(mainImgLabel);
    } catch (IllegalArgumentException e) {
      this.v.renderMessage("Unable to read this file. Please try again.");
      return;
    }
    //    JScrollPane scrollMainImage = new JScrollPane(this.centerPanel,
    //            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    //            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //    this.add(scrollMainImage, BorderLayout.CENTER);

    this.westPanel.removeAll();
    this.createHistograms();
  }

  /**
   * Shows all loaded images other than the current one at the bottom of the screen to allow the
   * client to work on and edit multiple images at once from the gallery.
   */
  public void showLoadedImages() {
    this.loadedImagesPanel.removeAll();
    Objects.requireNonNull(this.model);
    for (Map.Entry<String, BetterIPModel> entry : this.c.getKnownImageModels().entrySet()) {
      if (!Objects.equals(entry.getKey(), this.model.getImageName())) {
        System.out.println("LoadedImageKey =" + entry.getKey() +
                ", Value = " + entry.getValue());
        System.out.println("Current Model Name:" + model.getImageName());
        this.addLoadedImage(entry.getValue());
      }
    }
    //    JScrollPane scrollLoaded = new JScrollPane(this.loadedImagesPanel,
    //            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
    //            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    //    this.southPanel.add(scrollLoaded);
  }

  // Adds a single model and its image display to the current loaded image gallery.
  private void addLoadedImage(BetterIPModel m) {
    Image image = m.getImage();
    double widthToHeight = 1.0 * m.getWidth() / m.getHeight();
    int newHeight = this.height / 8;
    int newWidth = (int) (newHeight * widthToHeight);
    Image imageScaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    JButton loadedImg = new JButton();
    loadedImg.setPreferredSize(new Dimension(newWidth + 10, newHeight + 10));
    loadedImg.setIcon(new ImageIcon(imageScaled));
    loadedImg.addActionListener(e -> {
      this.setMainImage(m);
      this.v.refresh();
    });
    this.loadedImagesPanel.add(loadedImg);
  }
}
