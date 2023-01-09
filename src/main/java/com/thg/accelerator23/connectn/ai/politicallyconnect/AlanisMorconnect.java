package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.concurrent.ThreadLocalRandom;


public class AlanisMorconnect extends Player {
  public AlanisMorconnect(Counter counter) {
    //TODO: fill in your name here
    super(counter, AlanisMorconnect.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //int xMove = ThreadLocalRandom.current().nextInt(0, 9);
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}
