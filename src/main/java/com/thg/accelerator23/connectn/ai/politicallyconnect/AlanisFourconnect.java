package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.concurrent.ThreadLocalRandom;


public class AlanisFourconnect extends Player {
  public AlanisFourconnect(Counter counter) {
    //TODO: fill in your name here
    super(counter, AlanisFourconnect.class.getName());
  }

  public int validRandomMove(Board board){
    int randomMove;
    do {
      randomMove = ThreadLocalRandom.current().nextInt(0, board.getConfig().getWidth());
    } while (board.hasCounterAtPosition(new Position(randomMove, board.getConfig().getHeight()-1)));
    return randomMove;
  }


  @Override
  public int makeMove(Board board) {
    AIAnalyser slayIAnalyser = new AIAnalyser(board.getConfig());
    try {
      if (slayIAnalyser.winningColumn(board, getCounter()) != null) {
        return slayIAnalyser.winningColumn(board, getCounter());
      }
      else if (slayIAnalyser.winningColumn(board, getCounter().getOther()) != null){
        return slayIAnalyser.winningColumn(board, getCounter().getOther());
      }
      else {
      return validRandomMove(board);
    }} catch(Exception exception){
      return validRandomMove(board);
    }
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
  }
}

//   if (!board.hasCounterAtPosition(new Position((board.getConfig().getWidth() / 2), 0))){
//           return (int) Math.floor((board.getConfig().getWidth() / 2));
//           }