package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.politicallyconnect.analysis.BoardAnalyser;

import java.util.ArrayList;
import java.util.List;

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
        for (int column = 0; column < board.getConfig().getWidth(); column++) {
            if (isWin(column, board, counter)) {
                return column;
            }
        }
        return null;
    }

    public boolean isColumnFull(Board board, int column) {
        return board.getCounterAtPosition(new Position(column, board.getConfig().getHeight() - 1)) != null;
    }

    public List<Integer> movesNotBelowGameEndingSpace(Board board, Counter counter) {
        List<Integer> propaDecentMoves = new ArrayList<>();
        for (int column = 0; column < board.getConfig().getWidth(); column++) {
            try {
                Board copyOfBoard = new Board(board, column, counter);
                if (!isWin(column, copyOfBoard, counter) && !isWin(column, copyOfBoard, counter.getOther())) {
                    propaDecentMoves.add(column);
                }
            } catch (InvalidMoveException exception) {
                continue;
            }
        }
        return propaDecentMoves;
    }
}