package controller;

import one.*;

public interface GameControllerInterface {

  Panel startGame(Object obj);
  
  void loadGame(String slot);
  void startNewGame(Race race);
  void saveGame(String slot);
  
  World getWorld();
  Player getPlayer();
  
}
