package com.thg.accelerator23.connectn.ai.TwinningAI;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo.MCTS;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TwinningAI extends Player {
  public TwinningAI(Counter counter) {
    //TODO: fill in your name here
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