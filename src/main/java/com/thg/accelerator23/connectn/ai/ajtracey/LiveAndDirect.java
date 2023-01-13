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

    List<Position> currentPositions = BA.getNextPositions(board);
    List<Position> winningPositions = BA.returnListOfPositionsForAWinCase(getCounter(), board);
    List<Position> stopTheirWinPositions = BA.returnListOfPositionsForAWinCase(getCounter().getOther(), board);
    List<Position> blackList =  BA.returnBlackListOfPositions(getCounter().getOther(), board);


    for(int i = 0; i<winningPositions.size(); i++) {
        if (!winningPositions.isEmpty() && currentPositions.contains(winningPositions.get(i)) && board.isWithinBoard(winningPositions.get(i))){
            return winningPositions.get(i).getX();
        }
    }
    for(int i=0; i<stopTheirWinPositions.size(); i++){
        if (!stopTheirWinPositions.isEmpty() && currentPositions.contains(stopTheirWinPositions.get(i)) && board.isWithinBoard(stopTheirWinPositions.get(i))){
            return stopTheirWinPositions.get(i).getX();
        }
    }

    List<Integer> theseAllHaveTheSameBinaryValue = BA.returnsXValueForOurBestMove(board, getCounter());
    Random randomGen = new Random();

      for (int xValue: theseAllHaveTheSameBinaryValue) {


      }

    int thisXToBeUsed = randomGen.nextInt(theseAllHaveTheSameBinaryValue.size());

    return theseAllHaveTheSameBinaryValue.get(thisXToBeUsed);


    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
  }
}
