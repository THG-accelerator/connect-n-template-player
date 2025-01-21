package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;


public class GoodAiMate extends Player {
  public GoodAiMate(Counter counter) {
    //TODO: fill in your name here
    super(counter, GoodAiMate.class.getName());
  }

  public int getScore(Board board) {
    int score = 0;
    ArrayList<Window> windows = new ArrayList<>();

    //Columns
    for (int col = 0; col < board.getConfig().getWidth(); col++){
      for (int row = 0; row <= board.getConfig().getHeight() - board.getConfig().getnInARowForWin(); row++) {
        Position position1 = new Position(col, row);
        Position position2 = new Position(col,row + 1);
        Position position3 = new Position(col,row + 2);
        Position position4 = new Position(col,row + 3);
        Window currWindow = new Window(board.getCounterAtPosition(position1),board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
        windows.add(currWindow);
      }
    }
    //Rows
    for (int row = 0; row < board.getConfig().getHeight(); row++) {
      for (int col = 0; col <= board.getConfig().getWidth() - board.getConfig().getnInARowForWin(); col++) {
        Position position1 = new Position(col,row);
        Position position2 = new Position(col+1,row );
        Position position3 = new Position(col+2,row);
        Position position4 = new Position(col+3,row );
        Window currWindow = new Window(board.getCounterAtPosition(position1),board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
        windows.add(currWindow);
      }
    }
    //Positive diagonals (col and row increase)
    for (int row = 0; row <= board.getConfig().getHeight() - board.getConfig().getnInARowForWin(); row++) {
      for (int col = 0; col <= board.getConfig().getWidth() - board.getConfig().getnInARowForWin(); col++) {
        Position position1 = new Position(col,row);
        Position position2 = new Position(col + 1,row + 1);
        Position position3 = new Position(col + 2,row + 2);
        Position position4 = new Position(col + 3,row + 3);
        Window currWindow = new Window(board.getCounterAtPosition(position1), board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
        windows.add(currWindow);
      }
    }
    //Negative diagonals (col decreases, row increases)
    for (int col = board.getConfig().getWidth() - 1; col >= 3; col--) {
      for (int row = 0; row <= board.getConfig().getHeight() - board.getConfig().getnInARowForWin(); row++) {
        Position position1 = new Position(col,row);
        Position position2 = new Position(col-1,row+1);
        Position position3 = new Position(col-2,row+2);
        Position position4 = new Position(col-3,row+3);
        Window currWindow = new Window(board.getCounterAtPosition(position1),board.getCounterAtPosition(position2),board.getCounterAtPosition(position3),board.getCounterAtPosition(position4));
        windows.add(currWindow);
      }
    }
    
    return score;

  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}
