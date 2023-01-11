
package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class MiniMaxWinLose {
    Counter counter;

    Counter oppositionCounter;
    int score;

    int bestColumn;

    int bestScore;

    MiniMaxWinLose(Counter counter) {
        this.counter = counter;
        this.oppositionCounter = counter.getOther();
    }
    public int miniMaxWinLoseMove(Board boardPlay, boolean isMax, int depth) throws InvalidMoveException {
        BoardAnalyser boardAnalyser = new BoardAnalyser(boardPlay.getConfig());
        if (depth == 0) {return bestScore;}
        else if (isMax) {
            bestScore = -1000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++){
                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay,counter, xMax);
                    GameState gameState = boardAnalyser.calculateGameState(tempBoard);
                    if (gameState.isWin()) {
                        score = 1;
                    } else if (gameState.isDraw()) {
                        score = 0;
                    } else {
                        score = miniMaxWinLoseMove(tempBoard, false, depth - 1);
                    }
                    if (score>bestScore){this.bestColumn = xMax;
                        bestScore = score;}
                }}return bestScore;}
        else{
            bestScore = 1000;
            for (int xMin=0; xMin<boardPlay.getConfig().getWidth(); xMin++){
                Position checkPosition = new Position(xMin, getMinY(xMin, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay, oppositionCounter, xMin);
                    GameState gameState = boardAnalyser.calculateGameState(tempBoard);
                    if (gameState.isWin()) {
                        score = -1;
                    } else if (gameState.isDraw()) {
                        score = 0;
                    } else {
                        score = miniMaxWinLoseMove(tempBoard, false, depth - 1);
                    }
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
    public int getBestColumn() {
        return bestColumn;
    }
}