package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.MiniMaxScoring;

import java.util.Arrays;
import java.util.Random;


public class Connecty extends Player {
  Counter opponentCounter;
  public boolean firstPlayer;

  public Connecty(Counter counter) {
    super(counter, Connecty.class.getName());
    this.opponentCounter = counter.getOther();

    switch (this.getCounter()) {
      case O -> firstPlayer = true;
      case X -> firstPlayer = false;
    }
  }

  @Override
  public int makeMove(Board board) {

    MiniMaxScoring miniMaxScoring = new MiniMaxScoring(this.getCounter());
    try {
      miniMaxScoring.miniMaxMove(board, true, 3, 0);
      System.out.println("MinMaxScoring");
      return miniMaxScoring.getBestColumn();
    } catch (InvalidMoveException e) {
      System.out.println("Invalid move,  connecty!");
    }
    return 6;
  }
}
