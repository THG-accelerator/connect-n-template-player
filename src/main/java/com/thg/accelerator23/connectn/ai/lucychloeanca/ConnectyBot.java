package com.thg.accelerator23.connectn.ai.lucychloeanca;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.*;


public class ConnectyBot extends Player {
  public ConnectyBot(Counter counter) {
    super(counter, ConnectyBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {

    ColumnScore optimisedCol;
    try {
      optimisedCol = miniMax(3, true, board, 0);
    } catch (InvalidMoveException e) {
      throw new RuntimeException(e);
    }
    return optimisedCol.getColumn();
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

  private Counter getOpponentCounter(Counter counter){
    Counter opponentCounter = null;
    switch (counter) {
      case X -> opponentCounter = Counter.O;
      case O -> opponentCounter = Counter.X;
    }
    return opponentCounter;
  }

  private int evaluateWindow(List<Counter> window, Counter counter){
    int score = 0;
    Counter opponentCounter = getOpponentCounter(counter);
    int count = Collections.frequency(window, counter);
    int empty = Collections.frequency(window, null);
    int opponentCount = Collections.frequency(window, opponentCounter);
    if (count == 4){
      score = score + 100;
    } else if (count == 3 && empty == 1) {
      score = score +5;
    } else if (count ==2 && empty ==2) {
      score = score + 2;
    }
    if (opponentCount == 3 && empty==1){
      score = score - 4;
    }
    return score;
  }

  private int scoreCenterArray(Board board, Counter counter){
    List<Integer> availableCol = getAvailableCol(board);
    int score =0;
    int middleCol = Math.floorDiv(board.getConfig().getWidth(), 2);
    if (availableCol.contains(middleCol)) {
      List<Counter> centerList = new ArrayList<>();
      for (int row = 0; row < board.getConfig().getHeight(); row++) {
        Counter currentCounter = board.getCounterAtPosition(new Position(middleCol, row));
        centerList.add(currentCounter);
      }
      int centerCount = Collections.frequency(centerList, counter);
      score = score + (centerCount * 3);
    }

    return score;

  }

  private int horizontalScoreCalculator(Board board, Counter counter){

    int score =0;
    for (int row = 0; row<board.getConfig().getHeight(); row++){
      List<Counter> rowList = new ArrayList<>();
      for(int col=0; col<board.getConfig().getWidth(); col++){
        Counter countInPosition = board.getCounterAtPosition(new Position(col,row));
        rowList.add(countInPosition);
      }
      for (int col=0; col<rowList.size()-3; col++){
        List<Counter> window = rowList.subList(col, col+4);
        score = score + evaluateWindow(window, counter);

      }
    }
    return score;
  }

  private int verticalScoreCalculator(Board board, Counter counter){

    int score =0;
    for (int col=0; col<board.getConfig().getWidth(); col++){
      List<Counter> colList = new ArrayList<>();
      for (int row = 0; row<board.getConfig().getHeight(); row++){
        Counter countInPosition = board.getCounterAtPosition(new Position(col,row));
        colList.add(countInPosition);
      }
      for (int row = 0; row< colList.size()-3; row++){
        List<Counter> window = colList.subList(row, row+4);
        score = score + evaluateWindow(window, counter);
      }
    }

    return score;
  }

  private int positiveDiagonalScoreCalculator(Board board, Counter counter){

    int score =0;
    for (int row=0; row<board.getConfig().getHeight()-3;row++){

      for (int col =0; col<board.getConfig().getWidth()-3;col++){
        List<Counter> window = new ArrayList<>();
        for (int length = 0; length<4; length++){
          Counter currentPlace = board.getCounterAtPosition(new Position(col+length,row+length));
          window.add(currentPlace);
        }
        score = score + evaluateWindow(window, counter);
      }
    }

    return score;
  }

  private int negativeDiagonalScoreCalculator(Board board, Counter counter){

    int score =0;
    for (int row=0; row<board.getConfig().getHeight()-3;row++){
      for (int col =0; col<board.getConfig().getWidth()-3;col++){
        List<Counter> window = new ArrayList<>();
        for (int length = 0; length<4; length++){
          Counter currentPlace = board.getCounterAtPosition(new Position(col+length,row+3-length));
          window.add(currentPlace);
        }
        score = score + evaluateWindow(window, counter);
      }
    }

    return score;
  }



  private int scoreCalculator(Board board, Counter counter){
    return (horizontalScoreCalculator(board, counter)
            +verticalScoreCalculator(board, counter)
            +positiveDiagonalScoreCalculator(board, counter)
            +negativeDiagonalScoreCalculator(board, counter)
            +scoreCenterArray(board, counter));
  }

  private ColumnScore miniMax (int depth, boolean maximisingPlayer, Board board, int bestCol) throws InvalidMoveException {
    LCABoardAnalyser boardAnalyser = new LCABoardAnalyser(board.getConfig());
    if (depth == 0 || boardAnalyser.calculateGameState(board).isEnd()) {
      if (depth == 0) {
        int finalScore = scoreCalculator(board, this.getCounter());
        return new ColumnScore(bestCol ,finalScore);
      }
      else {
        if (boardAnalyser.calculateGameState(board).getWinner().equals(this.getCounter())) {
          return new ColumnScore(bestCol ,1000000000);
        } else if (boardAnalyser.calculateGameState(board).getWinner().equals(getOpponentCounter(this.getCounter()))) {
          return new ColumnScore(bestCol ,-1000000000);
        } else {
          return new ColumnScore(bestCol ,0);
        }
      }

    }
    List<Integer> availableCol = getAvailableCol(board);
    if (maximisingPlayer){
      int maxEval = -1000000000;
      for (int col: availableCol){
        Board newPossibleBoard = new Board(board, col, this.getCounter());
        int eval = miniMax( depth - 1, false, newPossibleBoard, col).getScore();
        if (eval> maxEval){
          maxEval = eval;
          bestCol = col;

        }
      }
      return new ColumnScore(bestCol, maxEval);
    } else {
      int minEval = 1000000000;
      for (int col: availableCol){
        Board newPossibleBoard = new Board(board, col, getOpponentCounter(this.getCounter()));
        int eval = miniMax( depth -1, true, newPossibleBoard, col).getScore();
        if (eval<minEval){
          minEval = eval;
          bestCol = col;
        }
      }
      return new ColumnScore(bestCol, minEval);
    }
  }


}



