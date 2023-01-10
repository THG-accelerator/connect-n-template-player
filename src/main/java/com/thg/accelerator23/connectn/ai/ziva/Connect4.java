package com.thg.accelerator23.connectn.ai.ziva;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class Connect4 extends Player {
  public Connect4(Counter counter) {
    //TODO: fill in your name here
    super(counter, Connect4.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    Random rand = new Random();
    int easyComputerMove;
    easyComputerMove = rand.nextInt(10);
    return easyComputerMove;
  }
}
