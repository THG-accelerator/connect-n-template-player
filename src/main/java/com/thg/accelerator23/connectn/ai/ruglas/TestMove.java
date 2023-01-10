package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

public class TestMove {
    private static Board tryMove(Board board, int column, Counter counter) throws InvalidMoveException {
        try {
            return new Board(board, column, counter);
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move");
        }
        return null;
    }
    // (The Board constructor automatically finds valid move within a column if there is one)

    public static boolean isGameOverAfterMove(Board board, int column, Counter counter) throws InvalidMoveException {
        Board tryBoard = tryMove(board, column, counter);
        BoardAnalyser boardAnalyser = new BoardAnalyser(tryBoard.getConfig());
        GameState gameState = boardAnalyser.calculateGameState(tryBoard);

        if (gameState.isWin()) {
            return true;
        }
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
//    sometimes there is no other option but to make a move that let's the opponent
}




//
//    private boolean checkThreeInARow(Board board, Counter counter) {
//
//        ArrayList<Counter> threeInARowArray = new ArrayList<>();
//        int maxInARow = analyser.calculateGameState(board).getMaxInARowByCounter().get(counter);
//        return (maxInARow == 3);
//    }
//
//    private ArrayList<Position> getAllPositions(Board board) {
//
//        ArrayList<Position> positions = new ArrayList<>();
//
//        for (int x=0; x < board.getConfig().getWidth(); x++) {
//            for ( int y=0 ; y< board.getConfig().getHeight(); y++) {
//                Position position = new Position(x,y);
//                positions.add(position);
//            }
//        }
//        return positions;
//    }
}
