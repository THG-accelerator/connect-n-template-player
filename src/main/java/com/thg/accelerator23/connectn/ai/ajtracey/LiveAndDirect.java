package com.thg.accelerator23.connectn.ai.ajtracey;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.GameState;

import java.util.*;


public class LiveAndDirect extends Player {
  public LiveAndDirect(Counter counter) {
    super(counter, LiveAndDirect.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    BoardAnalyser BA = new BoardAnalyser(board.getConfig());

    List<Position> currentPositions = BA.getNextPositions(board);
    currentPositions.forEach(position -> {
        System.out.println(position.getX() + ", " + position.getY() + " cP");
    });
    List<Position> winningPositions = BA.returnListOfPositionsForAWinCase(getCounter(), board);
    winningPositions.forEach(position -> {
        System.out.println(position.getX() + ", " + position.getY() + " win");
    });
    List<Position> stopTheirWinPositions = BA.returnListOfPositionsForAWinCase(getCounter().getOther(), board);
     stopTheirWinPositions.forEach(position -> {
          System.out.println(position.getX() + ", " + position.getY() + " stop");
      });
     List<Position> otherBlackList = BA.returnBlackListOfPositions(getCounter(), board);
     otherBlackList.forEach(position -> {
         System.out.println(position.getX() + ", " + position.getY() + " oBl");
     });
    List<Position> blackList =  BA.returnBlackListOfPositions(getCounter().getOther(), board);
      blackList.forEach(position -> {
          System.out.println(position.getX() + ", " + position.getY() + " Bl");
      });

    for(int i = 0; i<winningPositions.size(); i++) {
        if (!winningPositions.isEmpty() && currentPositions.contains(winningPositions.get(i)) && board.isWithinBoard(winningPositions.get(i))){
            System.out.println(winningPositions.get(i).getX() + "win");
            return winningPositions.get(i).getX();
        }
    }
    for(int i=0; i<stopTheirWinPositions.size(); i++){
        if (!stopTheirWinPositions.isEmpty() && currentPositions.contains(stopTheirWinPositions.get(i)) && board.isWithinBoard(stopTheirWinPositions.get(i))){
            System.out.println(stopTheirWinPositions.get(i).getX() + "stop");
            return stopTheirWinPositions.get(i).getX();
        }
    }

    Random randomGen = new Random();
    List<Integer> positionsWeAreAllowedToUse = new ArrayList<>(board.getConfig().getWidth());
    List<Integer> positionsWeAreAllowedToUseBL2 = new ArrayList<>(board.getConfig().getWidth());

      for (Position position: currentPositions) {
        if(!otherBlackList.contains(position)){
            positionsWeAreAllowedToUse.add(position.getX());
        }
        else{
            for(Position posInBlackList: blackList){
                if(posInBlackList.getX() != position.getX() && posInBlackList.getY() != position.getY()){
                    positionsWeAreAllowedToUse.add(position.getX());
                }
            }
        }
      }


      System.out.println(positionsWeAreAllowedToUse);
    int thisXToBeUsed = randomGen.nextInt(positionsWeAreAllowedToUse.size());

      System.out.println(thisXToBeUsed);
    return positionsWeAreAllowedToUse.get(thisXToBeUsed);


    /}
}
