package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.*;


public class ForcesGrid extends Player {
  public ForcesGrid(Counter counter) {
    //TODO: fill in your name here
    super(counter, ForcesGrid.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    long tzero = System.currentTimeMillis();
    forPrinting.printBoard(board);

    ArrayList<Position> validLocations = Analysis.getValidLocations(board);

    int move = 4; //default move
    int maxValue = 0;

    for (Position position : validLocations) {
      int score = forScoring.scorePosition(board, position, getCounter());
//      System.out.println(position.getX()+", "+ position.getY() + ": " + score);
      if (score > maxValue) {
        move = position.getX();
        maxValue = score;
      }
    }
    System.out.println("game over? " + Analysis.gameOver(board, getCounter()));
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    System.out.println("move: " + move + "  TimeElapsed: " + (System.currentTimeMillis() - tzero) + "ms");
    return move;
  }
}