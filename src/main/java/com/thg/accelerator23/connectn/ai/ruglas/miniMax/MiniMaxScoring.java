package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

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
//       System.out.println("depth " + depth + " column " + column + " score "  + score);

        GetScore getScore = new GetScore(boardPlay, this.counter);
        BoardAnalyser boardAnalyser = new BoardAnalyser(boardPlay.getConfig());
        GameState gameState = boardAnalyser.calculateGameState(boardPlay);

        if (depth == 0 || gameState.isEnd()) {
            if (gameState.isWin()){
                if (gameState.getWinner() == counter){return 10000000;}
                else{return -10000000;}}
            else if (gameState.isDraw()){
                return 0;
            }
            else return (getScore.getTotalScore(new Position(column, getMinY(column, boardPlay)), boardPlay, counter)
                        -(getScore.getOpponentScore(new Position(column, getMinY(column, boardPlay)), boardPlay, oppositionCounter)));}
        else if (isMax) {
            bestScore = -1000000;
            for (int xMax=0; xMax<boardPlay.getConfig().getWidth(); xMax++) {
                Position checkPosition = new Position(xMax, getMinY(xMax, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay, counter, xMax);
                    score = miniMaxMove(tempBoard, false, depth - 1, xMax);

                    if (score > bestScore) {
                        bestScore = score;
                        this.bestColumn = xMax;
//                        System.out.println("bestcolumn " + xMax);
                    }

                }}
            return bestScore; }

        else{
            bestScore = 1000000;
            for (int xMin=0; xMin<boardPlay.getConfig().getWidth(); xMin++){
                Position checkPosition = new Position(xMin, getMinY(xMin, boardPlay));
                if (boardPlay.isWithinBoard(checkPosition)){
                    Board tempBoard = makeMove(boardPlay, oppositionCounter, xMin);

                    score = miniMaxMove(tempBoard, true, depth - 1, xMin);

                    if (score<bestScore){
                        this.bestColumn = xMin;
                        bestScore = score;
//                   System.out.println("bestcolumn " + xMin);
                    }

                }
            }}
        return bestScore;
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

