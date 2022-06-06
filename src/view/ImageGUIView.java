package view;

import java.awt.*;

import javax.swing.*;

public class ImageGUIView {
  private String headerName;
  private Dimension dimension;
  private JFrame frame;
  private JLabel label;
  private JPanel panel;

  public ImageGUIView(String header, int width, int height) {
    if (header == null || width < 1 || height < 1) {
      throw new IllegalArgumentException("Invalid arguments: ");
    }
    headerName = header;
    label = new JLabel();
    panel = new JPanel();
    frame = new JFrame(headerName);
    dimension = new Dimension(width, height);
  }

  public void displayImage() {
    panel.add(label);
    frame.setContentPane(panel);
  }

}
