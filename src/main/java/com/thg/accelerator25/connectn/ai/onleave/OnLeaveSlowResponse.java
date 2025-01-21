package com.thg.accelerator25.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OnLeaveSlowResponse extends Player {

    // timeout value, gives 500ms buffer
    private static final long TIMEOUT_SECONDS = 9500;
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
        int bestMoveFound = 4;
        int bestScoreFound = Integer.MIN_VALUE;
        int bestDepthFound = 0;
        // for a given depth, first value is the best move, second value is the best score
        List<Integer> depthBest;

        List<Integer> legalMoves = getLegalMoves(board);

        for (int depth = 7; !isTimeUp(); depth++) {
            try {
                depthBest = searchMovesAtDepth(board, depth, legalMoves);
                if (depthBest.get(1) > bestScoreFound) {
                    bestMoveFound = depthBest.get(0);
                    bestScoreFound = depthBest.get(1);
                    bestDepthFound = depth;
                }
            } catch (TimeoutException e) {
                break;
            }
        }
        System.out.println("Best move found: " + bestMoveFound);
        System.out.println("Best score found: " + bestScoreFound);
        System.out.println("Best depth found: " + bestDepthFound);
        return bestMoveFound;
    }


    private List<Integer> searchMovesAtDepth(Board board, int depth, List<Integer> legalMoves) throws TimeoutException {
        int depthBestScore = Integer.MIN_VALUE;
        int depthBestMove = 4;
        System.out.println("Searching for moves at depth " + depth);
        for (int move : legalMoves) {
            int score;
            if (isTimeUp()) {
                System.out.println("Timeout, use previous best result");
                throw new TimeoutException();
            }
            try {
                Board newBoard = new Board(board, move, getCounter());
                score = minimax(newBoard, depth-1, false, Integer.MIN_VALUE, Integer.MAX_VALUE, getCounter().getOther());
            } catch (InvalidMoveException e){
                break;
            }

            if (score > depthBestScore) {
                depthBestScore = score;
                depthBestMove = move;
            }
        }
        System.out.println("Best score found: " + depthBestScore);
        System.out.println("Best move found: " + depthBestMove);
        return Arrays.asList(depthBestMove, depthBestScore);
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
                      int alpha, int beta, Counter currentCounter) throws TimeoutException, InvalidMoveException {
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
        int score = minimax(newBoard, depth - 1, false, alpha, beta, currentCounter.getOther());
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
        Board newBoard = new Board(board, move, currentCounter);
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
                    if (row + 3 < counterPlacements.length && col - 3 >= 0 &&
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
                // Horizontal right check (4 consecutive cells)
                if (col + 3 < counterPlacements[row].length) {
                    int filled = 0;
                    // Count how many of the 4 positions are filled with the same counter
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row][col + i]) {
                            filled++;
                        }
                    }
                    // If there are exactly 3 filled and 1 empty, count it
                    if (filled == 3 && counterPlacements[row][col + 3] == null) {
                        count += 1;
                    }
                }

                // Vertical down check (4 consecutive cells)
                if (row + 3 < counterPlacements.length) {
                    int filled = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col]) {
                            filled++;
                        }
                    }
                    if (filled == 3 && counterPlacements[row + 3][col] == null) {
                        count += 1;
                    }
                }

                // Diagonal (down-right) check (4 consecutive cells)
                if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length) {
                    int filled = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col + i]) {
                            filled++;
                        }
                    }
                    if (filled == 3 && counterPlacements[row + 3][col + 3] == null) {
                        count += 1;
                    }
                }

                // Diagonal (down-left) check (4 consecutive cells)
                if (row + 3 < counterPlacements.length && col - 3 >= 0) {
                    int filled = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col - i]) {
                            filled++;
                        }
                    }
                    if (filled == 3 && counterPlacements[row + 3][col - 3] == null) {
                        count += 1;
                    }
                }
            }
        }

        return count;
    }

    private int hasTwoInARow(Counter[][] counterPlacements, Counter counter) {
        int count = 0;
        Counter opponentCounter = (counter == Counter.O) ? Counter.X : Counter.O;

        for (int row = 0; row < counterPlacements.length; row++) {
            for (int col = 0; col < counterPlacements[row].length; col++) {
                // Horizontal right check (4 consecutive cells)
                if (col + 3 < counterPlacements[row].length) {
                    int filled = 0;
                    int emptyOrOpponent = 0;
                    // Count how many of the 4 positions are filled with the same counter
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row][col + i]) {
                            filled++;
                        } else if (counterPlacements[row][col + i] == null || counterPlacements[row][col + i] == opponentCounter) {
                            emptyOrOpponent++;
                        }
                    }
                    // If there are exactly 2 filled and 2 empty or opponent, count it
                    if (filled == 2 && emptyOrOpponent == 2) {
                        count += 1;
                    }
                }

                // Vertical down check (4 consecutive cells)
                if (row + 3 < counterPlacements.length) {
                    int filled = 0;
                    int emptyOrOpponent = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col]) {
                            filled++;
                        } else if (counterPlacements[row + i][col] == null || counterPlacements[row + i][col] == opponentCounter) {
                            emptyOrOpponent++;
                        }
                    }
                    if (filled == 2 && emptyOrOpponent == 2) {
                        count += 1;
                    }
                }

                // Diagonal (down-right) check (4 consecutive cells)
                if (row + 3 < counterPlacements.length && col + 3 < counterPlacements[row].length) {
                    int filled = 0;
                    int emptyOrOpponent = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col + i]) {
                            filled++;
                        } else if (counterPlacements[row + i][col + i] == null || counterPlacements[row + i][col + i] == opponentCounter) {
                            emptyOrOpponent++;
                        }
                    }
                    if (filled == 2 && emptyOrOpponent == 2) {
                        count += 1;
                    }
                }

                // Diagonal (down-left) check (4 consecutive cells)
                if (row + 3 < counterPlacements.length && col - 3 >= 0) {
                    int filled = 0;
                    int emptyOrOpponent = 0;
                    for (int i = 0; i < 4; i++) {
                        if (counter == counterPlacements[row + i][col - i]) {
                            filled++;
                        } else if (counterPlacements[row + i][col - i] == null || counterPlacements[row + i][col - i] == opponentCounter) {
                            emptyOrOpponent++;
                        }
                    }
                    if (filled == 2 && emptyOrOpponent == 2) {
                        count += 1;
                    }
                }
            }
        }

        return count;
    }
}
