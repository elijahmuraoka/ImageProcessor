package controller;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.Scanner;

import javax.swing.*;

public class ImageControllerImpl implements ImageController {

  private Scanner scan;

  public ImageControllerImpl(Readable r) {
    scan = new Scanner(r);
  }

  @Override
  public void load(String imagePath, String imageName) {
    System.out.println(imagePath + "/" + imageName);
  }

  @Override
  public void save(String imagePath, String imageName) {
    /*
    FileOutputStream fos = new FileOutputStream("" + imagePath + imageName);
    fos.write(new String(matrix).getBytes());
     */
  }

  public void testMod() {
    //
  }

  @Override
  public void redComponent(String imageName, String destName) {

  }

  @Override
  public void horizontalFlip(String imageName, String imagePath) {

  }

  @Override
  public void verticalFlip(String imageName, String imagePath) {

  }

  @Override
  public void brighten(int increment, String imageName, String imagePath) {

  }

  public void show(String imageName, String imagePath) {
    JFrame frame = new JFrame(imageName + " | " + imagePath);
    JLabel label = new JLabel("This is a label");
    JPanel panel = new JPanel();

    panel.add(label);
    frame.setSize(new Dimension(400, 300));
    frame.setVisible(true);
  }

}
