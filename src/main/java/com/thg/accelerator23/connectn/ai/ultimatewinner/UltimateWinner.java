package com.thg.accelerator23.connectn.ai.ultimatewinner;

import com.thehutgroup.accelerator.connectn.player.*;

import static java.lang.Math.random;


public class UltimateWinner extends Player {
  public int depth = 4;

  public UltimateWinner(Counter counter) {
    //TODO: fill in your name here
    super(counter, UltimateWinner.class.getName());
  }
//  @Override
//  public int makeMove(Board board) {
//    int randomNum = 0 + (int)(random() * 9);
//    System.out.println(board.getCounterAtPosition(new Position(1, 1)));
//    //TODO: some crazy analysis
//    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
//    return randomNum;
//  }
  boolean maximizingPlayer = true;
  @Override
  public int makeMove(Board board) {
    int chosenMove = minimaxChoice(board, 4, maximizingPlayer);
    int randomNum = (int)(random() * 9);
    minimaxChoice(board, 4, maximizingPlayer);
    System.out.println(randomNum);
    return randomNum;
  }
  public boolean winningMove(Board board, Counter counter) {
    for (int columns = 0; columns < board.getConfig().getWidth(); columns++) {
      for (int rows = 0; rows < board.getConfig().getHeight() -3; rows++) {
        if (board.getCounterAtPosition(new Position(columns, rows)) == counter && board.getCounterAtPosition(new Position(columns, rows + 1)) == counter && board.getCounterAtPosition(new Position(columns, rows + 2)) == counter && board.getCounterAtPosition(new Position(columns, rows + 3)) == counter) {
          System.out.println("4 in row vertical");
          return true;
        }
      }
    }
    for (int columns = 0; columns < board.getConfig().getWidth() -3; columns++) {
      for (int rows = 0; rows < board.getConfig().getHeight(); rows++) {
        if (board.getCounterAtPosition(new Position(columns, rows)) == counter && board.getCounterAtPosition(new Position(columns + 1, rows)) == counter && board.getCounterAtPosition(new Position(columns + 2, rows)) == counter && board.getCounterAtPosition(new Position(columns + 3, rows)) == counter) {
          System.out.println("4 in row horizontal");
          return true;
        }
      }
    }
    for (int columns = 0; columns < board.getConfig().getWidth() -3; columns++) {
      for (int rows = 0; rows < board.getConfig().getHeight() -3; rows++) {
        if (board.getCounterAtPosition(new Position(columns, rows)) == counter && board.getCounterAtPosition(new Position(columns +1, rows +1)) == counter && board.getCounterAtPosition(new Position(columns +2, rows +2)) == counter && board.getCounterAtPosition(new Position(columns +3, rows +3)) == counter) {
          System.out.println("4 in row diagonal");
          return true;
        }
      }
    }
    for (int columns = 0; columns < board.getConfig().getWidth() -3; columns++) {
      for (int rows = 3; rows < board.getConfig().getHeight(); rows++) {
        if (board.getCounterAtPosition(new Position(columns, rows)) == counter && board.getCounterAtPosition(new Position(columns +1, rows -1)) == counter && board.getCounterAtPosition(new Position(columns +2, rows -2)) == counter && board.getCounterAtPosition(new Position(columns +3, rows -3)) == counter) {
          System.out.println("4 in row diagonal");
          return true;
        }
      }
    }
    return false;
  }

  private boolean isTerminal(){
    return false;
  }
  private int minimaxChoice(Board board, int depth, boolean maximizingPlayer) {
    winningMove(board, Counter.O);
    winningMove(board, Counter.X);

//    if (depth == 0 | isTerminal()) {
//      if (isTerminal()) {
//        if (winningMove(board, XPiece)) {
//          return (None; 10000000000);
//        }
//        else if (winningMove(board, YPiece)) {
//          return (None; -10000000000);
//        }
//        else {
//          return (None; 0);
//      }
//    }
    return 5;
  }

}

