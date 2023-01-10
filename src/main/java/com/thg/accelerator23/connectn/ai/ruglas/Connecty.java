package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.Arrays;
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

    ChooseMove moveChooser = new ChooseMove(board, this.getCounter());

    try {
      moveChooser.findWinPosition();
    } catch (InvalidMoveException e) {
      throw new RuntimeException(e);
    }
    if (moveChooser.getPlayLocation() != null) {
      return moveChooser.getPlayLocation();
    } else {
      try {
        moveChooser.findBlockPosition();
      } catch (InvalidMoveException e) {
        throw new RuntimeException(e);
      }
      if (moveChooser.getPlayLocation() != null) {
        return moveChooser.getPlayLocation();
      } else {
        int randomNumber;
        do {
          Random rand = new Random();
          randomNumber = rand.nextInt(board.getConfig().getWidth());
        } while (TestMove.doesMoveGiveOpponentWin(board, randomNumber, this.getCounter()));
        return randomNumber;
      }
    }
  }
}
