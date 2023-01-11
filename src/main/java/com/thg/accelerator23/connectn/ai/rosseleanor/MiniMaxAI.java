package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAI {
    private final int maxDepth;
    private final Counter maximisingCounter;
    private final Random r;
    GameState gameState;
    private Counter minimisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private int lastMove;


    public MiniMaxAI(int maxDepth, Counter maximisingCounter) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
        this.r = new Random();
        r.setSeed(10);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardAnalyser(BoardAnalyser boardAnalyser) {
        this.boardAnalyser = boardAnalyser;
    }

    //Initiates the MiniMax algorithm
    public int getMove() throws InvalidMoveException {
        this.minimisingCounter = boardAnalyser.otherPlayer(maximisingCounter);
        Move maxMove = max(board, 0);
        return maxMove.getColumn();
    }

    protected List<Board> getChildren(Counter counter) throws InvalidMoveException {
        List<Board> children = new ArrayList<>();
        for (int col = 0; col < board.getConfig().getWidth(); col++) {
            if (!boardAnalyser.isColumnFull(board, col)) {
                Board child = new Board(board, col, counter);
                children.add(child);
            }
        }
        return children;
    }


    public Move max(Board board, int depth) throws InvalidMoveException {
        gameState = boardAnalyser.calculateGameState(board);

        if (gameState.isEnd() || (depth == maxDepth)) {
            return new Move(lastMove, boardAnalyser.analyse(board, maximisingCounter));
        }

        List<Board> children = new ArrayList<>(getChildren(maximisingCounter));

        Move maxMove = new Move(null, Integer.MIN_VALUE);
        int childCol = 0;

        for (Board child : children) {
            Move move = min(child, depth + 1);

            if (move.getValue() >= maxMove.getValue()) {
                lastMove = childCol;
                if ((move.getValue() == maxMove.getValue())) {
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

    public Move min(Board board, int depth) throws InvalidMoveException {
        gameState = boardAnalyser.calculateGameState(board);

        if (gameState.isEnd() || (depth == maxDepth)) {
            return new Move(lastMove, boardAnalyser.analyse(board, maximisingCounter));
        }

        List<Board> children = new ArrayList<>(getChildren(minimisingCounter));

        Move minMove = new Move(null, Integer.MAX_VALUE);
        int childCol = 0;

        for (Board child : children) {
            Move move = max(child, depth + 1);
            if (move.getValue() <= minMove.getValue()) {
                lastMove = childCol;
                if ((move.getValue() == minMove.getValue())) {
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
