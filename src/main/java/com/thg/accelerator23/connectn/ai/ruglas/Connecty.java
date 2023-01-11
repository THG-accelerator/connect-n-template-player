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
<<<<<<< HEAD
    MiniMax miniMax = new MiniMax(getCounter());
    try {

      miniMax.miniMaxMove(board, true, 5);
      System.out.println("best position " + miniMax.getBestColumn());
    } catch (InvalidMoveException e) {
    }
    Random rand = new Random();
    int randomNumber = rand.nextInt(board.getConfig().getWidth());
=======
>>>>>>> 7fe2fca42d1e1478483b5c5a8c2ff8b9ad5bd864

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
<<<<<<< HEAD
    CheckMoveForGameOver checkForLosingMove = null;
    try {
      checkForLosingMove = new CheckMoveForGameOver(board, opponentCounter);
    } catch (InvalidMoveException e) {
    }
    if (checkForLosingMove.isGameOver()) {
      return checkForLosingMove.getPlayLocation();
    }
    return miniMax.bestColumn;
=======
>>>>>>> 7fe2fca42d1e1478483b5c5a8c2ff8b9ad5bd864
  }
}
