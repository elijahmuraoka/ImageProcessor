package view;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

import javax.swing.*;

public class Histogram extends JPanel {
  private final Map<Integer, Integer> data;
  private final Color color;
  private final Color backgroundColor;
  private int maxCalculatedHeight;
  private final int maxHeight;
  private int width;

  public Histogram(int maxHeight, Color color, Color backgroundColor, Map<Integer, Integer> data) {
    super();
    this.maxHeight = maxHeight;
    this.color = color;
    this.backgroundColor = backgroundColor;
    this.data = data;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.width = 255;

    int maxGraphArea = this.maxHeight * 3 / 4;

    // calculate the maximum height
    for (int i = 0; i < 255; i++) {
      try {
        this.maxCalculatedHeight = Math.max(Objects.requireNonNull(this.data.get(i)),
                this.maxCalculatedHeight);
      } catch (NullPointerException e) {
        // skip drawing the empty rectangle on the chart
      }
    }

    // draw the bars on the chart
    double barWidth = this.width / 255.0;
    for (int i = 0; i < 255; i++) {
      double barStart = i * barWidth;
      double barEnd = i * barWidth + barWidth;
      try {
        Objects.requireNonNull(data.get(i));
        double scale = 1.0 * this.maxCalculatedHeight / maxGraphArea;
        double barHeight = this.data.get(i) / scale;
        double baseLine = maxGraphArea - barHeight;
        System.out.println("BarStart: " + barStart
                + ", BarEnd: " + barEnd
                + ", BarWidth: " + barWidth
                + ", BarHeight: " + barHeight
                + ", MaxHeight: " + maxGraphArea
                + ", Baseline: " + baseLine);
        // color background
        g.setColor(this.backgroundColor);
        g.fillRect((int) barStart, 0, (int) barWidth, (int) baseLine + this.maxHeight / 4);
        // color and make bars
        g.setColor(this.color);
        g.fillRect((int) barStart, (int) baseLine + this.maxHeight / 4,
                (int) barWidth, (int) barHeight);
      } catch (NullPointerException e) {
        // draw only a background color that fills the entire column
        g.setColor(this.backgroundColor);
        g.fillRect((int) barStart, 0, (int) barWidth, maxGraphArea + this.maxHeight / 4);
      }
    }
    g.setColor(Color.BLACK);
    g.drawLine(0, this.maxHeight / 4, this.width, this.maxHeight / 4);
    System.out.println("Histogram Height: " + this.maxHeight + ", Width: " + this.width);

    // draw the histogram's titles
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
    g.setFont(new Font("Plain", Font.PLAIN, fontSize));
    g.drawString(type, this.width / 4, this.maxHeight / 6);
  }

//  @Override
//  protected void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    this.width = 255;
//
//        // calculate the maximum height
//    for (int i = 0; i < 255; i++) {
//      try {
//        this.maxCalculatedHeight = Math.max(Objects.requireNonNull(this.data.get(i)),
//                this.maxCalculatedHeight);
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
//        double scale = 1.0 * this.maxCalculatedHeight / maxHeight;
//        double barHeight = this.data.get(i) / scale;
//        double baseLine = this.maxHeight - barHeight;
//        System.out.println("BarStart: " + barStart
//                + ", BarEnd: " + barEnd
//                + ", BarWidth: " + barWidth
//                + ", BarHeight: " + barHeight
//                + ", MaxHeight: " + this.maxHeight
//                + ", Baseline: " + baseLine);
//        g.setColor(this.backgroundColor);
//        g.fillRect((int) barStart, 0, (int) barWidth, (int) baseLine);
//        g.setColor(this.color);
//        g.fillRect((int) barStart, (int) baseLine, (int) barWidth, (int) barHeight);
//      } catch (NullPointerException e) {
//        // skip drawing the empty rectangle on the chart
//      }
//    }
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
//    } else {
//      type = "Intensity";
//    }
//    int fontSize = this.maxHeight / 8;
//    g.setFont(new Font("Plain", Font.BOLD, fontSize));
//    g.drawString(type, this.width / 2 - type.length(), this.maxHeight / 8);
//  }
}
