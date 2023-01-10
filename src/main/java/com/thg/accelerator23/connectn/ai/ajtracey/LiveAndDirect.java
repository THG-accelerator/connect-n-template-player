package com.thg.accelerator23.connectn.ai.ajtracey;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LiveAndDirect extends Player {
  public LiveAndDirect(Counter counter) {
    super(counter, LiveAndDirect.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    BoardAnalyser BA = new BoardAnalyser(board.getConfig());

      if(BA.winningPositionExists(this.getCounter(), board)){
        return BA.winningPosition(this.getCounter(),board);
      } else if (BA.winningPositionExists(this.getCounter().getOther(), board)){
      return BA.winningPosition(this.getCounter().getOther(), board);

    } else {
        List<Integer> theseAllHaveTheSameBinaryValue = BA.returnsXValueForOurBestMove(board, getCounter());
        Random randomGen = new Random();
          System.out.println(theseAllHaveTheSameBinaryValue);
        int thisXToBeUsed = randomGen.nextInt(theseAllHaveTheSameBinaryValue.size());
          System.out.println(thisXToBeUsed);
        return theseAllHaveTheSameBinaryValue.get(thisXToBeUsed);
      }


    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
  }
}
