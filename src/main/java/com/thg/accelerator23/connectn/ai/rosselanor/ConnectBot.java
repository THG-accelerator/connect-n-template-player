package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.stream.IntStream;


public class ConnectBot extends Player {
  public ConnectBot(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    int position = 4;
    
    if (isColumnFull(board, position)){
      return position + 1;
    }
    else {
      return position;
    }
  }

  private boolean isColumnFull(Board board, int position) {

    int i = position;
    return IntStream.range(0, board.getConfig().getHeight())
            .allMatch(
                    j -> board.hasCounterAtPosition(new Position(i, j))
            );
  }

}