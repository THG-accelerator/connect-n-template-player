package com.thg.accelerator23.connectn.ai.sreshtha;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.sreshtha.analysis.GameState;


public class disconnectFour extends Player {
  public disconnectFour(Counter counter) {
    super(counter, disconnectFour.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int board_width = 10;
    int board_height = 8;

    int min = 0;
    int max = board_width - 1;

    int random = (int)Math.floor(Math.random() * (max - min + 1) + min);
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return random;
  }

  public int eval(GameState gameState){
    Counter counter = getCounter();
    Counter winner = gameState.getWinner();
    if(winner==counter){
      return 1;
    }
    return 0;
  }

  // let's try minimax, dunno if it will work :)
  // alpha beta pruning removes the depth of the minimax algorithm, this allows it to take up less tree memory therefore faster : )
  public int minimax(Board board, int depth, int alpha, int beta, boolean maximisingPlayer, GameState gameState) throws InvalidMoveException {
    if(depth==0 || gameState.isWin() || gameState.isEnd()){
      return eval(gameState);
    }

    if(maximisingPlayer){
      double maxEval = Double.NEGATIVE_INFINITY;
      for(int i=0; i < board.getConfig().getWidth(); i++){
        Board b = new Board(board, i, this.getCounter());
        int new_score = minimax(b, depth-1, alpha, beta, false, gameState);
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
        int new_score = minimax(board, depth-1, alpha, beta, true, gameState);
        minEval = Math.max(minEval, new_score);
        beta = (int)Math.min(beta, minEval);
        if(alpha >= beta){
          break;
        }
      }return (int)minEval;
    }


  }

}
