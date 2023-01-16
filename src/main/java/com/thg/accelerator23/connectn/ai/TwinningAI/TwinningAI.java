package com.thg.accelerator23.connectn.ai.TwinningAI;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo.MCTS;


public class TwinningAI extends Player {
  public TwinningAI(Counter counter) {
    super(counter, TwinningAI.class.getName());
  }

  @Override
  public int makeMove(Board board) {

    MCTS MCTS = new MCTS();

    try {
      return MCTS.MCTS_Searcher(board, getCounter());
    } catch (InvalidMoveException e) {
      throw new RuntimeException(e);
    }
  }
}