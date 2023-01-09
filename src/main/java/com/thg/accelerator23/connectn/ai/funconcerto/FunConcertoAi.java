package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;

public class FunConcertoAi extends Player {
  public FunConcertoAi(Counter counter) {
    super(counter, FunConcertoAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    GameConfig config = new GameConfig(10,8,4);
    BoardAnalyser analyzer = new BoardAnalyser(config);
    int bestScore = 0;
    int[] moveScores = new int[10];
    for (int i = 0; i < 10; i++) {
      try {
        Board copyBoard = new Board(board, i, this.getCounter());
        moveScores[i] = scoreBoard(copyBoard, analyzer);
      } catch (InvalidMoveException e) {
        System.out.println("Invalid move");
      }
    }

    for (int moveScore : moveScores) {
      bestScore = Math.max(bestScore, moveScore);
    }

    for(int i = 0; i < moveScores.length; i++) {
      if(moveScores[i] == bestScore) return i;
    }
    return -1;
  }

  public int scoreBoard(Board board, BoardAnalyser analyzer) {
    GameState state = analyzer.calculateGameState(board);
    if(state.isWin() && state.getWinner() == this.getCounter()){
      return 999999999;
    }
    return 0;
  }
}
