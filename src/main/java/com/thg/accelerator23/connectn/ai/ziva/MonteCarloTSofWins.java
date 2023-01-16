package com.thg.accelerator23.connectn.ai.ziva;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class MonteCarloTSofWins extends Player {
  public MonteCarloTSofWins(Counter counter) {
    //TODO: fill in your name here
    super(counter, MonteCarloTSofWins.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    MonteCarloTS ai = new MonteCarloTS(board, 5000);

    return ai.getOptimalMove();
  }
}
