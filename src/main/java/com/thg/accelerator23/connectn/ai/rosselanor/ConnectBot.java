package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;

import java.util.stream.IntStream;

import java.util.concurrent.ThreadLocalRandom;


public class ConnectBot extends Player {
  BoardAnalyser boardAnalyser;
  public ConnectBot(Counter counter) {
    super(counter, ConnectBot.class.getName());
    boardAnalyser = new BoardAnalyser()
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

}