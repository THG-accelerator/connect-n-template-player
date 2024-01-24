package com.thg.accelerator23.connectn.ai.connectfourdotjason;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.GameState;

import java.util.Map;


public class ConnectFourDotJason extends Player {

  final int MAX_POSSIBLE = Integer.MAX_VALUE;
  final int MIN_POSSIBLE = Integer.MIN_VALUE;
  final long WIN_SCORE_MAXIMISING_PLAYER = Integer.MAX_VALUE;
  final long WIN_SCORE_MINIMISING_PLAYER = -100_000_000;
  final long OPEN_THREE_SCORE_MAXIMISING_PLAYER = 6_000_000;
  final long OPEN_THREE_SCORE_MINIMISING_PLAYER = -100_000;
  final long TRIPLE_SCORE_MAXIMISING_PLAYER = 5_000;
  final long TRIPLE_SCORE_MINIMISING_PLAYER = -700;
  final long DOUBLE_SCORE_MAXIMISING_PLAYER = 10;
  final long DOUBLE_SCORE_MINIMISING_PLAYER = -1;
  public ConnectFourDotJason(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectFourDotJason.class.getName());
  }

  @Override
  public int makeMove(Board board) {

    int curMaximizingMove = -1, curMaxEval = MIN_POSSIBLE;
    int stepsAhead = 5;
    for (int i = 0; i < board.getConfig().getWidth(); i++) {
      try {
        Board newBoard = new Board(board, i, this.getCounter());
        int miniMax = getMiniMax(newBoard, stepsAhead, MIN_POSSIBLE, MAX_POSSIBLE, false);
        System.out.println(miniMax);
        if (miniMax > curMaxEval) {
          curMaxEval = miniMax;
          curMaximizingMove = i;
        }
      } catch (InvalidMoveException ime) {}
    }
    return curMaximizingMove;
  }

  private int getMiniMax(Board board, int stepsAhead, int alpha, int beta, boolean maximisingPlayer) {
    GameState gameState = new BoardAnalyser(board.getConfig()).calculateGameState(board);
    if (stepsAhead == 0 || gameState.isEnd()) {
      return evaluateGame(board);
    }
    if (maximisingPlayer) {
      int maxEval = MIN_POSSIBLE;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, maximisingPlayer ? this.getCounter() : this.getCounter().getOther());
          int intermediateEval = getMiniMax(newBoard, stepsAhead - 1, alpha, beta, false);
          maxEval = Math.max(maxEval, intermediateEval);
          alpha = Math.max(alpha, intermediateEval);
          if (alpha >= beta) {
            break;
          }
        } catch (InvalidMoveException ime) {}
      }
      return maxEval;
    } else {
      int minEval = MAX_POSSIBLE;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, maximisingPlayer ? this.getCounter() : this.getCounter().getOther());
          int intermediateEval = getMiniMax(newBoard, stepsAhead - 1, alpha, beta, true);
          minEval = Math.min(minEval, intermediateEval);
          beta = Math.min(beta, intermediateEval);
          if (alpha >= beta) {
            break;
          }
        } catch (InvalidMoveException ime) {}
      }
      return minEval;
    }
  }

  // use this.getCounter();
  private int evaluateGame(Board board) {
    int score = 0;
    score += evaluateWins(board, WIN_SCORE_MAXIMISING_PLAYER, WIN_SCORE_MINIMISING_PLAYER)
            + evaluateOpenThrees(board, OPEN_THREE_SCORE_MAXIMISING_PLAYER, OPEN_THREE_SCORE_MINIMISING_PLAYER)
            + evaluateThrees(board, TRIPLE_SCORE_MAXIMISING_PLAYER, TRIPLE_SCORE_MINIMISING_PLAYER)
            + evaluateTwos(board, DOUBLE_SCORE_MAXIMISING_PLAYER, DOUBLE_SCORE_MINIMISING_PLAYER);

    return score;
  }

  private long evaluateTwos(Board board, long scoreForTwosMaximisingPlayer, long scoreForTwosMinimisingPlayer) {
    int score = 0;
    score += evaluateTwosHorizontal(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosVertical(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosDiagonal1(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosDiagonal2(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer);

    return score;
  }

  private long evaluateTwosDiagonal2(Board board, long scoreForTwosMaximisingPlayer, long scoreForTwosMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 3; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 2; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 3; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y + i));
          if (curCounter == null) {
            nullCount++;
          } else if (curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 2) {
            score += scoreForTwosMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForTwosMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateTwosDiagonal1(Board board, long scoreForTwosMaximisingPlayer, long scoreForTwosMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 2; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 2; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 3; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y - i));
          if (curCounter == null) {
            nullCount++;
          } else if (curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 2) {
            score += scoreForTwosMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForTwosMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateTwosVertical(Board board, long scoreForTwosMaximisingPlayer, long scoreForTwosMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 2; y--) {
      for (int x = 0; x < board.getConfig().getWidth(); x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 3; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x, y - i));
          if (curCounter == null) {
            nullCount++;
          } else if (curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 2) {
            score += scoreForTwosMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForTwosMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateTwosHorizontal(Board board, long scoreForTwosMaximisingPlayer, long scoreForTwosMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 2; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 3; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y));
          if (curCounter == null) {
            nullCount++;
          } else if (curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 2) {
            score += scoreForTwosMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForTwosMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateThrees(Board board, long scoreForThreesMaximisingPlayer, long scoreForThreesMinimisingPlayer) {
    int score = 0;
    score += evaluateThreesHorizontal(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesVertical(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesDiagonal1(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesDiagonal2(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer);

    return score;
  }

  private long evaluateThreesDiagonal1(Board board, long scoreForThreesMaximisingPlayer, long scoreForThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 3; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          if (board.getCounterAtPosition(new Position(x + i, y - i)) == null) {
            nullCount++;
          } else if (board.getCounterAtPosition(new Position(x + i, y - i)).equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 3) {
            score += scoreForThreesMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForThreesMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateThreesDiagonal2(Board board, long scoreForThreesMaximisingPlayer, long scoreForThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 4; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          if (board.getCounterAtPosition(new Position(x + i, y + i)) == null) {
            nullCount++;
          } else if (board.getCounterAtPosition(new Position(x + i, y + i)).equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 3) {
            score += scoreForThreesMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForThreesMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateThreesVertical(Board board, long scoreForThreesMaximisingPlayer, long scoreForThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 3; y--) {
      for (int x = 0; x < board.getConfig().getWidth(); x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          if (board.getCounterAtPosition(new Position(x, y - i)) == null) {
            nullCount++;
          } else if (board.getCounterAtPosition(new Position(x, y - i)).equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 3) {
            score += scoreForThreesMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForThreesMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateThreesHorizontal(Board board, long scoreForThreesMaximisingPlayer, long scoreForThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int nullCount = 0;
        int ownCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          if (board.getCounterAtPosition(new Position(x + i, y)) == null) {
            nullCount++;
          } else if (board.getCounterAtPosition(new Position(x + i, y)).equals(this.getCounter())) {
            ownCounterCount++;
          }
        }
        if (nullCount == 1) {
          if (ownCounterCount == 3) {
            score += scoreForThreesMaximisingPlayer;
          } else if (ownCounterCount == 0) {
            score += scoreForThreesMinimisingPlayer;
          }
        }
      }
    }
    return score;
  }

  private long evaluateOpenThrees(Board board, long scoreForOpenThreesMaximisingPlayer,
                                  long scoreForOpenThreesMinimisingPlayer) {
    int score = 0;
    score += evaluateHorizontalOpenThrees(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer)
            + evaluateDiagonalOpenThrees1(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer)
            + evaluateDiagonalOpenThrees2(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer);
    return score;
  }

  private long evaluateHorizontalOpenThrees(Board board, long scoreForOpenThreesMaximisingPlayer,
                                            long scoreForOpenThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 4; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        if (board.getCounterAtPosition(new Position(x, y)) == null &&
                board.getCounterAtPosition(new Position(x + 4, y)) == null) {
          for (int i = 1; i < 4; i++) {
            Counter curCounter = board.getCounterAtPosition(new Position(x + i, y));
            if (curCounter != null && curCounter.equals(this.getCounter())) {
              ownCounterCount++;
            } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
              otherCounterCount++;
            }
          }
        }
        if (ownCounterCount == 3) {
          score += scoreForOpenThreesMaximisingPlayer;
        } else if (otherCounterCount == 3) {
          score += scoreForOpenThreesMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateDiagonalOpenThrees1(Board board, long scoreForOpenThreesMaximisingPlayer,
                                           long scoreForOpenThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 4; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 4; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        if (board.getCounterAtPosition(new Position(x, y)) == null &&
                board.getCounterAtPosition(new Position(x + 4, y - 4)) == null) {
          for (int i = 1; i < 4; i++) {
            Counter curCounter = board.getCounterAtPosition(new Position(x + i, y - i));
            if (curCounter != null && curCounter.equals(this.getCounter())) {
              ownCounterCount++;
            } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
              otherCounterCount++;
            }
          }
        }
        if (ownCounterCount == 3) {
          score += scoreForOpenThreesMaximisingPlayer;
        } else if (otherCounterCount == 3) {
          score += scoreForOpenThreesMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateDiagonalOpenThrees2(Board board, long scoreForOpenThreesMaximisingPlayer,
                                           long scoreForOpenThreesMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 5; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 4; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        if (board.getCounterAtPosition(new Position(x, y)) == null &&
                board.getCounterAtPosition(new Position(x + 4, y + 4)) == null) {
          for (int i = 1; i < 4; i++) {
            Counter curCounter = board.getCounterAtPosition(new Position(x + i, y + i));
            if (curCounter != null && curCounter.equals(this.getCounter())) {
              ownCounterCount++;
            } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
              otherCounterCount++;
            }
          }
        }
        if (ownCounterCount == 3) {
          score += scoreForOpenThreesMaximisingPlayer;
        } else if (otherCounterCount == 3) {
          score += scoreForOpenThreesMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateWins(Board board, long winScoreForMaximisingPlayer, long winScoreForMinimisingPlayer) {
    int score = 0;
    GameState gameState = new BoardAnalyser(board.getConfig()).calculateGameState(board);
    if (gameState.isWin()) {
      score += evaluateHorizontalWins(board, winScoreForMaximisingPlayer, winScoreForMinimisingPlayer)
              + evaluateVerticalWins(board, winScoreForMaximisingPlayer, winScoreForMinimisingPlayer)
              + evaluateDiagonalWins1(board, winScoreForMaximisingPlayer, winScoreForMinimisingPlayer)
              + evaluateDiagonalWins2(board, winScoreForMaximisingPlayer, winScoreForMinimisingPlayer);
    }
    return score;
  }

  private long evaluateHorizontalWins(Board board, long winScoreForMaximisingPlayer, long winScoreForMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y));
          if (curCounter != null && curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
            otherCounterCount++;
          }
        }
        if (ownCounterCount == 4) {
          score += winScoreForMaximisingPlayer;
        } else if (otherCounterCount == 4) {
          score += winScoreForMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateVerticalWins(Board board, long winScoreForMaximisingPlayer, long winScoreForMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 3; y--) {
      for (int x = 0; x < board.getConfig().getWidth(); x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x, y - i));
          if (curCounter != null && curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
            otherCounterCount++;
          }
        }
        if (ownCounterCount == 4) {
          score += winScoreForMaximisingPlayer;
        } else if (otherCounterCount == 4) {
          score += winScoreForMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateDiagonalWins1(Board board, long winScoreForMaximisingPlayer, long winScoreForMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 1; y >= 3; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y - i));
          if (curCounter != null && curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
            otherCounterCount++;
          }
        }
        if (ownCounterCount == 4) {
          score += winScoreForMaximisingPlayer;
        } else if (otherCounterCount == 4) {
          score += winScoreForMinimisingPlayer;
        }
      }
    }
    return score;
  }

  private long evaluateDiagonalWins2(Board board, long winScoreForMaximisingPlayer, long winScoreForMinimisingPlayer) {
    int score = 0;
    for (int y = board.getConfig().getHeight() - 4; y >= 0; y--) {
      for (int x = 0; x < board.getConfig().getWidth() - 3; x++) {
        int ownCounterCount = 0;
        int otherCounterCount = 0;
        for (int i = 0; i < 4; i++) {
          Counter curCounter = board.getCounterAtPosition(new Position(x + i, y + i));
          if (curCounter != null && curCounter.equals(this.getCounter())) {
            ownCounterCount++;
          } else if (curCounter != null && curCounter.equals(this.getCounter().getOther())) {
            otherCounterCount++;
          }
        }
        if (ownCounterCount == 4) {
          score += winScoreForMaximisingPlayer;
        } else if (otherCounterCount == 4) {
          score += winScoreForMinimisingPlayer;
        }
      }
    }
    return score;
  }
}
