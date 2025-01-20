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
    // Cases
    // 4 in a row: player's counter --> Integer.MAX_VALUE; opponent's counter --> Integer.MIN_VALUE

    // Placeholder
    return 0;
  }
}
