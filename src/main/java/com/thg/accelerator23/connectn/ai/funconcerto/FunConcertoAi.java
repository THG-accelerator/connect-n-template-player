package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Player;


public class FunConcertoAi extends Player {
  public FunConcertoAi(Counter counter) {
    super(counter, FunConcertoAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    GameConfig config = new GameConfig(10,8,4);
    BoardAnalyser analyzer = new BoardAnalyser(config);
    GameState state = analyzer.calculateGameState(board);
    return 4;
  }
}
