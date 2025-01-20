package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
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


    private static class TimeoutException extends Exception {
    }

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

  private int minimax(Board board, int depth, boolean isMaximizing,
                      int alpha, int beta, Counter currentCounter) throws TimeoutException, InvalidMoveException, InvalidMoveException {
    if (isTimeUp()) {
      throw new TimeoutException();
    }
    if (depth == 0 || isGameOver(board)) {
      return evaluatePosition(board);
    }

    List<Integer> moves = getLegalMoves(board);
    if (isMaximizing) {
      int maxScore = Integer.MIN_VALUE;
      for (int move : moves) {
        Board newBoard = new Board(board, move, currentCounter);
        int score = minimax(newBoard, depth - 1, false, alpha, beta, currentCounter);
        maxScore = Math.max(maxScore, score);
        alpha = Math.max(alpha, score);
        if (beta <= alpha) {
          break;
        }
      }
      return maxScore;
    } else {
      int minScore = Integer.MAX_VALUE;
      for (int move : moves) {

        Board newBoard = new Board(board, move, currentCounter.getOther());
        int score = minimax(newBoard, depth - 1, true, alpha, beta, currentCounter.getOther());
        minScore = Math.min(minScore, score);
        beta = Math.min(beta, score);
        if (beta <= alpha) {
          break;
        }
      }
      return minScore;
    }
  }
    private int evaluatePosition(Board board) {
        Counter[][] counterPlacements = board.getCounterPlacements();
        Counter counter = getCounter();
        int score = 0;
        // Cases
        // 4 in a row: player's counter --> Integer.MAX_VALUE; opponent's counter --> Integer.MIN_VALUE
        if (hasFourInARow(counterPlacements, counter)) {
            return Integer.MAX_VALUE;
        } else if (hasFourInARow(counterPlacements, counter.getOther())) {
            return Integer.MIN_VALUE;
        }
        // 3 in a row and next is empty
        score += 100 * hasThreeInARow(counterPlacements, counter);
        score -= 50 * hasThreeInARow(counterPlacements, counter.getOther());
        // 2 in a row and both sides are empty
        score += 30 * hasTwoInARow(counterPlacements, counter);
        score -= 10 * hasTwoInARow(counterPlacements, counter.getOther());
        // Placeholder
        return score;
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

    private int hasThreeInARow(Counter[][] counterPlacements, Counter counter) {
        int count = 0;
        for (int row = 0; row < counterPlacements.length; row++) {
            for (int col = 0; col < counterPlacements[row].length; col++) {
                // check horizontal right
                if (col + 3 < counterPlacements[row].length &&
                        counter == counterPlacements[row][col + 1] &&
                        counter == counterPlacements[row][col + 2] &&
                        ((counterPlacements[row][col + 3] == null && counter == counterPlacements[row][col]) ||
                                (counterPlacements[row][col] == null && counter == counterPlacements[row][col + 3]))) {
                    count += 1;
                }
                // check vertical down
                if (row + 3 < counterPlacements.length &&
                        counter == counterPlacements[row + 1][col] &&
                        counter == counterPlacements[row + 2][col] &&
                        ((counterPlacements[row + 3][col] == null && counter == counterPlacements[row][col]) ||
                                (counterPlacements[row][col] == null && counter == counterPlacements[row + 3][col]))) {
                    count += 1;
                }
                // check diagonal (down, right)
                if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length &&
                        counter == counterPlacements[row + 1][col + 1] &&
                        counter == counterPlacements[row + 2][col + 2] &&
                        ((counterPlacements[row + 3][col + 3] == null && counter == counterPlacements[row][col]) ||
                                (counterPlacements[row][col] == null && counter == counterPlacements[row + 3][col + 3]))) {
                    count += 1;
                }
                // check diagonal (down, left)
                if (row + 3 < counterPlacements.length && col - 3 <= 0 &&
                        counter == counterPlacements[row + 1][col - 1] &&
                        counter == counterPlacements[row + 2][col - 2] &&
                        ((counterPlacements[row + 3][col - 3] == null && counter == counterPlacements[row][col])
                                || (counterPlacements[row][col] == null && counter == counterPlacements[row + 3][col - 3]))) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private int hasTwoInARow(Counter[][] counterPlacements, Counter counter) {
        int count = 0;
        for (int row = 0; row < counterPlacements.length; row++) {
            for (int col = 0; col < counterPlacements[row].length; col++) {
                if (counterPlacements[row][col] == null) {
                    // check horizontal right
                    if (col + 3 < counterPlacements[row].length &&
                            counter == counterPlacements[row][col + 1] &&
                            counter == counterPlacements[row][col + 2] &&
                            null == counterPlacements[row][col + 3]) {
                        count += 1;
                    }
                    // check vertical down
                    if (row + 3 < counterPlacements.length &&
                            counter == counterPlacements[row + 1][col] &&
                            counter == counterPlacements[row + 2][col] &&
                            null == counterPlacements[row + 3][col]) {
                        count += 1;
                    }
                    // check diagonal (down, right)
                    if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length &&
                            counter == counterPlacements[row + 1][col + 1] &&
                            counter == counterPlacements[row + 2][col + 2] &&
                            null == counterPlacements[row + 3][col + 3]) {
                        count += 1;
                    }
                    // check diagonal (down, left)
                    if (row + 3 < counterPlacements.length && col - 3 <= 0 &&
                            counter == counterPlacements[row + 1][col - 1] &&
                            counter == counterPlacements[row + 2][col - 2] &&
                            null == counterPlacements[row + 3][col - 3]) {
                        count += 1;
                    }
                }
            }
        }
        return count;
    }
}
