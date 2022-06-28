package model;

import java.awt.Image;
import java.util.List;
import java.util.Map;

/**
 * A better version that enhances the current IPModel interface.
 */
public interface BetterIPModel extends IPModel {

  /**
   * Retrieves the model's current list of commands to be executed.
   *
   * @return the model's current list of commands as a list of strings.
   */
  List<String> getCommandList();

  /**
   * Adds the given command as to the commandList as a string.
   *
   * @param command adds this string, representing a command, to the current list of commands.
   */
  void addToCommandList(String command);

  /**
   * Sets the model's command list using the given list of strings.
   *
   * @param commandList the given
   */
  void setCommandList(List<String> commandList);

  /**
   * Returns a readable that contains all commands to be executed on this model.
   */
  Readable make();

  /**
   * Removes the most recently added command from the list of commands.
   *
   * @throws IllegalStateException when there are no more commands left to undo (ignoring the
   *                               first which is the load command)
   */
  void undo() throws IllegalStateException;

  /**
   * Sets the image of the model using the given image.
   *
   * @param image the image object this model represents
   */
  void setImage(Image image);

  /**
   * Retrieves the model's current image object representation.
   *
   * @return the image object that currently represents the model
   */
  Image getImage();

  /**
   * A list of histograms representing the model's pixel values and frequencies using
   * individual RGB components and intensity.
   *
   * @return all histograms (represented as a map of integers) in a single list for all types
   *         including: Red, Green, Blue, and Intensity
   */
  List<Map<Integer, Integer>> generateHistograms();

  /**
   * Adds an integer to the undo counter list.
   *
   * @param i an integer representing the number of times this command was called
   */
  void addToUndoCounter(int i);

  /**
   * Set the model's undoCounter field using the given list of integers.
   *
   * @param undoCounter a list of integers as
   */
  void setUndoCounter(List<Integer> undoCounter);

  /**
   * Retrieve the undoCounter field from the model.
   *
   * @return a list of integers used to determine how many times to undo
   */
  List<Integer> getUndoCounter();
}
