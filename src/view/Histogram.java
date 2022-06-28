package view;


import java.awt.*;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

/**
 * A Histogram representing the frequency of red, green, blue, and intensity values
 * that occur between 0 and 255 for a specific image model and all of its pixels.
 */
public class Histogram extends JPanel {
  private final Map<Integer, Integer> data;
  private final Color color;
  private final Color backgroundColor;
  private int maxCalculatedFrequency;
  private final int maxHeight;

  /**
   * The constructor used to create a generic Histogram object.
   *
   * @param maxHeight       the maximum height of the entire panel
   * @param color           the color of the bars, also used to determine the
   * @param backgroundColor the background color,
   * @param data            the data of the pixel value frequencies represented as a
   *                        map of integers to integers
   * @throws IllegalArgumentException when the maximum panel height is not greater than zero
   */
  public Histogram(int maxHeight, Color color, Color backgroundColor, Map<Integer, Integer> data) {
    super();
    if (maxHeight <= 0) {
      throw new IllegalArgumentException("Error: Maximum height must be greater than zero.");
    }
    this.maxHeight = maxHeight;
    this.color = color;
    this.backgroundColor = backgroundColor;
    this.data = data;
  }

  /**
   * Draws a full histogram according to the inputted color:
   * - Red -> Red
   * - Green -> Green
   * - Blue -> Blue
   * - Orange -> Intensity
   * This method also displays a background color and title appropriately.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  //  @Override
  //  protected void paintComponent(Graphics g) {
  //    super.paintComponent(g);
  //    int width = 256;
  //
  //    // the maximum area reserved to draw the actual histogram
  //    int maxGraphArea = this.maxHeight * 3 / 4;
  //
  //    // calculate the maximum height
  //    for (int i = 0; i < 256; i++) {
  //      try {
  //        this.maxCalculatedFrequency = Math.max(Objects.requireNonNull(this.data.get(i)),
  //                this.maxCalculatedFrequency);
  //      } catch (NullPointerException e) {
  //        // skip drawing the empty rectangle on the chart
  //      }
  //    }
  //
  //    for (int i = 0; i < width; i++) {
  //      // color background
  //      g.setColor(this.backgroundColor);
  //      g.fillRect(i, 0, i + 1, this.maxHeight);
  //    }
  //
  //    // draw the bars on the chart
  //    double barWidth = width / 256.0;
  //    g.setColor(this.color);
  //    for (Map.Entry<Integer, Integer> entry : this.data.entrySet()) {
  //      int scale = entry.getValue() / this.maxCalculatedFrequency;
  //      double barHeight = scale * maxGraphArea;
  //      double baseLine = this.maxHeight + 1.0 * this.maxHeight/4 - barHeight;
  //      g.fillRect((Integer) entry.getKey(), (int) baseLine, 1, (int) barHeight);
  //      System.out.println(
  //              ", BarHeight: " + barHeight
  //                      + ", MaxHeight: " + maxGraphArea
  //                      + ", Baseline: " + baseLine);
  //    }
  //    g.setColor(Color.BLACK);
  //    g.drawLine(0, this.maxHeight / 4, width, this.maxHeight / 4);
  //    System.out.println("Histogram Height: " + this.maxHeight + ", Width: " + width);
  //
  //    // draw the histogram's titles
  //    g.setColor(Color.BLACK);
  //    String type = "";
  //    if (Color.RED.equals(this.color)) {
  //      type = "Red";
  //    } else if (Color.GREEN.equals(this.color)) {
  //      type = "Green";
  //    } else if (Color.BLUE.equals(this.color)) {
  //      type = "Blue";
  //    } else if (Color.ORANGE.equals(this.color)) {
  //      type = "Intensity";
  //    }
  //    int fontSize = this.maxHeight / 8;
  //    g.setFont(new Font("Plain", Font.BOLD, fontSize));
  //    g.drawString(type, width / 4, this.maxHeight / 6);
  //  }

  //  @Override
  //  protected void paintComponent(Graphics g) {
  //    super.paintComponent(g);
  //    this.width = 255;
  //
  //    // the maximum area reserved to draw the actual histogram
  //    int maxGraphArea = this.maxHeight * 3 / 4;
  //
  //    // calculate the maximum height
  //    for (int i = 0; i < 255; i++) {
  //      try {
  //        this.maxCalculatedFrequency = Math.max(Objects.requireNonNull(this.data.get(i)),
  //                this.maxCalculatedFrequency);
  //      } catch (NullPointerException e) {
  //        // skip drawing the empty rectangle on the chart
  //      }
  //    }
  //
  //    // draw the bars on the chart
  //    double barWidth = this.width / 255.0;
  //    for (int i = 0; i < 255; i++) {
  //      double barStart = i * barWidth;
  //      double barEnd = i * barWidth + barWidth;
  //      try {
  //        Objects.requireNonNull(data.get(i));
  //        double scale = 1.0 * this.maxCalculatedFrequency / maxGraphArea;
  //        double barHeight = this.data.get(i) / scale;
  //        double baseLine = maxGraphArea - barHeight;
  //        System.out.println("BarStart: " + barStart
  //                + ", BarEnd: " + barEnd
  //                + ", BarWidth: " + barWidth
  //                + ", BarHeight: " + barHeight
  //                + ", MaxHeight: " + maxGraphArea
  //                + ", Baseline: " + baseLine);
  //        // color background
  ////        g.setColor(this.backgroundColor);
  ////        g.fillRect((int) barStart, 0, (int) barWidth, (int) baseLine + this.maxHeight / 4);
  //        // draw only a background color that fills the entire column
  //        g.setColor(this.backgroundColor);
  //        g.fillRect((int) barStart, 0, (int) barWidth, this.maxHeight);
  //        // color and make bars
  //        g.setColor(this.color);
  //        g.fillRect((int) barStart, (int) baseLine, (int) barWidth, (int) barHeight);
  //      } catch (NullPointerException e) {
  //        //
  //      }
  //    }
  //    g.setColor(Color.BLACK);
  //    g.drawLine(0, this.maxHeight / 4, this.width, this.maxHeight / 4);
  //    System.out.println("Histogram Height: " + this.maxHeight + ", Width: " + this.width);
  //
  //    // draw the histogram's titles
  //    g.setColor(Color.BLACK);
  //    String type = "";
  //    if (Color.RED.equals(this.color)) {
  //      type = "Red";
  //    } else if (Color.GREEN.equals(this.color)) {
  //      type = "Green";
  //    } else if (Color.BLUE.equals(this.color)) {
  //      type = "Blue";
  //    } else if (Color.ORANGE.equals(this.color)) {
  //      type = "Intensity";
  //    }
  //    int fontSize = this.maxHeight / 8;
  //    g.setFont(new Font("Plain", Font.BOLD, fontSize));
  //    g.drawString(type, this.width / 4, this.maxHeight / 6);
  //  }
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int width = 256;

    // the maximum area reserved to draw the actual histogram
    int maxGraphArea = this.maxHeight * 3 / 4;

    // calculate the maximum frequency height
    for (int i = 0; i < width; i++) {
      try {
        this.maxCalculatedFrequency = Math.max(Objects.requireNonNull(this.data.get(i)),
                this.maxCalculatedFrequency);
      } catch (NullPointerException e) {
        // skip drawing the empty rectangle on the chart
      }
    }

    // draw the bars on the chart
    double barWidth = width / 256.0;
    for (int i = 0; i < width; i++) {
      double barStart = i * barWidth;
      double barEnd = i * barWidth + barWidth;
      try {
        Objects.requireNonNull(data.get(i));
        double scale = 1.0 * this.data.get(i) / this.maxCalculatedFrequency;
        double barHeight = scale * maxGraphArea;
        double baseLine = this.maxHeight - barHeight;
//        System.out.println("BarStart: " + barStart
//                + ", BarEnd: " + barEnd
//                + ", BarWidth: " + barWidth
//                + ", BarHeight: " + barHeight
//                + ", MaxHeight: " + maxGraphArea
//                + ", Baseline: " + baseLine);
        // draw only a background color that fills the entire column
        g.setColor(this.backgroundColor);
        g.fillRect((int) barStart, 0, (int) barWidth, this.maxHeight);
        // color and make bars
        g.setColor(this.color);
        g.fillRect((int) barStart, (int) baseLine,
                (int) barWidth, (int) barHeight);
      } catch (NullPointerException e) {
        // draw only a background color that fills the entire column
        g.setColor(this.backgroundColor);
        g.fillRect((int) barStart, 0, (int) barWidth, this.maxHeight);
      }
    }
    g.setColor(Color.BLACK);
    g.drawLine(0, this.maxHeight / 4, width, this.maxHeight / 4);
    System.out.println("Histogram Height: " + this.maxHeight + ", Width: " + width);

    // draw the histogram's title
    g.setColor(Color.BLACK);
    String type = "";
    if (Color.RED.equals(this.color)) {
      type = "Red";
    } else if (Color.GREEN.equals(this.color)) {
      type = "Green";
    } else if (Color.BLUE.equals(this.color)) {
      type = "Blue";
    } else if (Color.ORANGE.equals(this.color)) {
      type = "Intensity";
    }
    int fontSize = this.maxHeight / 8;
    g.setFont(new Font("Plain", Font.BOLD, fontSize));
    g.drawString(type, width / 4, this.maxHeight / 6);
  }
}
