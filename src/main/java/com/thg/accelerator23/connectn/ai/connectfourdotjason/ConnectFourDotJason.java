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
    return minimax(board, true);
  }

  public int minimax(Board board, boolean thisTurn) {
    GameState gameState = new BoardAnalyser(board.getConfig()).calculateGameState(board);
    if (gameState.isEnd()) {
      if (gameState.isDraw()) {
        return 0;
      } else if (gameState.getWinner().equals(this.getCounter())) {
        return 1;
      } else {
        return -1;
      }
    }
    if (thisTurn) {
      int maxEval = (int) Double.NEGATIVE_INFINITY;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, this.getCounter());
          int eval = minimax(newBoard, false);
          maxEval = (int) Math.max(eval, maxEval);
        } catch (InvalidMoveException ime) {
          ime.printStackTrace();
        }
      }
      return maxEval;
    } else {
      int minEval = (int) Double.POSITIVE_INFINITY;
      for (int i = 0; i < board.getConfig().getWidth(); i++) {
        try {
          Board newBoard = new Board(board, i, this.getCounter());
          int eval = minimax(newBoard, true);
          minEval = (int) Math.max(eval, minEval);
        } catch (InvalidMoveException ime) {
          ime.printStackTrace();
        }
      }
      return minEval;
    }
  }
}
