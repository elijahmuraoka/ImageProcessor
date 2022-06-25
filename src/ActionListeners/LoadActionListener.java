package ActionListeners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import controller.BetterIPController;
import view.IPGuiView;

public class LoadActionListener implements ActionListener {
  BetterIPController c;
  JPanel parent;

  public LoadActionListener(BetterIPController c, JPanel parent) {
    this.c = c;
    this.parent = parent;
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalStateException {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Choose the image you would like to edit!");
    int openDialogue = fileChooser.showOpenDialog(this.parent);
    this.parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    if (openDialogue == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String imagePath = file.getAbsolutePath();
      String imageName = imagePath.replaceAll("\\s", "");
      System.out.println("Init String Name:" + imageName);
      try {
        this.c.load(imageName, imagePath);
      } catch (IOException ex) {
        throw new IllegalStateException(ex.getMessage());
      }
    }
    this.parent.setCursor(Cursor.getDefaultCursor());
  }
}
