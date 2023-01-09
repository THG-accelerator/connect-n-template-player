package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class Connecty extends Player {
  public Connecty(Counter counter) {
    //TODO: fill in your name here
    super(counter, Connecty.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    Random rand = new Random();
    int randomNumber = rand.nextInt(10);
    return randomNumber;
  }
}
