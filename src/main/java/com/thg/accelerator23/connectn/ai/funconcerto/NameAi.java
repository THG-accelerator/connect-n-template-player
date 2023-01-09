package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.Random;

public class NameAi extends Player {
  public NameAi(Counter counter) {
    //TODO: fill in your name here
    super(counter, NameAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    Random r = new Random();
    int randomColumn;
    do {
      randomColumn = r.nextInt(10);
    }while(board.getCounterAtPosition(new Position(randomColumn, 0)) != null);
    return randomColumn;
  }
}
