package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import view.IPView;

/**
 * An abstract representation for several types of image files.
 * This includes...
 * - PPM
 * - JPG
 * - PNG
 * - BMP
 */
public abstract class AbstractImageFile implements ImageFile {
  // the height of the PPM image
  protected int height;
  // the width of the PPM image
  protected int width;
  // the working image displayed as a 1-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  protected final List<List<int[]>> workingImageData = new ArrayList<>();

  /**
   * Read any image file and store its data.
   *
   * @param filename the path of the file.
   * @param v        the Image Processor's view to render necessary message
   * @throws IOException when either the input(s) and/or output(s) are invalid
   */
  @Override
  public void read(String filename, IPView v) throws IOException {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(filename));
    } catch (IOException e) {
      v.renderMessage("File " + filename + " not found!\n");
      return;
    }
    if (img != null) {
      this.width = img.getWidth();
      v.renderMessage("Width of image: " + this.width + "\n");
      this.height = img.getHeight();
      v.renderMessage("Height of image: " + this.height + "\n");
      int maxValue = 255;
      v.renderMessage("Maximum value of a color in this file (usually 255): " + maxValue + "\n");
      for (int i = 0; i < this.height; i++) {
        ArrayList<int[]> newColumn = new ArrayList<>();
        for (int j = 0; j < this.width; j++) {
          Color c = new Color(img.getRGB(j, i));
          int r = c.getRed();
          int g = c.getGreen();
          int b = c.getBlue();
          int[] pixel = new int[]{r, g, b};
          newColumn.add(pixel);
          // System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        }
        this.workingImageData.add(newColumn);
      }
      v.renderMessage("Successfully loaded image: " + filename + "\n");
    }
  }

  public abstract String generateFileName(String saveAsName, String imagePath);

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public List<List<int[]>> getWorkingImageData() {
    return this.workingImageData;
  }
}
