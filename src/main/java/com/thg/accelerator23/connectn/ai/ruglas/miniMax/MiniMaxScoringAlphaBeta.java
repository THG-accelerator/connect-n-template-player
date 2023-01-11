package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class MiniMaxScoringAlphaBeta {
    Counter counter;

    Counter oppositionCounter;
    int score;

    int bestColumn;

    int bestScore;

    MiniMaxScoringAlphaBeta(Counter counter) {
        this.counter = counter;
        this.oppositionCounter = counter.getOther();
    }
   public int miniMaxMoveAlphaBeta(Board boardPlay, boolean isMax, int depth, int column, int alpha, int beta) throws InvalidMoveException {
        //NEED TO CALCULATE SCORE FOR BOTH OPPOSITION AND WINNER
        GetScore getScore = new GetScore(boardPlay);
       BoardAnalyser boardAnalyser = new BoardAnalyser(boardPlay.getConfig());
       GameState gameState = boardAnalyser.calculateGameState(boardPlay);
       if ((depth == 0) || (gameState.isEnd()))  {
           System.out.println("Zero depth" + depth);
           Counter counterPlay = getCounter(isMax);
           if (isMax){
           score = getScore.getTotalScore(new Position(column, getMinY(column, boardPlay)), boardPlay, counterPlay);}
           else {score = -getScore.getTotalScore(new Position(column, getMinY(column, boardPlay)), boardPlay, counterPlay);}
           System.out.println("isMax " + isMax + " Score " + score);
           return score;}
       if (isMax) {
             bestScore = -10000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++){
//                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
//                if (!boardPlay.isWithinBoard(checkPosition)){break;}
                    Board tempBoard = makeMove(boardPlay,counter, xMax);
//                alpha = Math.max(alpha, bestScore);
                score = miniMaxMoveAlphaBeta(tempBoard, false, depth - 1, xMax, alpha, beta);

                if (score>bestScore){this.bestColumn = xMax;}
                bestScore = Math.max(score, bestScore);
//
//                    if (beta <= alpha) {
//                    continue;}

           System.out.println("Best Score" + bestScore);}
            return bestScore;}

        else{
            bestScore = 10000;
           for (int xMin=0; xMin<boardPlay.getConfig().getWidth(); xMin++){
//               Position checkPosition = new Position(xMin, getMinY(xMin, boardPlay));
//               if (boardPlay.isWithinBoard(checkPosition)){break;}
                Board tempBoard = makeMove(boardPlay, oppositionCounter, xMin);
//               beta = Math.min(beta, bestScore);

               score = miniMaxMoveAlphaBeta(tempBoard, true, depth - 1, xMin, alpha, beta);


               if (score<bestScore){this.bestColumn = xMin;}
               bestScore = Math.min(score, bestScore);

//               if (beta <= alpha) {
//
//                   continue;}
               System.out.println("Best Score" + bestScore);
          }}
       return score;
   }


            private Board makeMove (Board boardPlay, Counter counterPlay,int x) {
        try{
                return new Board(boardPlay, x, counterPlay);}
                catch (InvalidMoveException e) {
                    System.out.println("cant make move");
                }
        return boardPlay;
            }


            public int getMinY ( int x, Board board){
                for (int y = 0; y < board.getConfig().getHeight(); y++) {
                    Position minYPosition = new Position(x, y);
                    if (!board.hasCounterAtPosition(minYPosition)) {
                        return y;
                    }
                }
                return 1000;
            }

            private Counter getCounter(boolean isMax){
        if (isMax) return counter;
        else{return oppositionCounter;}
            }


    public int getBestColumn() {
        return bestColumn;
    }
}
