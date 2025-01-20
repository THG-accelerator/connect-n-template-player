package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.ArrayList;
import java.util.List;


public class OnLeaveSlowResponse extends Player {

  // timeout value, gives 500ms buffer
  private static final long TIMEOUT_SECONDS = 9500;
  private int bestMoveFound;
  private int bestScoreFound;
  private long startTime;

  public OnLeaveSlowResponse(Counter counter) {
    //TODO: fill in your name here
    super(counter, OnLeaveSlowResponse.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    startTime = System.currentTimeMillis();
    bestMoveFound = 4;
    bestScoreFound = Integer.MIN_VALUE;

    List<Integer> legalMoves = getLegalMoves(board);

    for (int depth = 1; !isTimeUp(); depth++) {
      try {
        searchMovesAtDepth(board, depth, legalMoves);
      } catch (TimeoutException e) {
        break;
      }
    }
    return bestMoveFound;
  }




  private void searchMovesAtDepth(Board board, int depth, List<Integer> legalMoves) throws TimeoutException {
    for (int move : legalMoves) {
      if (isTimeUp()) {
        throw new TimeoutException();
      }
      // do minimax
      int score = 0;

      if (score > bestScoreFound) {
        bestScoreFound = score;
        bestMoveFound = move;
      }
    }
  }

  private boolean isTimeUp() {
    return System.currentTimeMillis() - startTime > TIMEOUT_SECONDS;
  }


  private static class TimeoutException extends Exception {}

  public List<Integer> getLegalMoves(Board board) {
    List<Integer> legalMoves = new ArrayList<>();
    Counter[][] counterPlacements = board.getCounterPlacements();
    for (int col = 0; col < board.getConfig().getWidth(); col++) {
      if (counterPlacements[col][board.getConfig().getHeight() - 1] == null) {
        legalMoves.add(col);
      }
    }
    return legalMoves;
  }

  private int evaluatePosition(Board board) {
    Counter[][] counterPlacements = board.getCounterPlacements();
    Counter counter = getCounter();
    // Cases
    // 4 in a row: player's counter --> Integer.MAX_VALUE; opponent's counter --> Integer.MIN_VALUE
    if (hasFourInARow(counterPlacements, counter)) {
      return Integer.MAX_VALUE;
    } else if (hasFourInARow(counterPlacements, counter.getOther())) {
      return Integer.MIN_VALUE;
    }
    // Placeholder
    return 0;
  }

  private boolean isGameOver(Board board) {
    Counter[][] counterPlacements = board.getCounterPlacements();
    if (isBoardFull(counterPlacements)) {
      return true;
    }
    if (hasFourInARow(counterPlacements, Counter.O) || (hasFourInARow(counterPlacements, Counter.X))) {
      return true;
    }

    return false;
  }

  private boolean isBoardFull(Counter[][] counterPlacements) {
    // returns false if any space is empty, otherwise return true
      for (Counter[] counterPlacement : counterPlacements) {
          for (Counter counter : counterPlacement) {
              if (counter == null) {
                  return false;
              }
          }
      }
    return true;
  }

  private boolean hasFourInARow(Counter[][] counterPlacements, Counter counter) {
    for (int row = 0; row < counterPlacements.length; row++) {
      for (int col = 0; col < counterPlacements[row].length; col++) {
        if (counter == counterPlacements[row][col]) {
          // check horizontal right
          if (col + 3 < counterPlacements[row].length &&
          counter == counterPlacements[row][col + 1] &&
          counter == counterPlacements[row][col + 2] &&
          counter == counterPlacements[row][col + 3]) {
            return true;
          }
          // check vertical down
          if (row + 3 < counterPlacements.length &&
          counter == counterPlacements[row + 1][col] &&
          counter == counterPlacements[row + 2][col] &&
          counter == counterPlacements[row + 3][col]) {
            return true;
          }
          // check diagonal (down, right)
          if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length &&
          counter == counterPlacements[row + 1][col + 1] &&
          counter == counterPlacements[row + 2][col + 2] &&
          counter == counterPlacements[row + 3][col + 3]) {
            return true;
          }
          // check diagonal (down, left)
          if (row + 3 < counterPlacements.length && col - 3 <= 0 &&
          counter == counterPlacements[row + 1][col - 1] &&
          counter == counterPlacements[row + 2][col - 2] &&
          counter == counterPlacements[row + 3][col - 3]) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
