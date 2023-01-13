package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.ArrayList;

public class TestMove {

    public static Board tryMove(Board board, int column, Counter counter) throws InvalidMoveException {
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
    }
    public static Board placeSeveralCounters(Counter counter, int[] columnList) throws InvalidMoveException {

        GameConfig config = new GameConfig(10,8,4);
        ArrayList<Board> boards = new ArrayList<>();
        Board returnBoard = new Board(config);
        boards.add(returnBoard);

        for (int i=0; i < columnList.length; i++) {
            returnBoard = new Board(boards.get(i), columnList[i],  counter);
            boards.add(returnBoard);
        }
        return returnBoard;
    }
}