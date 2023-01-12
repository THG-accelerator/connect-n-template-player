package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.Connecty;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.GetScore;

public class MiniMaxScoring {
    Counter counter;

    Counter oppositionCounter;
    int score;

    int bestColumn;

    int bestScore;

    public MiniMaxScoring(Counter counter) {
        this.counter = counter;
        this.oppositionCounter = counter.getOther();
    }
   public int miniMaxMove(Board boardPlay, boolean isMax, int depth, int column) throws InvalidMoveException {
        GetScore getScore = new GetScore(boardPlay, this.counter);
       if (depth == 0) {
           Counter counterPlay = getCounter(isMax);
           score = getScore.getTotalScore(new Position(column, getMinY(column, boardPlay)), boardPlay, counterPlay) -
                   getScore.getOpponentScore(new Position(column, getMinY(column, boardPlay)), boardPlay, counterPlay.getOther());
           return score;}
        else if (isMax) {
             bestScore = -1000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++){
                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay,counter, xMax);
                    score = miniMaxMove(tempBoard, false, depth - 1, xMax);
                if (score>bestScore){this.bestColumn = xMax;
                    bestScore = score;}
            }}return bestScore;}
        else{
            bestScore = 1000;
           for (int xMin=0; xMin<boardPlay.getConfig().getWidth(); xMin++){
               Position checkPosition = new Position(xMin, getMinY(xMin, boardPlay));
               if (boardPlay.isWithinBoard(checkPosition)){
                Board tempBoard = makeMove(boardPlay, oppositionCounter, xMin);
                score = miniMaxMove(tempBoard, true, depth - 1, xMin);
                   if (score<bestScore){this.bestColumn = xMin;
                       bestScore = score;}
               }}return bestScore;}}


            private Board makeMove (Board boardPlay, Counter counterPlay,int x) throws InvalidMoveException {
                return new Board(boardPlay, x, counterPlay);
            }


            public int getMinY ( int x, Board board){
                for (int y = 0; y < board.getConfig().getHeight(); y++) {
                    Position minYPosition = new Position(x, y);
                    if (!
                            board.hasCounterAtPosition(minYPosition)) {
                        return y;
                    }
                }
                return 0;
            }

            private Counter getCounter(boolean isMax){
        if (isMax) return counter;
        else{return oppositionCounter;}
            }

    public int getBestColumn() {
        return bestColumn;
    }
}
