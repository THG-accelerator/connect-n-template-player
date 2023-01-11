package com.thg.accelerator23.connectn.ai.ruglas.miniMax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class MiniMax {
    Board board;
    BoardAnalyser boardAnalyser;

    MiniMax(Board board) {
        this.board = board;
        this.boardAnalyser = new BoardAnalyser(board.getConfig());

    }

    public void MiniMaxMove() {

    }

    public int makeMove(int column, Counter counter, Board board, boolean isMax, int depth) throws InvalidMoveException {
        if (depth ==0) {
            return 200;
        }
        Board newBoard = setBoard(board, counter, column);
        GameState gameState = boardAnalyser.calculateGameState(newBoard);
        if (gameState.isDraw()){
            return 0;
        }
        if (isMax) {
            if (gameState.isWin()){
                return 1;
            }
            else return -10000;}
        else {if(gameState.isWin()){
            return -1;
        }
        else{return 10000;}}
    }

    private Board setBoard(Board board, Counter counter, int column) throws InvalidMoveException {
        return new Board(board, column, counter);
    }
}
