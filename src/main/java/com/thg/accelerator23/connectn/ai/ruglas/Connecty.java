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
    Random rand = new Random();
    int randomNumber = rand.nextInt(board.getConfig().getWidth());

    CheckMoveForGameOver checkForWinningMove = null;
    try {
      checkForWinningMove = new CheckMoveForGameOver(board, this.getCounter());
    } catch (InvalidMoveException e) {
    }
    if (checkForWinningMove.isGameOver()) {
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
    return randomNumber;
  }



}
