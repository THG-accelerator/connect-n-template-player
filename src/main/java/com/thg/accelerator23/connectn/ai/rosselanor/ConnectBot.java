package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.concurrent.ThreadLocalRandom;


public class ConnectBot extends Player {
  public ConnectBot(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    return ThreadLocalRandom.current().nextInt(0, 10);
  }
}
