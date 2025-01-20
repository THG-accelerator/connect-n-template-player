package com.thg.accelerator23.connectn.ai.onleave;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class onLeaveSlowResponse extends Player {
  public onLeaveSlowResponse(Counter counter) {
    //TODO: fill in your name here
    super(counter, onLeaveSlowResponse.class.getName());
  }



  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }

  private boolean isValidMove(Board board, int column) {
    if (column < 0 || column >= board.getConfig().getWidth()) {
      return false;
    }
    Counter[][] counterPlacements = board.getCounterPlacements();
    for (int row = 0; row < board.getConfig().getHeight(); row++) {
      if (counterPlacements[row][column] == null) {
        return true;
      }
    }
    return false;
  }


}
