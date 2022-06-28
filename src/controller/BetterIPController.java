package controller;

import java.util.Map;

import model.BetterIPModel;

/**
 * An additional better controller interface.
 */
public interface BetterIPController extends IPController {

  /**
   * Retrieves the controller's knownImageModels field and passes it on to the client.
   *
   * @return a map containing all known image models
   */
  Map<String, BetterIPModel> getKnownImageModels();

  /**
   * Sets the 'in' field of the current controller using the given readable object.
   *
   * @param in the readable object parameter
   */
  void setReadable(Readable in);
}
