package com.thg.accelerator23.connectn.ai.bibblebobbleshrimpbot;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.ArrayList;
import java.util.List;


public class BibbleBobbleShrimpBot extends Player {
  public BibbleBobbleShrimpBot(Counter counter) {
    super(counter, BibbleBobbleShrimpBot.class.getName());
  }
    private int randomNum(int max) {
      return (int) (Math.random() * max);
    }

  @Override
  public int makeMove(Board board) {

    GameConfig config = board.getConfig();
    int width = config.getWidth();
    int height = config.getHeight();

    List<Integer> validColumns = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      Position position = new Position(i, height - 1);
      if (!board.hasCounterAtPosition(position)) {
        validColumns.add(i);
      }
    }

    return randomNum(validColumns.size());
  }
}
