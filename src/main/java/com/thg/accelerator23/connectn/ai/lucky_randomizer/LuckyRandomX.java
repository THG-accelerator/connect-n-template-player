package com.thg.accelerator23.connectn.ai.lucky_randomizer;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class LuckyRandomX extends Player {
  public LuckyRandomX(Counter counter) {
    //TODO: fill in your name here
    super(counter, LuckyRandomX.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}
