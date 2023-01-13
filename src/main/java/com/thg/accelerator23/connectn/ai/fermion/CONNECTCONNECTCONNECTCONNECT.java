package com.thg.accelerator23.connectn.ai.fermion;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class CONNECTCONNECTCONNECTCONNECT extends Player {
  public CONNECTCONNECTCONNECTCONNECT(Counter counter) {
    super(counter, CONNECTCONNECTCONNECTCONNECT.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    BoardAnalyser moveChecker = new BoardAnalyser();
    int randomColumn = 4;


    while(!moveChecker.checkForFullColumn(randomColumn,board)){
      randomColumn = (int) (Math.random() * (10));
    }

    return randomColumn;
  }
}
