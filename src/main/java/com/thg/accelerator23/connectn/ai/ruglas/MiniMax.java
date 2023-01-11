package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class MiniMax {
    Counter counter;
    Counter oppositionCounter;

    int bestColumn;

    int bestScore;
    int score;

    MiniMax(Counter counter) {
        this.counter = counter;
        this.oppositionCounter = getOpponent(counter);
    }

   public int miniMaxMove(Board boardPlay, boolean isMax, int depth) throws InvalidMoveException {
        BoardAnalyser boardAnalyser = new BoardAnalyser(boardPlay.getConfig());
        System.out.println("Depth " + depth);
       System.out.println("isMax " + isMax);
       System.out.println(boardPlay.getConfig().getWidth());

       if (depth == 0) {return bestScore;}
        else if (isMax) {
             bestScore = -1000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++){
                System.out.println("Column " + xMax);
                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
            Board tempBoard = makeMove(boardPlay,counter, xMax);
            GameState gameState = boardAnalyser.calculateGameState(tempBoard);
                if (gameState.isWin()) {
                score = 1;
            } else if (gameState.isDraw()) {
                score = 0;
            } else {
                    score = miniMaxMove(tempBoard, false, depth - 1);
                }
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
                GameState gameState = boardAnalyser.calculateGameState(tempBoard);
                if (gameState.isWin()) {
                    score = -1;
                } else if (gameState.isDraw()) {
                    score = 0;
                } else {
                    score = miniMaxMove(tempBoard, false, depth - 1);
                }
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


            private Counter getOpponent (Counter counter){
                return switch (counter) {
                    case X -> Counter.O;
                    case O -> Counter.X;
                };

            }

    public int getBestColumn() {
        return bestColumn;
    }
}
