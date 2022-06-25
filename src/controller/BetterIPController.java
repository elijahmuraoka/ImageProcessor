package controller;

import java.util.List;
import java.util.Map;

import model.BetterIPModel;

public interface BetterIPController extends IPController {
  Map<String, BetterIPModel> getKnownImageModels();
  void setReadable(Readable in);
}
