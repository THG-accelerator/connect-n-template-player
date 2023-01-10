package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class CheckMoveForGameOver {
    Board board;
    Counter counter;
    int playLocation;
    boolean isGameOver;

    CheckMoveForGameOver(Board board, Counter counter) throws InvalidMoveException {
        this.board = board;
        this.counter = counter;
        this.isGameOver = findGameOver();
    };

    public boolean findGameOver() throws InvalidMoveException {
        for(int x=0; x<board.getConfig().getWidth(); x++) {
            Board tryBoard = MoveLogic.tryMove(board, x, counter);
            BoardAnalyser boardAnalyser = new BoardAnalyser(tryBoard.getConfig());
            GameState gameState = boardAnalyser.calculateGameState(tryBoard);
            if (gameState.isWin()) {
                this.playLocation = x;
                return true;
            }
        }
        return false;
    }

    public int getMinY(int x) {
        for (int y=0; y<board.getConfig().getHeight(); y++) {
            Position minYPosition = new Position(x, y);
            if(!board.hasCounterAtPosition(minYPosition)){
                return y;
            }
        }
        return 0;}

    public int getPlayLocation(){return this.playLocation;}
    public boolean isGameOver() {
        return isGameOver;
    }
}

