package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

public class MiniMaxScoring {
    Counter counter;

    Counter oppositionCounter;
    int score;

    int bestColumn;

    int bestScore;

    MiniMaxScoring(Counter counter) {
        this.counter = counter;
        this.oppositionCounter = counter.getOther();
    }
   public int miniMaxMove(Board boardPlay, boolean isMax, int depth, int column) throws InvalidMoveException {
        GetScore getScore = new GetScore(boardPlay);
        System.out.println("Depth " + depth);
       System.out.println("isMax " + isMax);

       if (depth == 0) {
           Counter counterPlay = getCounter(isMax);
           score = getScore.getScoreFromAgjPositions(new Position(column, getMinY(column, boardPlay)), boardPlay, counterPlay);
           return score;}
        else if (isMax) {
             bestScore = -1000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++){
                System.out.println("Column " + xMax);
                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay,counter, xMax);
                    score = miniMaxMove(tempBoard, false, depth - 1, xMax);
                if (score>bestScore){this.bestColumn = xMax;
                    System.out.println("xmax " + xMax);
                    bestScore = score;}
            }}return bestScore;}
        else{
            bestScore = 1000;
           for (int xMin=0; xMin<boardPlay.getConfig().getWidth(); xMin++){
               System.out.println("Column " + xMin);
               Position checkPosition = new Position(xMin, getMinY(xMin, boardPlay));
               if (boardPlay.isWithinBoard(checkPosition)){
                Board tempBoard = makeMove(boardPlay, oppositionCounter, xMin);
                score = miniMaxMove(tempBoard, false, depth - 1, xMin);
                   if (score<bestScore){this.bestColumn = xMin;
                       System.out.println("xmin " + xMin);
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
