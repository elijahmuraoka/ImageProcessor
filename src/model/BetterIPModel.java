package model;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * A better version that enhances the current IPModel interface.
 */
public interface BetterIPModel extends IPModel {

  /**
   * Retrieves this model's current list of commands to be executed.
   *
   * @return the model's current list of commands as a readable.
   */
  List<String> getCommandList();

  /**
   * Adds the given command to the commandList.
   *
   * @param command adds the readable object, representing a command, to the current
   *                list of commands.
   */
  void addToCommandList(String command);

  void setCommandList(List<String> commandList);

  /**
   * Returns a readable that contains all commands to be executed on this model.
   */
  Readable make();

  /**
   * Removes the most recently added command from the list of commands.
   */
  void undo() throws IllegalStateException;

  void setImage(Image image);

  Image getImage();

  List<Map<Integer, Integer>> generateHistograms();

  void addToUndoCounter(int i);

  void setUndoCounter(List<Integer> undoCounter);

  List<Integer> getUndoCounter();
}
