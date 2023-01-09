package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.stream.IntStream;

import java.util.concurrent.ThreadLocalRandom;


public class ConnectBot extends Player {
  public ConnectBot(Counter counter) {
    super(counter, ConnectBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
  
    int position = ThreadLocalRandom.current().nextInt(0, 10);
    
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