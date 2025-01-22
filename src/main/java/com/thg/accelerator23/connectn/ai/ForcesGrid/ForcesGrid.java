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

    ArrayList<Position> validLocations = getValidLocations(board);

    int move = 4; //default move
    int maxValue = 0;

    for (Position position : validLocations) {
      int score = forScoring.scorePosition(board, position, getCounter());
      if (score > maxValue) {
        move = position.getX();
        maxValue = score;
      }
    }

    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    System.out.println("move: " + move + "  TimeElapsed: " + (System.currentTimeMillis() - tzero) + "ms");
    return move;
  }

  private int getMinVacantY(int x, Board board) {
    int h = board.getConfig().getHeight();
    //edge-case when column is full
    if (board.getCounterPlacements()[x][h - 1] != null) {
      return 100;
    }
    for (int i = h - 1; i >= 0; --i) {
      if (i == 0 || board.getCounterPlacements()[x][i - 1] != null) {
        return i;
      }
    }
    throw new RuntimeException("no y is vacant");
  }

  private ArrayList<Position> getValidLocations(Board board) {
    int width = board.getConfig().getWidth();
    ArrayList<Position> positions = new ArrayList<>();
    for (int i = 0; i < width; i++) {
      Position pos = new Position(i, getMinVacantY(i, board));
      if (board.isWithinBoard(pos)) {
        positions.add(pos);
      }
    }
    return positions;
  }
}