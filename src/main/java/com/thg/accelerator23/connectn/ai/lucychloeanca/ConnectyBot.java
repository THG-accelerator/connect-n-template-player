package com.thg.accelerator23.connectn.ai.lucychloeanca;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.*;
import java.util.stream.IntStream;


public class ConnectyBot extends Player {
  public ConnectyBot(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectyBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int position = getOptimalCol(board);
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return position;
  }


  private int randomMove(Board board) {
    int position = new Random().nextInt(0, 10);
    if (!isSpaceAvailable(board, position)){
      position = randomMove(board);
    }
    return position;
  }

  private boolean isSpaceAvailable(Board board, int position){
    for (int i = 0; i < board.getConfig().getHeight(); i++){
      if (!board.hasCounterAtPosition(new Position(position, i))){
        return true;
      }
    }
    return false;
  }

  private List<Integer> getAvailableCol(Board board){
    List<Integer> availableCol = new ArrayList<>();
    for (int col = 0; col<board.getConfig().getWidth(); col++){
      if (this.isSpaceAvailable(board, col)){
        availableCol.add(col);
      }
    }
    return availableCol;
  }


  private int columnScoreCalculator(Board board, int columnNumber){
    int score = 0;
    try {
      Board possibleNewBoard = new Board(board, columnNumber, getCounter());
      GameConfig newConfig = possibleNewBoard.getConfig();
      LCABoardAnalyser boardAnalyser = new LCABoardAnalyser(newConfig);
      if (boardAnalyser.calculateGameState(possibleNewBoard).isEnd()) {
        Counter winner = boardAnalyser.calculateGameState(possibleNewBoard).getWinner();
        if (winner.equals(this.getCounter())) {
          score = 1;
        }else {
          score = -1;
        }
      } else {
        score = 0;
      }

    }catch (InvalidMoveException e){
      score = -100000000;
    }

    return score;
  }

  private int miniMax (int position, int depth, boolean maximisingPlayer, Board board){
    if (depth == 0 || new LCABoardAnalyser(board.getConfig()).calculateGameState(board).isEnd()) {
      return columnScoreCalculator(board, position);
    }
    if (maximisingPlayer){
      int maxEval = -1000000;
      for (int i = 0; i < board.getConfig().getWidth(); i++){
        int eval = miniMax(i , depth - 1, false, board);
        maxEval = Math.max(maxEval, eval);
      }
      return maxEval;
    } else {
      int minEval = 1000000;
      for (int i = 0; i < board.getConfig().getWidth(); i++){
        int eval = miniMax(i, depth -1, true, board);
        minEval = Math.min(minEval, eval);
      }
      return minEval;
    }
  }

  private Map<Integer, Integer> getAllColScores(Board board){
    Map<Integer, Integer> colScores = new HashMap<>();
    List<Integer> availableCol = getAvailableCol(board);
    availableCol.stream().forEach((colNumber) -> {
      int score = miniMax(colNumber, 3, true, board);
      colScores.put(colNumber, score);
    });
    return colScores;
  }

  private int getOptimalCol(Board board){
    Map<Integer, Integer> colScores = getAllColScores(board);
    int highestScoringCol = colScores.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    int highestScore = colScores.get(highestScoringCol);
    if (highestScore>0) {
      return highestScoringCol;
    }else {
      System.out.println("random");
      return randomMove(board);
    }
  }
}