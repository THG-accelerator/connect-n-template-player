package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.ArrayList;
import java.util.List;

public class CheckForWinningMove {
    Board board;
    Counter counter;
    int winningInt;
    CheckForWinningMove(Board board, Counter counter){
        this.board = board;
        this.counter = counter;
    };

    public boolean findWin() throws InvalidMoveException {
        for(int x=0; x<board.getConfig().getWidth(); x++) {
            if (getMinY(x) < board.getConfig().getHeight()){
            Board tryBoard = new Board(board, x, counter);
            BoardAnalyser boardAnalyser = new BoardAnalyser(board.getConfig());
            GameState gameState = boardAnalyser.calculateGameState(tryBoard);
            if (gameState.isWin()) {
                this.winningInt = x;
                return true;
            }}

        }
        return false;}

    public int getMinY(int x) {
        for (int y=0; y<board.getConfig().getHeight(); y++) {
            Position minYPosition = new Position(x, y);
            if(!
            board.hasCounterAtPosition(minYPosition)){
                return y;
            }
    }return 0;}



}
