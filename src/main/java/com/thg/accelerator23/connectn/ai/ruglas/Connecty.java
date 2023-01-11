package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class Connecty extends Player {
  Counter opponentCounter;
  public Connecty(Counter counter) {
    //TODO: fill in your name here
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
    Random rand = new Random();
    int randomNumber = rand.nextInt(board.getConfig().getWidth());

    CheckMoveForGameOver checkForWinningMove = null;
    try {
      checkForWinningMove = new CheckMoveForGameOver(board, this.getCounter());
    } catch (InvalidMoveException e) {
    }
    if (checkForWinningMove.isGameOver()){
      return checkForWinningMove.getPlayLocation();
    }
    CheckMoveForGameOver checkForLosingMove = null;
    try {
      checkForLosingMove = new CheckMoveForGameOver(board, opponentCounter);
    } catch (InvalidMoveException e) {
    }
    if (checkForLosingMove.isGameOver()) {
      return checkForLosingMove.getPlayLocation();
    }
    return miniMax.bestColumn;
  }



}
