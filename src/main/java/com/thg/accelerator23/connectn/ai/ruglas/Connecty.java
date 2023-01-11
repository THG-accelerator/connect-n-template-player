package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.RandomAI;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.MiniMaxScoring;


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

    MiniMaxScoring miniMaxScoring = new MiniMaxScoring(this.getCounter());
    try {
      miniMaxScoring.miniMaxMove(board, true, 3, 0);
      System.out.println("MinMaxScoring");
      return miniMaxScoring.getBestColumn();
    } catch (InvalidMoveException e) {
      System.out.println("Invalid move connecty");
    }
    RandomAI randomAI = new RandomAI(this.getCounter());
    return randomAI.makeMove(board);
  }
}
