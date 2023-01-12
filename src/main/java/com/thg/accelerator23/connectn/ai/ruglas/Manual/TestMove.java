package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class TestMove {

    private static Board tryMove(Board board, int column, Counter counter) throws InvalidMoveException {
        try {
            return new Board(board, column, counter);
        } catch (InvalidMoveException e) {
        }
        return board;
    }
    // (The Board constructor automatically finds valid move within a column if there is one)

    public static boolean isGameOverAfterMove(Board board, int column, Counter counter) throws InvalidMoveException {
        Board tryBoard = tryMove(board, column, counter);
        if (tryBoard != null) {
        BoardAnalyser boardAnalyser = new BoardAnalyser(tryBoard.getConfig());
        GameState gameState = boardAnalyser.calculateGameState(tryBoard);

        if (gameState.isWin()) {
            return true;
        }
        return false;}
        return false;
    }

    public static boolean doesMoveGiveOpponentWin(Board board, int column, Counter counter) throws InvalidMoveException {
        Counter opponentCounter = counter.getOther();
        Board tryBoard = tryMove(board,column, counter);
        if (isGameOverAfterMove(tryBoard, column, opponentCounter)) {
            return true;
        }
        return false;
    }}