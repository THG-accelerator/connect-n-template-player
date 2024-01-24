package com.thg.accelerator23.connectn.ai.connectfourdotjason;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.GameState;

import java.util.Map;


public class ConnectFourDotJason extends Player {
  public ConnectFourDotJason(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectFourDotJason.class.getName());
  }

  @Override
  public int makeMove(Board board) {

    int curMaximizingMove = -1, curMaxEval = (int) Double.NEGATIVE_INFINITY;
    int stepsAhead = 6;
    for (int i = 0; i < board.getConfig().getWidth(); i++) {
      try {
        Board newBoard = new Board(board, i, this.getCounter());
        int miniMax = getMiniMax(newBoard, stepsAhead, false);
        System.out.println(miniMax);
        if (miniMax > curMaxEval) {
          curMaxEval = miniMax;
          curMaximizingMove = i;
        }
      } catch (InvalidMoveException ime) {}
    }
    return curMaximizingMove;
  }

  private int getMiniMax(Board board, int stepsAhead, boolean maximisingPlayer) {
    GameState gameState = new BoardAnalyser(board.getConfig()).calculateGameState(board);
    if (stepsAhead == 0 || gameState.isEnd()) {
      return evaluateGame(board);
    }
    if (maximisingPlayer) {
      int maxEval = (int) Double.NEGATIVE_INFINITY;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, maximisingPlayer ? this.getCounter() : this.getCounter().getOther());
          maxEval = Math.max(maxEval, getMiniMax(newBoard, stepsAhead - 1, false));
        } catch (InvalidMoveException ime) {}
      }
      return maxEval;
    } else {
      int minEval = (int) Double.POSITIVE_INFINITY;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, maximisingPlayer ? this.getCounter() : this.getCounter().getOther());
          minEval = Math.min(minEval, getMiniMax(newBoard, stepsAhead - 1, true));
        } catch (InvalidMoveException ime) {}
      }
      return minEval;
    }
  }

  // use this.getCounter();
  private int evaluateGame(Board board) {
    int winScoreForMaximisingPlayer = 10000;
    int winScoreForMinimisingPlayer = -1000;
    int scoreForOpenThreesMaximisingPlayer = 500;
    int scoreForOpenThreesMinimisingPlayer = -300;
    int scoreForThreesMaximisingPlayer = 100;
    int scoreForThreesMinimisingPlayer = -50;
    int scoreForTwosMaximisingPlayer = 6;
    int scoreForTwosMinimisingPlayer = -2;
    int score = 0;
    score += evaluateWins(board, winScoreForMaximisingPlayer, winScoreForMinimisingPlayer)
            + evaluateOpenThrees(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer)
            + evaluateThrees(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer)
            + evaluateTwos(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer);

    return score;
  }

  private int evaluateTwos(Board board, int scoreForTwosMaximisingPlayer, int scoreForTwosMinimisingPlayer) {
    int score = 0;
    score += evaluateTwosHorizontal(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosVertical(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosDiagonal1(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer) +
            evaluateTwosDiagonal2(board, scoreForTwosMaximisingPlayer, scoreForTwosMinimisingPlayer);

    return score;
  }

  private int evaluateTwosDiagonal2(Board board, int scoreForTwosMaximisingPlayer, int scoreForTwosMinimisingPlayer) {
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

  private int evaluateTwosDiagonal1(Board board, int scoreForTwosMaximisingPlayer, int scoreForTwosMinimisingPlayer) {
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

  private int evaluateTwosVertical(Board board, int scoreForTwosMaximisingPlayer, int scoreForTwosMinimisingPlayer) {
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

  private int evaluateTwosHorizontal(Board board, int scoreForTwosMaximisingPlayer, int scoreForTwosMinimisingPlayer) {
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

  private int evaluateThrees(Board board, int scoreForThreesMaximisingPlayer, int scoreForThreesMinimisingPlayer) {
    int score = 0;
    score += evaluateThreesHorizontal(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesVertical(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesDiagonal1(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer) +
            evaluateThreesDiagonal2(board, scoreForThreesMaximisingPlayer, scoreForThreesMinimisingPlayer);

    return score;
  }

  private int evaluateThreesDiagonal1(Board board, int scoreForThreesMaximisingPlayer, int scoreForThreesMinimisingPlayer) {
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

  private int evaluateThreesDiagonal2(Board board, int scoreForThreesMaximisingPlayer, int scoreForThreesMinimisingPlayer) {
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

  private int evaluateThreesVertical(Board board, int scoreForThreesMaximisingPlayer, int scoreForThreesMinimisingPlayer) {
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

  private int evaluateThreesHorizontal(Board board, int scoreForThreesMaximisingPlayer, int scoreForThreesMinimisingPlayer) {
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

  private int evaluateOpenThrees(Board board, int scoreForOpenThreesMaximisingPlayer,
                                 int scoreForOpenThreesMinimisingPlayer) {
    int score = 0;
    score += evaluateHorizontalOpenThrees(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer)
            + evaluateDiagonalOpenThrees1(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer)
            + evaluateDiagonalOpenThrees2(board, scoreForOpenThreesMaximisingPlayer, scoreForOpenThreesMinimisingPlayer);
    return score;
  }

  private int evaluateHorizontalOpenThrees(Board board, int scoreForOpenThreesMaximisingPlayer,
                                           int scoreForOpenThreesMinimisingPlayer) {
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

  private int evaluateDiagonalOpenThrees1(Board board, int scoreForOpenThreesMaximisingPlayer,
                                          int scoreForOpenThreesMinimisingPlayer) {
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

  private int evaluateDiagonalOpenThrees2(Board board, int scoreForOpenThreesMaximisingPlayer,
                                          int scoreForOpenThreesMinimisingPlayer) {
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

  private int evaluateWins(Board board, int winScoreForMaximisingPlayer, int winScoreForMinimisingPlayer) {
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

  private int evaluateHorizontalWins(Board board, int winScoreForMaximisingPlayer, int winScoreForMinimisingPlayer) {
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

  private int evaluateVerticalWins(Board board, int winScoreForMaximisingPlayer, int winScoreForMinimisingPlayer) {
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

  private int evaluateDiagonalWins1(Board board, int winScoreForMaximisingPlayer, int winScoreForMinimisingPlayer) {
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

  private int evaluateDiagonalWins2(Board board, int winScoreForMaximisingPlayer, int winScoreForMinimisingPlayer) {
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
