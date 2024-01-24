package com.thg.accelerator23.connectn.ai.sreshtha;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class disconnectFour extends Player {
  public disconnectFour(Counter counter) {
    super(counter, disconnectFour.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int board_width = 10;
    int board_height = 8;

    int min = 0;
    int max = board_width - 1;

    int random = (int)Math.floor(Math.random() * (max - min + 1) + min);

    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return random;
  }

}
