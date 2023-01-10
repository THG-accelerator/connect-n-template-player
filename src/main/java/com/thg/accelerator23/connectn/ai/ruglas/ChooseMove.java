package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;

public class ChooseMove {

    Board board;
    int boardWidth;
    int boardHeight;
    Counter counter;
    Counter opponentCounter;
    Integer playLocation;

    ChooseMove(Board board, Counter counter) {
        this.board = board;
        this.counter = counter;
        this.opponentCounter = counter.getOther();
        this.boardHeight = board.getConfig().getHeight();
        this.boardWidth = board.getConfig().getWidth();
        this.playLocation = null;
    }

    public void findWinPosition() throws InvalidMoveException {
        for (int column = 0; column < this.boardWidth; column++) {
            System.out.println(column);
            if (TestMove.isGameOverAfterMove(this.board, column, this.counter)) {
                this.playLocation = column;
            }
        }
    }

    public void findBlockPosition() throws InvalidMoveException {
        for (int column = 0; column < this.boardWidth; column++) {
            if (TestMove.isGameOverAfterMove(this.board, column, this.opponentCounter)) {
                this.playLocation = column;
            }
        }
    }
    public Integer selectBestMove() throws InvalidMoveException {
        findWinPosition();
        if (this.playLocation == null ) {
            findBlockPosition();
        }
//        if (this.playLocation == null){
////            other stuff so that this.playLocation isn't null.
//        }
        return this.playLocation;
    }

    public Integer getPlayLocation() {
        return this.playLocation;
    }
}
