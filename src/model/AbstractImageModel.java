package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * An abstract representation for several types of image file types.
 * This includes...
 * - PPM
 * - JPG
 * - PNG
 * - BMP
 * Each model represents a single image (or version) that stores data such as the name of the image,
 * the (desired) file name for the image, the height, the width, the maximum color component,
 * and the image's pixel information.
 */
public abstract class AbstractImageModel implements IPModel {
  // the current name of the image
  protected String imageName;
  // the (desired) file name for this ImageFile object
  protected String fileName;
  // the height of the image
  protected int height;
  // the width of the image
  protected int width;
  // the maximum color component for this image model
  protected int maxComponent;
  // the working image displayed as a 1-D list of Pixels
  // Each Pixel is an array of 3 integers that represent
  // its RGB components respectively
  protected List<List<int[]>> workingImageData = new ArrayList<>();

  /**
   * An image model constructor used to create representative image objects using
   * the given image name, height, width, and a 2D array of pixels (size-3 arrays)
   * representing the image's data.
   *
   * @param imageName        the current name of the image
   * @param height           the height of the image
   * @param width            the width of the image
   * @param maxComponent     the maximum color component
   * @param workingImageData the working image data displayed as a 2-D list of Pixels
   *                         Each Pixel is an array of 3 integers that represent
   *                         its RGB components respectively
   * @param fileName         the name of the image file
   * @throws IllegalArgumentException when the image's height and/or width are negative
   */
  public AbstractImageModel(String imageName, int height, int width, int maxComponent,
                            List<List<int[]>> workingImageData, String fileName)
          throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || maxComponent <= 0) {
      throw new IllegalArgumentException("Image height and width must be greater than 0.");
    }
    this.imageName = imageName;
    this.height = height;
    this.width = width;
    this.maxComponent = maxComponent;
    this.workingImageData = workingImageData;
    this.fileName = fileName;
  }

  /**
   * The abstract image file constructor used to create an Image File object and store its
   * file name.
   *
   * @param fileName the (desired) name of the file
   */
  public AbstractImageModel(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public abstract String generateFileName(String saveAsName, String imagePath);

  /**
   * Read any image file and store its data.
   *
   * @throws IllegalStateException when the file cannot be read properly
   */
  @Override
  public void read() throws IllegalStateException {
    BufferedImage img;
    try {
      img = ImageIO.read(new File(this.fileName));
    } catch (IOException e) {
      throw new IllegalStateException("File " + this.fileName + " not found!\n");
    }
    if (img != null) {
      this.width = img.getWidth();
      this.height = img.getHeight();
      for (int i = 0; i < this.height; i++) {
        ArrayList<int[]> newColumn = new ArrayList<>();
        for (int j = 0; j < this.width; j++) {
          Color c = new Color(img.getRGB(j, i));
          int r = c.getRed();
          int g = c.getGreen();
          int b = c.getBlue();
          this.maxComponent = Math.max(this.maxComponent, Math.max(r, Math.max(g, b)));
          int[] pixel = new int[]{r, g, b};
          newColumn.add(pixel);
          // System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        }
        this.workingImageData.add(newColumn);
      }
    }
  }

  @Override
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  @Override
  public void setWidth(int width) throws IllegalArgumentException {
    if (width > 0) {
      this.width = width;
      return;
    }
    throw new IllegalArgumentException("Max component must be greater than zero.");
  }

  @Override
  public void setHeight(int height) throws IllegalArgumentException {
    if (height > 0) {
      this.height = height;
      return;
    }
    throw new IllegalArgumentException("Height must be greater than zero.");
  }

  @Override
  public void setMaxComponent(int maxComponent) throws IllegalArgumentException {
    if (maxComponent >= 0 && maxComponent <= 255) {
      this.maxComponent = maxComponent;
      return;
    }
    throw new IllegalArgumentException("Max color component must be equal to or greater"
            + " than 0 and less than 255.");
  }

  @Override
  public void setWorkingImageData(List<List<int[]>> workingImageData) {
    this.workingImageData = workingImageData;
  }

  @Override
  public String getImageName() {
    return this.imageName;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxComponent() {
    return this.maxComponent;
  }

  @Override
  public List<List<int[]>> getWorkingImageData() {
    return this.workingImageData;
  }

  @Override
  public String getFileName() {
    return this.fileName;
  }
}
