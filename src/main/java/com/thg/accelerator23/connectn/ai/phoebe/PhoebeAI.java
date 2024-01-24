package com.thg.accelerator23.connectn.ai.phoebe;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class PhoebeAI extends Player {
  public PhoebeAI(Counter counter) {
    //TODO: fill in your name here
    super(counter, PhoebeAI.class.getName());
  }

  @Override
  public int makeMove(Board board) {

    return 4;
  }
}
