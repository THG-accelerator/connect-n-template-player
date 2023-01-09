package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAI {

    //Variable that holds the maximum depth the MiniMax algorithm will reach for this player
    private int maxDepth;
    //Variable that holds which letter this player controls
    private Counter maximisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private Move lastMove;


    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

//    public Counter getPlayerLetter() {
//        return playerCounter;
//    }
//
//    public void setPlayerLetter(Counter playerLetter) {
//        this.playerCounter = playerLetter;
//    }


    //    public void MinimaxAI(Counter counter) {
//        maxDepth = 4;
////        playerLetter = Board.X;
//        playerLetter = counter.getStringRepresentation();
//    }


    public void MinimaxAI(int maxDepth, Counter maximisingCounter, Board board) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
        this.board = board;
        this.boardAnalyser = new BoardAnalyser(board.getConfig());

    }

    //Initiates the MiniMax algorithm
    public int MiniMax() {

        return 1;
    }

    private List<Board> getChildren(Counter counter) throws InvalidMoveException {
        List<Board> children = new ArrayList<>();
        for (int col = 0; col < board.getConfig().getWidth(); col++) {
            if (!boardAnalyser.isColumnFull(board, col)) {
                Board child = new Board(board, col, counter);
                children.add(child);
            }
        }
        return children;
    }


    // The max and min functions are called interchangeably, one after another until a max depth is reached
    public Move max(Board board, int depth, Counter counter) throws InvalidMoveException {
        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((boardAnalyser.calculateGameState(board)).isEnd() || (depth == maxDepth)) {
//            lastMove = new Move(lastMove.getRow(), lastMove.getCol(), board.evaluate());
            return lastMove;
        }

        List<Board> children = new ArrayList<>(getChildren(counter));

        Move maxMove = new Move(Integer.MIN_VALUE);

        for (Board child : children) {
            int col = 0;
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if (move.getValue() >= maxMove.getValue()) {
                if ((move.getValue() == maxMove.getValue())) {
                    //If the heuristic has the same value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0) {
                        maxMove.setColumn(col);
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setColumn(col);
                    maxMove.setValue(move.getValue());
                }
            }
        }
        return maxMove;
    }

    //Min works similarly to max
//    public Move min(Board board, int depth) {
//        Random r = new Random();
//
//        if ((board.checkGameOver()) || (depth == maxDepth)) {
//            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
//            return lastMove;
//        }
//        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
//        Move minMove = new Move(Integer.MAX_VALUE);
//        for (Board child : children) {
//            Move move = max(child, depth + 1);
//            if (move.getValue() <= minMove.getValue()) {
//                if ((move.getValue() == minMove.getValue())) {
//                    if (r.nextInt(2) == 0) {
//                        minMove.setRow(child.getLastMove().getRow());
//                        minMove.setCol(child.getLastMove().getCol());
//                        minMove.setValue(move.getValue());
//                    }
//                } else {
//                    minMove.setRow(child.getLastMove().getRow());
//                    minMove.setCol(child.getLastMove().getCol());
//                    minMove.setValue(move.getValue());
//                }
//            }
//        }
//        return minMove;
//    }

}
