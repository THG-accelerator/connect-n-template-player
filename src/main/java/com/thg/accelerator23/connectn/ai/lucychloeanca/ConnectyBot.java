package com.thg.accelerator23.connectn.ai.lucychloeanca;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.*;


public class ConnectyBot extends Player {
  public ConnectyBot(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectyBot.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int position;
    try {
      position = miniMax(3, true, board).getColumn();
      //position = getOptimalCol(board, this.getCounter());
    } catch (InvalidMoveException e) {
      throw new RuntimeException(e);
    }
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
      score = score + 1000000;
    } else if (count == 3 && empty == 1) {
      score = score +1000;
    }

    if (opponentCount == 3 && empty==1){
      score = score - 8000;
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
      score = score + (centerCount * 100);
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
            +scoreCenterArray(board,counter));
  }

  private ColumnScore miniMax (int depth, boolean maximisingPlayer, Board board) throws InvalidMoveException {
    Counter counter = this.getCounter();
    if (!maximisingPlayer) {
      counter = getOpponentCounter(counter);
    }
    LCABoardAnalyser boardAnalyser = new LCABoardAnalyser(board.getConfig());
    if (depth == 0 || boardAnalyser.calculateGameState(board).isEnd()) {
      if (depth == 0) {
        int finalScore = scoreCalculator(board, counter);
        return new ColumnScore(randomMove(board) ,finalScore);
      }
      else {
        if (boardAnalyser.calculateGameState(board).getWinner().equals(this.getCounter())) {
          return new ColumnScore(randomMove(board) ,1000000000);
        } else if (boardAnalyser.calculateGameState(board).getWinner().equals(getOpponentCounter(this.getCounter()))) {
          return new ColumnScore(randomMove(board) ,-1000000000);
        } else {
          return new ColumnScore(randomMove(board) ,0);
        }
      }

    }
    List<Integer> availableCol = getAvailableCol(board);
    if (maximisingPlayer){
      int maxEval = -1000000000;
      int bestCol = randomMove(board);
      for (int col: availableCol){
        Board newPossibleBoard = new Board(board, col, this.getCounter());
        int eval = miniMax( depth - 1, false, newPossibleBoard).getScore();
        maxEval = Math.max(maxEval, eval);
        if (eval> maxEval){
          maxEval = eval;
          bestCol = col;

        }
      }
      return new ColumnScore(bestCol, maxEval);
    } else {
      int minEval = 1000000000;
      int bestCol = randomMove(board);
      for (int col: availableCol){
        Board newPossibleBoard = new Board(board, col, getOpponentCounter(this.getCounter()));
        int eval = miniMax( depth -1, true, newPossibleBoard).getScore();
        if (eval<minEval){
          minEval = eval;
          bestCol = col;
        }
      }
      return new ColumnScore(bestCol, minEval);
    }
  }

  private int getOptimalCol(Board board, Counter counter) throws InvalidMoveException {
    int bestScore = 0;
    int bestCol = randomMove(board);
    List<Integer> availableCol = getAvailableCol(board);
    for (int col =0; col<availableCol.size(); col++) {
      Board newPossibleBoard = new Board(board, col, counter);
      int score = scoreCalculator(newPossibleBoard, counter);
      if (score>bestScore){
        bestScore = score;
        bestCol = col;
      }

    }
    return bestCol;}


}



