package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;

import java.util.*;

public class FunConcertoAi extends Player {

  GameConfig config = new GameConfig(10,8,4);
  BoardAnalyser analyzer = new BoardAnalyser(config);

  public FunConcertoAi(Counter counter) {
    super(counter, FunConcertoAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int[] scoreAndColumn = miniMax(board, 6, -999999999, 999999999, true, getCounter());
    System.out.println(scoreAndColumn[0]); // Prints out the score
    return scoreAndColumn[1];
  }

  public int scoreBoard(Board board, Counter counter) {
    int score = 0;

    ArrayList<Counter> centreCounters = new ArrayList<Counter>();
    centreCounters.add(board.getCounterAtPosition(new Position(5,0)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,1)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,2)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,3)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,4)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,5)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,6)));
    centreCounters.add(board.getCounterAtPosition(new Position(5,7)));
    int numberOfCentreCountersForPlayer = Collections.frequency(centreCounters, counter);
    int numberOfCentreCountersForOtherPlayer = Collections.frequency(centreCounters, counter.getOther());
    score+=numberOfCentreCountersForPlayer;
    score-=numberOfCentreCountersForOtherPlayer;
    return score;
  }

  public LinkedList<Integer> getValidLocations(Board board){
    LinkedList list = new LinkedList<Integer>();
    for(int i = 0; i < 10; i++){
      if(!board.hasCounterAtPosition(new Position(i,7))){
        list.add(i);
      }
    }
    return list;
  }

  public boolean isTerminalNode(Board board){
    return checkWin(board, analyzer, getCounter()) || checkWin(board,analyzer, getCounter().getOther()) || getValidLocations(board).size() == 0;
  }

  public int[] miniMax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, Counter counter){
    LinkedList<Integer> listOfValidLocations = getValidLocations(board);
    int value;
    int column;
    boolean isTerminal = isTerminalNode(board);
    if(depth == 0 || isTerminal){
      if(isTerminal){
        if(checkWin(board, analyzer, counter)){
          return new int[]{100000000, -1};
        }else if(checkWin(board, analyzer, counter.getOther())){
          return new int[]{-100000000,-1};
        }else{ // Game is a draw as there are no more valid moves
          return new int[]{0, -1};
        }
      }else{ // Depth is 0 and the game hasn't ended
        return new int[]{scoreBoard(board, getCounter()), -1};
      }
    }
    if(maximizingPlayer){
      value = -999999999;
      column = -1;
      for(int i = 0; i < listOfValidLocations.size(); i++){
        try {
          Board copyBoard = new Board(board, listOfValidLocations.get(i), counter);
          alpha = Math.max(alpha, value);
          if(alpha >= beta){
            break;
          }
          int newScore = miniMax(copyBoard, depth-1, alpha, beta, false, counter)[0];
          if(newScore > value){
            value = newScore;
            column = listOfValidLocations.get(i);
          }
        }catch(InvalidMoveException e){
          System.out.println("This shouldn't happen");
        }
      }
      return new int[]{value, column};
    }else{
      value = 999999999;
      column = -1;
      for(int i = 0; i < listOfValidLocations.size(); i++) {
        try {
          Board copyBoard = new Board(board, listOfValidLocations.get(i), counter.getOther());
          beta = Math.min(beta, value);
          if (alpha >= beta) {
            break;
          }
          int newScore = miniMax(copyBoard, depth-1, alpha, beta, true, counter)[0];
          if(newScore < value){
            value = newScore;
            column = listOfValidLocations.get(i);
          }
        }catch(InvalidMoveException e){
          System.out.println("This shouldn't happen");
        }
      }
      return new int[]{value, column};
    }
  }

  public boolean checkWin(Board board, BoardAnalyser analyser, Counter counter){
    GameState state = analyser.calculateGameState(board);
    return state.isWin() && state.getWinner() == counter ;
  }

  public boolean checkWinForOpposingPlayerAfterYourMove(Board board, BoardAnalyser analyzer) {
    for(int i = 0; i < 10; i++){
      Board boardAfter1Move;
      try {
        boardAfter1Move = new Board(board, i, this.getCounter());
      }catch(InvalidMoveException e){
        continue;
      }
      for (int j = 0; j < 10; j++) {
        Board boardAfter2Moves = null;
        try {
          boardAfter2Moves = new Board(boardAfter1Move, j, this.getCounter().getOther());
        }catch (InvalidMoveException e) {

        }
        GameState status = analyzer.calculateGameState(boardAfter2Moves);
        if(status.isWin()){

        }
      }
    }
    return false;
  };
}
