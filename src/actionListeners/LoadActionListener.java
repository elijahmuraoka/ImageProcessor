package actionListeners;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JFileChooser;


import controller.BetterIPController;

/**
 * This class allows the "load" buttons to perform certain actions when clicked.
 * This includes "loading" the image, meaning the program reads and stores all its data
 * in a new model instance.
 */
public class LoadActionListener implements ActionListener {
  BetterIPController c;
  JPanel parent;

  /**
   * A constructor for the LoadActionListener class.
   *
   * @param c      the program's controller
   * @param parent the parent component of the specific load buttons
   */
  public LoadActionListener(BetterIPController c, JPanel parent)
          throws IllegalArgumentException {
    if (c == null || parent == null) {
      throw new IllegalArgumentException("Error: Either the controller and/or view parameters " +
              "for this LoadActionListener are null.");
    }
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
