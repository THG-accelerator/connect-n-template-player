package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class AlanisFourconnect extends Player {
  public AlanisFourconnect(Counter counter) {
    //TODO: fill in your name here
    super(counter, AlanisFourconnect.class.getName());
  }

  public int validRandomMove(Board board, List<Integer> columnsToPickFrom){
    int randomMove;
    do {
      randomMove = columnsToPickFrom.get(ThreadLocalRandom.current().nextInt(columnsToPickFrom.size()));
    } while (board.hasCounterAtPosition(new Position(randomMove, board.getConfig().getHeight()-1)));
    return randomMove;
  }


  @Override
  public int makeMove(Board board) {
    AIAnalyser slayIAnalyser = new AIAnalyser(board.getConfig());
    List<Integer> propaGoodMoves = slayIAnalyser.movesNotBelowGameEndingSpace(board, getCounter());
    try {
      Integer winningMove = slayIAnalyser.winningColumn(board, getCounter());
      if (winningMove != null) {
        return winningMove;
      }
      Integer blockingAWin = slayIAnalyser.winningColumn(board, getCounter().getOther());
      if (blockingAWin != null){
        return blockingAWin;
      }
      return validRandomMove(board, propaGoodMoves);
    } catch(Exception exception){
      return validRandomMove(board, propaGoodMoves);
    }
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
  }
}

//   if (!board.hasCounterAtPosition(new Position((board.getConfig().getWidth() / 2), 0))){
//           return (int) Math.floor((board.getConfig().getWidth() / 2));
//           }