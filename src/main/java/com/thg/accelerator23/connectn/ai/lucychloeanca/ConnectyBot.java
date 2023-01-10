package com.thg.accelerator23.connectn.ai.lucychloeanca;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.Random;


public class ConnectyBot extends Player {
  public ConnectyBot(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectyBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int position = randomMove(board);
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return position;
  }


  private int randomMove(Board board) {
    int position = new Random().nextInt(0, 10);
    if (!isSpaceAvailable(board, position)){
      position = randomMove(board);
    }
    return position;
  }

  private boolean isSpaceAvailable(Board board, int position){
    for (int i = 0; i < board.getConfig().getHeight(); i++){
      if (!board.hasCounterAtPosition(new Position(position, i))){
        return true;
      }
    }
    return false;
  }

  private int scoreCalculator(Board board, int columnNumber){
    int score;
    try {
      Board possibleNewBoard = new Board(board, columnNumber, getCounter());
      GameConfig newConfig = possibleNewBoard.getConfig();

    }catch (InvalidMoveException e){
      score = -1000;
    }

    return 1;
  }
}