package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.politicallyconnect.analysis.BoardAnalyser;

public class AIAnalyser {
    BoardAnalyser boardAnalyser;

    public AIAnalyser(GameConfig gameConfig) {
        this.boardAnalyser = new BoardAnalyser(gameConfig);
    }

    private boolean isWin(int column, Board board, Counter counter) {
        try {
            Board isItGonnaBeTheWinningBoardOOOOOO = new Board(board, column, counter);
            return boardAnalyser.calculateGameState(isItGonnaBeTheWinningBoardOOOOOO).isWin();
        } catch (InvalidMoveException exception) {
            return false;
        }
    }

    public Integer winningColumn(Board board, Counter counter) {
        for (int column = 0; column < board.getConfig().getWidth(); column++){
            if (isWin(column, board, counter)) {
                return column;
            }
        }
        return null;
    }

}

