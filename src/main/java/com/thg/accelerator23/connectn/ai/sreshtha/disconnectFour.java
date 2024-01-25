package com.thg.accelerator23.connectn.ai.sreshtha;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.sreshtha.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.sreshtha.analysis.GameState;


public class disconnectFour extends Player {
  public disconnectFour(Counter counter) {
    super(counter, disconnectFour.class.getName());
  }

  @Override
  public int makeMove(Board board) {
//    int board_width = 10;
//    int board_height = 8;
//
//    int min = 0;
//    int max = board_width - 1;
//
//    int random = (int)Math.floor(Math.random() * (max - min + 1) + min);
//    //TODO: some crazy analysis
//    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
//    return random;


    int bestScore = Integer.MIN_VALUE;
    int depth = 7;
    int bestMove=-1;

    for(int i=0; i < board.getConfig().getWidth(); i++){
        try {
            Board newBoard = new Board(board, i, getCounter());
            int score = minimax(newBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            if(score > bestScore){
              bestScore = score;
              bestMove = i;
            }
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
    }

    return bestMove;
  }


  // let's try minimax, dunno if it will work :)
  // alpha beta pruning removes the depth of the minimax algorithm, this allows it to take up less tree memory therefore faster : )
  public int minimax(Board board, int depth, int alpha, int beta, boolean maximisingPlayer) throws InvalidMoveException {
    GameState gameState = new BoardAnalyser(board.getConfig()).calculateGameState(board);

    if(depth==0 || gameState.isWin() || gameState.isEnd()){
      return evaluate(board);
    }

    if(maximisingPlayer){
      double maxEval = Double.NEGATIVE_INFINITY;
      for(int i=0; i < board.getConfig().getWidth(); i++){
        Board b = new Board(board, i, this.getCounter());
        int new_score = minimax(b, depth-1, alpha, beta, false);
        if(new_score > maxEval){
          maxEval = new_score;
          alpha = (int)Math.max(alpha, maxEval);
          if (alpha >= beta){
          break;
          }
        }
      } return (int) maxEval;

    }else{
      double minEval = Double.POSITIVE_INFINITY;
      for(int i=0; i< board.getConfig().getWidth(); i++){
        Board bo = new Board(board, i, this.getCounter().getOther());
        int new_score = minimax(bo, depth-1, alpha, beta, true);
        minEval = Math.max(minEval, new_score);
        beta = (int)Math.min(beta, minEval);
        if(alpha >= beta){
          break;
        }
      }return (int)minEval;
    }

  }

  public int calcScorePos(Board board, int row, int col, int increment_row, int increment_col){
    int maxingPoints = 0, miningPoints = 0;
    for(int i=0; i<4; i++){
      if(board.getCounterAtPosition(new Position(col, row)) == Counter.X){
        maxingPoints++;
      }else if(board.getCounterAtPosition(new Position(col, row)) == Counter.O){
        miningPoints++;
      }
      row += increment_row;
      col += increment_col;
    }
    if(maxingPoints == 4){
      return Integer.MAX_VALUE;
    }else if(miningPoints == 4){
      return Integer.MIN_VALUE; // change this
    }else {
      return maxingPoints;
    }
  }

  public int evaluate(Board board) {
    int verticalPoints = 0, horizontalPoints = 0, decsDiagPoint = 0, ascDiagPoints = 0, totalPoints = 0;
    for (int row = 0; row < board.getConfig().getHeight() - 3; row++) {
      for (int col = 0; col < board.getConfig().getWidth(); col++) {
        int tempScore = calcScorePos(board, row, col, 1, 0);
        verticalPoints += tempScore;
        if (tempScore >= Integer.MAX_VALUE || tempScore <= Integer.MIN_VALUE) {
          return tempScore;
        }

      }
    }
    for (int row = 0; row < board.getConfig().getHeight(); row++) {
      for (int col = 0; col < board.getConfig().getWidth() - 3; col++) {
        int tempScore = calcScorePos(board, row, col, 0, 1);
        horizontalPoints += tempScore;
        if (tempScore >= Integer.MAX_VALUE || tempScore <= Integer.MIN_VALUE) {
          return tempScore;
        }

      }
    }

    for(int row=0; row< board.getConfig().getHeight() - 3; row++){ //may need to change the -3 and the -4
      for(int col=0; col < board.getConfig().getHeight() - 3; col++){
        int tempScore = calcScorePos(board, row, col, 1, 1);
        decsDiagPoint += tempScore;
        if(tempScore>=Integer.MAX_VALUE || tempScore <= Integer.MIN_VALUE){
          return tempScore;
        }
      }
    }
    for(int row=3; row< board.getConfig().getHeight(); row++){
      for(int col=0; col<board.getConfig().getHeight() - 4; col++){
        int tempScore = calcScorePos(board, row, col, -1, 1);
        ascDiagPoints += tempScore;
        if(tempScore >= Integer.MAX_VALUE || tempScore <= Integer.MIN_VALUE){
          return tempScore;
        }
      }
    }

    totalPoints = verticalPoints + horizontalPoints + decsDiagPoint + ascDiagPoints;
    return totalPoints;

  }

}
