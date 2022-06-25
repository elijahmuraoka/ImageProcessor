package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

import ActionListeners.LoadActionListener;
import controller.BetterIPController;
import model.BetterIPModel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class SimpleGuiView implements IPGuiView {
  JFrame base;
  JPanel basePanel;
  CardLayout baseLayout;
  JPanel welcome;
  MainPanel main;

  BetterIPController c;

  public SimpleGuiView() {
  }


  @Override
  public void initialize() {
    int baseWidth = 1200;
    int baseHeight = 1000;
    // set the base JFrame initial settings
    this.base = new JFrame();
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
    this.welcome = new JPanel();
    this.welcome.setPreferredSize(new Dimension(baseWidth, baseHeight));
    this.welcome.setBackground(Color.DARK_GRAY);
    this.welcome.setLayout(new GridBagLayout());

    JLabel welcomeMessage = new JLabel("Welcome to the Image Processor Program!");
    // welcomeMessage.setPreferredSize(new Dimension(baseWidth / 15, baseHeight / 10));
    welcomeMessage.setHorizontalAlignment(JLabel.CENTER);
    welcomeMessage.setVerticalAlignment(JLabel.CENTER);
    welcomeMessage.setBackground(Color.WHITE);
    welcomeMessage.setForeground(Color.BLACK);
    welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    welcomeMessage.setFont(new Font("Plain", Font.BOLD, baseHeight / 25));

    JButton loadButton = new JButton("Click to Load an Image");
    //loadButton.setPreferredSize(new Dimension(baseWidth / 5, baseHeight /10));
    loadButton.setHorizontalAlignment(JLabel.CENTER);
    loadButton.setVerticalAlignment(JLabel.CENTER);
    loadButton.setBackground(Color.GREEN);
    loadButton.setForeground(Color.BLACK);
    loadButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    //loadButton.setIcon(new ImageIcon("images/eli.jpg"));
    // loadButton.setVerticalTextPosition(JButton.TOP);
    loadButton.setFont(new Font("Plain", Font.PLAIN, baseHeight / 25));
    loadButton.addActionListener(new LoadActionListener(this.c, this.welcome));

    Image welcomeImage = null;
    try {
       welcomeImage = ImageIO.read(new File("images/bucketboi.jpg"));
    } catch (IOException e) {
      this.renderMessage("Error: Welcome Image Not Found.");
    }
    Objects.requireNonNull(welcomeImage);
    Image welcomeImageScaled = welcomeImage.getScaledInstance(baseWidth/6,
            baseHeight/5, Image.SCALE_SMOOTH);
    ImageIcon welcomeImageIcon = new ImageIcon(welcomeImageScaled);
    JPanel centerWelcome = new JPanel();
    centerWelcome.setBackground(Color.LIGHT_GRAY);
    centerWelcome.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    centerWelcome.setLayout(new GridLayout(0, 1, 0, baseHeight / 20));
    centerWelcome.add(new JLabel(welcomeImageIcon));
    centerWelcome.add(welcomeMessage);
    centerWelcome.add(loadButton);
    this.welcome.add(centerWelcome);

    // add the welcome and main JPanels
    this.basePanel.add(this.welcome, "welcome");
    this.main = new MainPanel(baseWidth, baseHeight, this, this.c);
    this.basePanel.add(this.main, "main");

    // finalize and show everything in the view
    this.baseLayout.show(this.basePanel, "welcome");
    this.base.setVisible(true);
    this.base.pack();
  }

  @Override
  public void showMainPanel(String imageName) {
    BetterIPModel current = this.c.getKnownImageModels().get(imageName);
    this.main.setMainImage(current);
    this.main.showMainImageModel();
    this.baseLayout.show(this.basePanel, "main");
  }

  @Override
  public void refresh() {
    System.out.println("Refreshing now...");
    this.main.showLoadedImages();
    this.main.showMainImageModel();
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
