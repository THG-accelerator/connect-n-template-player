package com.thg.accelerator23.connectn.ai.ultimatewinner;

import com.thehutgroup.accelerator.connectn.player.*;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;
import static java.util.Collections.list;
//import static sun.nio.ch.DatagramChannelImpl.AbstractSelectableChannels.forEach;


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

  private int minimaxChoice(Board board, int depth, boolean maximizingPlayer) {
    winningMove(board, Counter.O);
    winningMove(board, Counter.X);
    scorePosition(board, Counter.O);
    scorePosition(board, Counter.X);
    return 5;
  }

  public int evaluateWindow (Counter[] window, Counter counter) {
    int score = 0;
    Counter other = counter.getOther();
    int playerCount = 0;
    int otherCount = 0;
    int emptyCount = 0;
    for (Counter current : window){
      if (current.getStringRepresentation() == counter.getStringRepresentation()){
        playerCount++;
      } else if (current.getStringRepresentation() == other.getStringRepresentation()) {
        otherCount++;
      } else {
        emptyCount++;
      }
    }
    if (playerCount == 4) {
      score += 100;
    } else if (playerCount == 3 && emptyCount == 1) {
      score += 5;
    } else if (playerCount == 2 && emptyCount == 2) {
      score += 2;
    }if (otherCount == 3 && emptyCount == 1) {
      score -= 4;
    }
    return score;
  }

  //minute 15
  public int scorePosition (Board board, Counter counter) {
    int score = 0;

    // Horizontal
    for (int r = 0; r < board.getConfig().getHeight(); r++) {
      //ArrayList row = new ArrayList();
      for (int c = 0; c < board.getConfig().getWidth() - 3; c++) {
        //row.add(board.getCounterAtPosition(new Position(r, c)));
        Counter [] window = new Counter [4];
        for (int i = 0; i < window.length; i++) {
          window [i] = board.getCounterAtPosition(new Position(c+i, r));
        }
        score += evaluateWindow(window, counter);
//        Counter [] window2 = {
//                board.getCounterAtPosition(new Position(c, r)),
//                board.getCounterAtPosition(new Position(c+1, r)),
//                board.getCounterAtPosition(new Position(c+2, r)),
//                board.getCounterAtPosition(new Position(c+3, r))
        //};
      }
    }
    // Vertical
    for (int c = 0; c < board.getConfig().getWidth(); c++) {
      for (int r = 0; r < board.getConfig().getHeight() - 3; r++) {
        Counter[] window = new Counter[4];
        for (int i = 0; i < window.length; i++) {
          window[i] = board.getCounterAtPosition(new Position(c, r + i));
        }
        score += evaluateWindow(window, counter);
      }
    }

    // Diagonal /
    for (int c = 0; c < board.getConfig().getWidth() - 3; c++) {
      for (int r = 0; r < board.getConfig().getHeight() - 3; r++) {
        Counter[] window = new Counter[4];
        for (int i = 0; i < window.length; i++) {
          window[i] = board.getCounterAtPosition(new Position(c + i, r + i));
        }
        score += evaluateWindow(window, counter);
      }
    }

    // Opposite Diagonal \
    for (int c = 0; c < board.getConfig().getWidth() - 3; c++) {
      for (int r = 0; r < board.getConfig().getHeight() - 3; r++) {
        Counter[] window = new Counter[4];
        for (int i = 0; i < window.length; i++) {
          window[i] = board.getCounterAtPosition(new Position(c + 3 - i, r + i));
        }
        score += evaluateWindow(window, counter);
      }
    }
    return score;
  }





}

