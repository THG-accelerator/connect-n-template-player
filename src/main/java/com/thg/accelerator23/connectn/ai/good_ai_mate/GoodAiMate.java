package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;


public class GoodAiMate extends Player {
  //Fields we only want to calculate once (will this work or the player instantiated each time?)
  private final ArrayList<Position> horizontalQuadruplets;
  private final ArrayList<Position> verticalQuadruplets;
  private final ArrayList<Position> positiveDiagonalQuadruplets;
  private final ArrayList<Position> negativeDiagonalQuadruplets;

  public GoodAiMate(Counter counter) {
    //TODO: fill in your name here
    super(counter, GoodAiMate.class.getName());
    horizontalQuadruplets = setHorizontalQuadruplets(10,8,4);
    verticalQuadruplets = setVerticalQuadruplets(10,8,4);
    positiveDiagonalQuadruplets = setPositiveDiagonalQuadruplets(10,8,4);
    negativeDiagonalQuadruplets = setNegativeDiagonalQuadruplets(10,8,4);
  }

  private ArrayList<Position> setHorizontalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> horizontalQuadruplets = new ArrayList<>();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        horizontalQuadruplets.add(new Position(col,row));
        horizontalQuadruplets.add(new Position(col+1,row ));
        horizontalQuadruplets.add(new Position(col+2,row));
        horizontalQuadruplets.add(new Position(col+3,row ));
        //Window currWindow = new Window(board.getCounterAtPosition(position1),board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
      }
    }
    return horizontalQuadruplets;
  }

  public ArrayList<Position> setVerticalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> verticalQuadruplets = new ArrayList<>();
    for (int col = 0; col < width; col++){
      for (int row = 0; row <= height - nToWin; row++) {
        verticalQuadruplets.add(new Position(col, row));
        verticalQuadruplets.add(new Position(col,row + 1));
        verticalQuadruplets.add(new Position(col,row + 2));
        verticalQuadruplets.add(new Position(col,row + 3));
      }
    }
    return verticalQuadruplets;
  }

  public ArrayList<Position> setPositiveDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> positiveDiagonalQuadruplets = new ArrayList<>();
    //Positive diagonals (col and row increase)
    for (int row = 0; row <= height - nToWin; row++) {
      for (int col = 0; col <= width - nToWin; col++) {
        positiveDiagonalQuadruplets.add(new Position(col,row));
        positiveDiagonalQuadruplets.add(new Position(col + 1,row + 1));
        positiveDiagonalQuadruplets.add(new Position(col + 2,row + 2));
        positiveDiagonalQuadruplets.add(new Position(col + 3,row + 3));
      }
    }
    return positiveDiagonalQuadruplets;
  }

  public ArrayList<Position> setNegativeDiagonalQuadruplets(int width, int height, int nToWin) {
    ArrayList<Position> negativeDiagonalQuadruplets = new ArrayList<>();
    //Negative diagonals (col decreases, row increases)
    for (int col = width - 1; col >= 3; col--) {
      for (int row = 0; row <= height - nToWin; row++) {
        negativeDiagonalQuadruplets.add(new Position(col,row));
        negativeDiagonalQuadruplets.add(new Position(col-1,row+1));
        negativeDiagonalQuadruplets.add(new Position(col-2,row+2));
        negativeDiagonalQuadruplets.add(new Position(col-3,row+3));
      }
    }
    return negativeDiagonalQuadruplets;
  }

  public int getScore() {
    return this.horizontalQuadruplets.size() + this.verticalQuadruplets.size() + this.negativeDiagonalQuadruplets.size() + this.positiveDiagonalQuadruplets.size();
  }

  //public boolean winningMove(Board board) {  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}
