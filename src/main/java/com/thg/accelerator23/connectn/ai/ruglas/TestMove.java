package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.GameState;

import java.util.ArrayList;

public class TestMove {

    private static Board tryMove(Board board, int column, Counter counter) {
        try {
            return new Board(board, column, counter);
        } catch (InvalidMoveException e) {
            System.out.println("Invalid move");
        }
        return null;
    }
    // (The Board constructor automatically finds valid move within a column if there is one)


    public static boolean isGameOverAfterMove(Board board, int column, Counter counter){
        Board tryBoard = tryMove(board, column, counter);
        BoardAnalyser boardAnalyser = new BoardAnalyser(tryBoard.getConfig());
        GameState gameState = boardAnalyser.calculateGameState(tryBoard);

        if (gameState.isWin()) {
            return true;
        }
        return false;
    }

    public static boolean doesMoveGiveOpponentWin(Board board, int column, Counter counter) {
        Counter opponentCounter = counter.getOther();
        Board tryBoard = null;
            tryBoard = tryMove(board, column, counter);

        if (isGameOverAfterMove(tryBoard, column, opponentCounter)) {
            return true;
        }
        return false;
    }


/*    TODO: function that takes in a position or int ad returns if there is a line of three in 4 spaces surrounding it. May involve tryMove on surrounding squares. */
    private boolean checkThreeInARow(Board board, Counter counter) {

        ArrayList<Counter> threeInARowArray = new ArrayList<>();

        GameConfig config = new GameConfig(10,8, 4);
        BoardAnalyser analyser = new BoardAnalyser(config);

        int maxInARow = analyser.calculateGameState(board).getMaxInARowByCounter().get(counter);

        return (maxInARow == 3);
    }

    public static ArrayList<ArrayList<Position>> getAdjacentNPositions(Board board, Position position, int n) {
        ArrayList<ArrayList<Position>> positions = new ArrayList<>();

        ArrayList<Position> horizontal = new ArrayList<>();
        ArrayList<Position> vertical = new ArrayList<>();
        ArrayList<Position> upwardsDiagonal = new ArrayList<>();
        ArrayList<Position> downwardsDiagonal = new ArrayList<>();

//        ArrayList<Position> below = new ArrayList<>();

        int x = position.getX();
        int y = position.getY();

        for ( int i=1-n; i < n ; i++) {

//            Position leftPosition = new Position(x-i,y);
            Position horizontalPosition = new Position(x+i, y);
            Position verticalPosition = new Position(x,y+i);
            Position upwardsDiagonalPosition = new Position(x+i,y+i);
            Position downwardsDiagonalPosition = new Position(x+i,y-i);

//            Position belowPosition = new Position(x,y-i);
//            Position diagRightUp = new Position(x+i,y+i);




            if (board.isWithinBoard(horizontalPosition)) {
                horizontal.add(horizontalPosition);
            }
            if (board.isWithinBoard(verticalPosition)) {
                vertical.add(verticalPosition);
            }
            if (board.isWithinBoard(upwardsDiagonalPosition)) {
                upwardsDiagonal.add(upwardsDiagonalPosition);
            }

            if (board.isWithinBoard(downwardsDiagonalPosition)) {
                downwardsDiagonal.add(downwardsDiagonalPosition);
            }

        }
        positions.add(horizontal);
        positions.add(vertical);
        positions.add(upwardsDiagonal);
        positions.add(downwardsDiagonal);

        return positions;
    }

//    public boolean checkForLineOfThree(Board board, Counter counter, Position position) {
//        int x = position.getX();
//        int y = position.getY();
//
//        ArrayList<ArrayList<Position>> adjacents = getAdjacentNPositions(board,position, 4);
//
//        ArrayList<Position> totheLeft = getAdjacentNPositions(board, position, 4).get(0);
//        ArrayList<Position> totheRight = getAdjacentNPositions(board, position, 4).get(0);
//        ArrayList<Position> above = getAdjacentNPositions(board, position, 4).get(0);
//
//        for (Position position : )
//    sometimes there is no other option but to make a move that lets the opponent !
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