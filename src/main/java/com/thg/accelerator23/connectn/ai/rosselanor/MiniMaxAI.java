package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAI {

    private int maxDepth;
    private Counter maximisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private int lastMove;


    public MiniMaxAI(int maxDepth, Counter maximisingCounter) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
//        this.lastMove = null;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardAnalyser(BoardAnalyser boardAnalyser) {
        this.boardAnalyser = boardAnalyser;
    }

    //Initiates the MiniMax algorithm
    public int getMove() throws InvalidMoveException {
        Move maxMove = max(board, 0);
        return maxMove.getColumn();
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
    public Move max(Board board, int depth) throws InvalidMoveException {
        Random r = new Random();

        /* If MAX is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((boardAnalyser.calculateGameState(board)).isEnd() || (depth == maxDepth)) {
            return new Move(lastMove, boardAnalyser.analyse(board, maximisingCounter));
        }

        List<Board> children = new ArrayList<>(getChildren(maximisingCounter));


        Move maxMove = new Move(null, Integer.MIN_VALUE);
        int childCol = 0;

        for (Board child : children) {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if (move.getValue() >= maxMove.getValue()) {
                lastMove = childCol;
                if ((move.getValue() == maxMove.getValue())) {
                    //If the heuristic has the same value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0) {
                        maxMove.setColumn(childCol);
                        maxMove.setValue(move.getValue());
                    }
                } else {
                    maxMove.setColumn(childCol);
                    maxMove.setValue(move.getValue());
                }
            }
            childCol++;
        }
        return maxMove;

    }

    //Min works similarly to max
    public Move min(Board board, int depth) throws InvalidMoveException {
        Random r = new Random();

        /* If MIN is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((boardAnalyser.calculateGameState(board)).isEnd() || (depth == maxDepth)) {
            return new Move(lastMove, boardAnalyser.analyse(board, maximisingCounter));
        }

        List<Board> children = new ArrayList<>(getChildren(maximisingCounter));

        Move minMove = new Move(null, Integer.MAX_VALUE);
        int childCol = 0;

        for (Board child : children) {

            //And for each child min is called, on a lower depth
            Move move = max(child, depth + 1);
            //The child-move with the greatest value is selected and returned by max
            if (move.getValue() <= minMove.getValue()) {
                lastMove = childCol;
                if ((move.getValue() == minMove.getValue())) {
                    //If the heuristic has the same value then we randomly choose one of the two moves
                    if (r.nextInt(2) == 0) {
                        minMove.setColumn(childCol);
                        minMove.setValue(move.getValue());
                    }
                } else {
                    minMove.setColumn(childCol);
                    minMove.setValue(move.getValue());
                }
            }
            childCol++;
        }
        return minMove;
    }


}
