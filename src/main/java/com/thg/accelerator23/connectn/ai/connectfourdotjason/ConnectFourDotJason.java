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
      return evaluateFinalGameState(gameState);
    }
    return thisTurn ? evaluateIntermediateGameState(board, true) :
              evaluateIntermediateGameState(board, false);
  }

  private int evaluateFinalGameState(GameState gameState) {
    if (gameState.isDraw()) {
      return 0;
    } else if (gameState.getWinner().getStringRepresentation()
            .equals(this.getCounter().getStringRepresentation())) {
      return 1;
    } else {
      return -1;
    }
  }

  private int evaluateIntermediateGameState(Board board, boolean thisTurn) {
    int minMaxEval = thisTurn ? (int) Double.NEGATIVE_INFINITY : (int) Double.POSITIVE_INFINITY;
    for (int i = 0; i < board.getConfig().getWidth(); i++) {
      try {
        Board newBoard = new Board(board, i, thisTurn ? this.getCounter() : this.getCounter().getOther());
        int eval = minimax(newBoard, !thisTurn);
        minMaxEval = thisTurn ? Math.max(minMaxEval, eval) : Math.min(minMaxEval, eval);
      } catch (InvalidMoveException ime) {
        ime.printStackTrace();
      }
    }
    return minMaxEval;
  }
}
