package com.thg.accelerator23.connectn.ai.superchargedrobbot;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.concurrent.TimeoutException;

public class SuperChargedRobBot extends Player {
  private static final int WIN_CONDITION = 4;
  private static final int MAX_DEPTH = 50;
  private static final long TIME_LIMIT_NANOS = 9_500_000_000L; // 9.5 seconds
  private static final int[][] DIRECTIONS = {{1,0}, {0,1}, {1,1}, {1,-1}}; // Right, Up, Diagonal-right, Diagonal-left

  private static final int WIN_SCORE = 1000000;
  private static final int OPEN_THREE_SCORE = 10000;
  private static final int DOUBLE_THREAT_SCORE = 50000;

  public SuperChargedRobBot(Counter counter) {
    super(counter, SuperChargedRobBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    long startTime = System.nanoTime();
    int bestMove = board.getConfig().getWidth() / 2; // Default to center
    int bestScore = Integer.MIN_VALUE;

    // Check for immediate winning moves or blocking moves
    int immediateMove = findImmediateMove(board);
    if (immediateMove != -1) {
      return immediateMove;
    }

    // Iterative deepening with time limit
    for (int depth = 1; depth <= MAX_DEPTH; depth++) {
      int currentBestMove = bestMove;
      int currentBestScore = bestScore;

      for (int column : getColumnOrder(board.getConfig().getWidth())) {
        if (!isValidMove(board, column)) continue;

        try {
          Board newBoard = new Board(board, column, getCounter());
          int score = minimaxWithTimeLimit(
                  newBoard,
                  depth,
                  false,
                  Integer.MIN_VALUE,
                  Integer.MAX_VALUE,
                  startTime
          );

          if (score > currentBestScore) {
            currentBestScore = score;
            currentBestMove = column;
          }
        } catch (TimeoutException e) {
          return bestMove;
        } catch (InvalidMoveException e) {
          // Skip invalid moves
        }
      }

      bestMove = currentBestMove;
      bestScore = currentBestScore;
    }

    return bestMove;
  }

  private int findImmediateMove(Board board) {
    Counter myCounter = getCounter();
    Counter opponent = myCounter.getOther();

    // Check for winning moves, then blocking moves
    for (Counter counter : new Counter[]{myCounter, opponent}) {
      for (int column = 0; column < board.getConfig().getWidth(); column++) {
        if (!isValidMove(board, column)) continue;

        try {
          Board newBoard = new Board(board, column, counter);
          if (hasWon(newBoard, counter)) {
            return column;
          }
        } catch (InvalidMoveException e) {
          continue;
        }
      }
    }
    return -1;
  }

  private int minimaxWithTimeLimit(
          Board board,
          int depth,
          boolean isMaximizing,
          int alpha,
          int beta,
          long startTime
  ) throws TimeoutException {
    if (System.nanoTime() - startTime > TIME_LIMIT_NANOS) {
      throw new TimeoutException();
    }

    Counter myCounter = getCounter();
    Counter opponent = myCounter.getOther();

    // Check terminal conditions
    if (hasWon(board, myCounter)) return 1000000;
    if (hasWon(board, opponent)) return -1000000;
    if (depth == 0 || isBoardFull(board)) {
      return evaluateBoard(board);
    }

    Counter currentCounter = isMaximizing ? myCounter : opponent;
    int bestValue = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

    for (int column : getColumnOrder(board.getConfig().getWidth())) {
      if (!isValidMove(board, column)) continue;

      try {
        Board newBoard = new Board(board, column, currentCounter);
        int eval = minimaxWithTimeLimit(
                newBoard,
                depth - 1,
                !isMaximizing,
                alpha,
                beta,
                startTime
        );

        if (isMaximizing) {
          bestValue = Math.max(bestValue, eval);
          alpha = Math.max(alpha, eval);
        } else {
          bestValue = Math.min(bestValue, eval);
          beta = Math.min(beta, eval);
        }

        if (beta <= alpha) break;
      } catch (InvalidMoveException e) {
        // Skip invalid moves
      }
    }

    return bestValue;
  }

  private int evaluateBoard(Board board) {
    int score = 0;
    Counter myCounter = getCounter();
    Counter opponent = myCounter.getOther();

    // Evaluate immediate threats and potential wins
    score += evaluateThreats(board, myCounter);
    score -= evaluateThreats(board, opponent) * 10; // Weight opponent threats slightly higher

    // Evaluate all possible lines
    for (int column = 0; column < board.getConfig().getWidth(); column++) {
      for (int row = 0; row < board.getConfig().getHeight(); row++) {
        Position pos = new Position(column, row);
        if (!board.hasCounterAtPosition(pos)) {
          // Evaluate empty positions for potential future threats
          score += evaluateEmptyPosition(board, pos, myCounter);
          score -= evaluateEmptyPosition(board, pos, opponent) * 1.1;
          continue;
        }

        Counter counter = board.getCounterAtPosition(pos);
        int multiplier = counter == myCounter ? 1 : -1;

        for (int[] dir : DIRECTIONS) {
          int length = getRunLength(board, pos, counter, dir[0], dir[1]);
          if (length >= 2) {
            score += evaluateRun(board, pos, length, dir[0], dir[1], multiplier);
          }
        }
      }
    }

    score += evaluateCenterControl(board);

    return score;
  }

  private int evaluateThreats(Board board, Counter counter) {
    int threatScore = 0;
    int doubleThreats = 0;

    // Check for open threes and double threats
    for (int column = 0; column < board.getConfig().getWidth(); column++) {
      for (int row = 0; row < board.getConfig().getHeight(); row++) {
        Position pos = new Position(column, row);
        if (!board.hasCounterAtPosition(pos)) continue;
        if (board.getCounterAtPosition(pos) != counter) continue;

        int openThreesAtPosition = 0;

        for (int[] dir : DIRECTIONS) {
          int length = getRunLength(board, pos, counter, dir[0], dir[1]);
          if (length == 3) {
            Position before = new Position(pos.getX() - dir[0], pos.getY() - dir[1]);
            Position after = new Position(pos.getX() + (length * dir[0]), pos.getY() + (length * dir[1]));

            boolean startOpen = isValidPosition(board, before) && !board.hasCounterAtPosition(before);
            boolean endOpen = isValidPosition(board, after) && !board.hasCounterAtPosition(after);

            if (startOpen && endOpen) {
              openThreesAtPosition++;
              threatScore += OPEN_THREE_SCORE;
            } else if (startOpen || endOpen) {
              threatScore += OPEN_THREE_SCORE / 2;
            }
          }
        }

        if (openThreesAtPosition >= 2) {
          doubleThreats++;
        }
      }
    }

    threatScore += doubleThreats * DOUBLE_THREAT_SCORE;
    return threatScore;
  }



  private int evaluateEmptyPosition(Board board, Position pos, Counter counter) {
    int score = 0;

    // Check if this position could complete or block a threat
    try {
      Board testBoard = new Board(board, pos.getX(), counter);

      // Check if this move would create an open three
      for (int[] dir : DIRECTIONS) {
        int length = getRunLength(testBoard, pos, counter, dir[0], dir[1]);
        if (length >= 3) {
          Position before = new Position(pos.getX() - dir[0], pos.getY() - dir[1]);
          Position after = new Position(pos.getX() + (length * dir[0]), pos.getY() + (length * dir[1]));

          boolean startOpen = isValidPosition(testBoard, before) && !testBoard.hasCounterAtPosition(before);
          boolean endOpen = isValidPosition(testBoard, after) && !testBoard.hasCounterAtPosition(after);

          if (startOpen && endOpen) {
            score += OPEN_THREE_SCORE / 2;
          }
        }
      }
    } catch (InvalidMoveException e) {
      return 0;
    }

    return score;
  }

  private int evaluateRun(Board board, Position start, int length, int dx, int dy, int multiplier) {
    int score = length * length * 10; // Base score for run length

    // Check if run is open-ended
    Position before = new Position(start.getX() - dx, start.getY() - dy);
    Position after = new Position(start.getX() + (length * dx), start.getY() + (length * dy));

    boolean startOpen = isValidPosition(board, before) && !board.hasCounterAtPosition(before);
    boolean endOpen = isValidPosition(board, after) && !board.hasCounterAtPosition(after);

    if (multiplier < 0 && startOpen && endOpen) {
      score *= 3;  // Triple the negative score for opponent's open-ended runs
    }

    // Bonus for our open-ended runs
    if (multiplier > 0) {
      if (startOpen || endOpen) score += length * 20;
      if (startOpen && endOpen) score += length * 30;
    }

    return score * multiplier;
  }

  private int evaluateCenterControl(Board board) {
    int score = 0;
    int centerColumn = board.getConfig().getWidth() / 2;
    Counter myCounter = getCounter();

    for (int row = 0; row < board.getConfig().getHeight(); row++) {
      Position centerPos = new Position(centerColumn, row);
      if (board.hasCounterAtPosition(centerPos)) {
        score += board.getCounterAtPosition(centerPos) == myCounter ? 30 : -30;
      }
    }
    return score;
  }

  private boolean hasWon(Board board, Counter counter) {
    for (int column = 0; column < board.getConfig().getWidth(); column++) {
      for (int row = 0; row < board.getConfig().getHeight(); row++) {
        Position pos = new Position(column, row);
        if (!board.hasCounterAtPosition(pos) || board.getCounterAtPosition(pos) != counter) continue;

        for (int[] dir : DIRECTIONS) {
          if (checkLine(board, pos, counter, WIN_CONDITION, dir[0], dir[1])) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean checkLine(Board board, Position start, Counter counter, int length, int dx, int dy) {
    for (int i = 0; i < length; i++) {
      Position pos = new Position(start.getX() + (dx * i), start.getY() + (dy * i));
      if (!isValidPosition(board, pos) || board.getCounterAtPosition(pos) != counter) {
        return false;
      }
    }
    return true;
  }

  private int getRunLength(Board board, Position start, Counter counter, int dx, int dy) {
    int length = 0;
    int x = start.getX();
    int y = start.getY();

    while (isValidPosition(board, new Position(x, y)) &&
            board.getCounterAtPosition(new Position(x, y)) == counter) {
      length++;
      x += dx;
      y += dy;
    }

    return length;
  }

  private int[] getColumnOrder(int width) {
    int[] columnOrder = new int[width];
    int center = width / 2;
    int left = center - 1;
    int right = center + 1;
    int index = 0;

    columnOrder[index++] = center;
    while (left >= 0 || right < width) {
      if (right < width) columnOrder[index++] = right++;
      if (left >= 0) columnOrder[index++] = left--;
    }

    return columnOrder;
  }

  private boolean isValidMove(Board board, int column) {
    return isValidPosition(board, new Position(column, board.getConfig().getHeight() - 1)) &&
            !board.hasCounterAtPosition(new Position(column, board.getConfig().getHeight() - 1));
  }

  private boolean isBoardFull(Board board) {
    for (int column = 0; column < board.getConfig().getWidth(); column++) {
      if (isValidMove(board, column)) return false;
    }
    return true;
  }

  private boolean isValidPosition(Board board, Position pos) {
    return pos.getX() >= 0 &&
            pos.getX() < board.getConfig().getWidth() &&
            pos.getY() >= 0 &&
            pos.getY() < board.getConfig().getHeight();
  }
}