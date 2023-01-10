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


    public MiniMaxAI(int maxDepth, Counter maximisingCounter, Board board) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
        this.board = board;
        this.boardAnalyser = new BoardAnalyser(board.getConfig());
        this.lastMove = null;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    //Initiates the MiniMax algorithm
    public int runMiniMax() {
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
            return lastMove;
        }

        List<Board> children = new ArrayList<>(getChildren(counter));

        Move maxMove = new Move(null, Integer.MIN_VALUE);
        int lastMoveCol = 0;
        int childCol = 0;

        for (Board child : children) {
            //And for each child min is called, on a lower depth
            Move move = min(child, depth + 1, counter);
            //The child-move with the greatest value is selected and returned by max
            if (move.getValue() >= maxMove.getValue()) {
                lastMoveCol = childCol;
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
        lastMove = new Move(lastMoveCol, boardAnalyser.analyse(board));
        return maxMove;
    }

    //Min works similarly to max
    public Move min(Board board, int depth, Counter counter) throws InvalidMoveException {
        Random r = new Random();

        /* If MIN is called on a state that is terminal or after a maximum depth is reached,
         * then a heuristic is calculated on the state and the move returned.
         */
        if ((boardAnalyser.calculateGameState(board)).isEnd() || (depth == maxDepth)) {
            return lastMove;
        }

        List<Board> children = new ArrayList<>(getChildren(counter));

        Move minMove = new Move(null, Integer.MAX_VALUE);
        int lastMoveCol = 0;
        int childCol = 0;

        for (Board child : children) {

            //And for each child min is called, on a lower depth
            Move move = max(child, depth + 1, counter);
            //The child-move with the greatest value is selected and returned by max
            if (move.getValue() <= minMove.getValue()) {
                lastMoveCol = childCol;
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
        lastMove = new Move(lastMoveCol, boardAnalyser.analyse(board));
        return minMove;
    }


}
