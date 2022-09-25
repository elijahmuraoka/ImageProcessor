package view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import actionListeners.LoadActionListener;
import controller.BetterIPController;
import model.BetterIPModel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * A simple GUI view class that implements the IPGuiView interface. This view
 * allows for an easy switch between the welcome page and the main page.
 * It is the main class used to refresh as well when changes are made.
 */
public class SimpleGuiView implements IPGuiView {
  private final int baseWidth;
  private final int baseHeight;
  private final JFrame base;
  private JPanel basePanel;
  private CardLayout baseLayout;
  private final JPanel welcome;
  private MainPanel main;

  private BetterIPController c;

  /**
   * An empty constructor used to create a SimpleGuiView object.
   * Must initialize the controller immediately after using this view.
   */
  public SimpleGuiView() {
    this.baseWidth = 1600;
    this.baseHeight = 1100;
    this.base = new JFrame();
    this.welcome = new JPanel();
  }


  @Override
  public void initialize() {
    // can initialize main panel after the controller is set
    this.main = new MainPanel(baseWidth, baseHeight, this, this.c);
    // set the base JFrame initial settings
    this.basePanel = new JPanel();
    this.base.setPreferredSize(new Dimension(baseWidth, baseHeight));
    this.basePanel.setPreferredSize(new Dimension(baseWidth, baseHeight));
    this.base.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.base.setTitle("ImageProcessor");

    // set the layout manager on basePanel to switch between panels
    baseLayout = new CardLayout();
    this.basePanel.setLayout(this.baseLayout);
    this.base.add(this.basePanel);

    // create the Welcome JPanel
    this.welcome.setPreferredSize(new Dimension(baseWidth, baseHeight));
    this.welcome.setBackground(Color.DARK_GRAY);
    this.welcome.setLayout(new GridBagLayout());
    String fontType = "Roboto";

    JLabel welcomeMessage = new JLabel("Welcome to Elijah's Image Processor Program!");
    welcomeMessage.setFont(new Font(fontType, Font.ITALIC + Font.BOLD, baseHeight / 30));
    welcomeMessage.setPreferredSize(welcomeMessage.getPreferredSize());

    JLabel helpMessage = new JLabel("<html>(Please load in either a PNG, JPG, BMP, or PPM image,<br>" +
            "and keep in mind that larger image files may be slow to load & edit)</html>");
    helpMessage.setFont(new Font(fontType, Font.BOLD, baseHeight / 60));
    helpMessage.setPreferredSize(helpMessage.getPreferredSize());
    helpMessage.setHorizontalAlignment(SwingConstants.CENTER);

    JButton loadButton = new JButton("Click Here to Load an Image");
    loadButton.setFont(new Font(fontType, Font.PLAIN, baseHeight / 30));
    loadButton.setPreferredSize(new Dimension(baseHeight / 35, baseHeight / 25));
    loadButton.addActionListener(new LoadActionListener(this.c, this.welcome));
    //loadButton.setBorder(new EmptyBorder(10, 0, 10, 0));

    //    Image welcomeImage = null;
    //    try {
    //      welcomeImage = ImageIO.read(new File("images/bucketboi.jpg"));
    //    } catch (IOException e) {
    //      this.renderMessage("Error: Welcome Image Not Found.");
    //    }
    //    Objects.requireNonNull(welcomeImage);
    //    Image welcomeImageScaled = welcomeImage.getScaledInstance(baseWidth / 6,
    //            baseHeight / 5, Image.SCALE_SMOOTH);
    //    ImageIcon welcomeImageIcon = new ImageIcon(welcomeImageScaled);
    JPanel centerWelcome = new JPanel();
    centerWelcome.setBackground(Color.LIGHT_GRAY);
    centerWelcome.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    centerWelcome.setBorder(new EmptyBorder(20, baseWidth / 5, 20, baseWidth / 5));
    centerWelcome.setLayout(new GridLayout(3, 1, 0, baseHeight / 25));
    centerWelcome.add(welcomeMessage);
    centerWelcome.add(helpMessage);
    centerWelcome.add(loadButton);
    this.welcome.add(centerWelcome);

    // add the welcome and main JPanels
    this.basePanel.add(this.welcome, "welcome");
    this.basePanel.add(this.main, "main");

    // finalize and show everything in the view
    this.baseLayout.show(this.basePanel, "welcome");
    this.base.setVisible(true);
    this.base.pack();
  }

  @Override
  public void showMainPanel(String imageName) throws IllegalStateException {
    BetterIPModel current = this.c.getKnownImageModels().get(imageName);
    this.main.setMainImage(current);
    try {
      this.main.showMainImageModel();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    this.baseLayout.show(this.basePanel, "main");
  }

  @Override
  public void refresh() throws IllegalStateException {
    System.out.println("Refreshing now...");
    try {
      this.main.showMainImageModel();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    this.main.showLoadedImages();
    this.base.repaint();
    this.base.revalidate();
    this.base.pack();
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this.base, message, "User Information",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setController(BetterIPController controller) {
    this.c = controller;
  }
}
