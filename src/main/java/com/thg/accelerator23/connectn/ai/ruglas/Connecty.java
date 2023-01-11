package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class Connecty extends Player {
  Counter opponentCounter;

  public Connecty(Counter counter) {
    super(counter, Connecty.class.getName());
    this.opponentCounter = getOpponent();
  }

  private Counter getOpponent() {
    return switch (this.getCounter()) {
      case X -> Counter.O;
      case O -> Counter.X;
      default -> null;
    };
  }

  @Override
  public int makeMove(Board board) {
    MiniMax miniMax = new MiniMax(getCounter());
    try {

      miniMax.miniMaxMove(board, true, 5);
      System.out.println("best position " + miniMax.getBestColumn());
    } catch (InvalidMoveException e) {
    }
    return miniMax.bestColumn;
  }
}
