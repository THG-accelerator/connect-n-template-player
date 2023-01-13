package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniMaxAI implements AI {
    private final int maxDepth;
    private final Counter maximisingCounter;
    GameState gameState;
    private final Counter minimisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private List<Integer> emptyCols;
    private List<Board> children;


    public MiniMaxAI(int maxDepth, Counter maximisingCounter) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
        this.minimisingCounter = maximisingCounter.getOther();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardAnalyser(BoardAnalyser boardAnalyser) {
        this.boardAnalyser = boardAnalyser;
    }


    @Override
    public int getMove() throws InvalidMoveException {

        if (boardAnalyser.isBoardFull(board)) {
            throw new RuntimeException("Board is full!");
        }

//        int bestMove = Math.floorDiv(board.getConfig().getWidth(), 2);
//        if (boardAnalyser.isBoardEmpty(board)) {
//            return bestMove;
//        }
        int bestMove = 0;
        int bestScore = Integer.MIN_VALUE;

        children = getChildren(maximisingCounter);

        for (int i = 0; i < children.size(); i++) {
            int score = minimax(0, minimisingCounter, children.get(i));
            if (score > bestScore) {
                bestMove = emptyCols.get(i);
                bestScore = score;
            }
        }
        return bestMove;
    }

    protected List<Board> getChildren(Counter counter) throws InvalidMoveException {
        emptyCols = new ArrayList<>();
        List<Board> children = new ArrayList<>();
        for (int col = 0; col < board.getConfig().getWidth(); col++) {
            if (!boardAnalyser.isColumnFull(board, col)) {
                Board child = new Board(board, col, counter);
                children.add(child);
                emptyCols.add(col);
            }
        }
        return children;
    }


    public int minimax(int depth, Counter counter, Board board) throws InvalidMoveException {

        gameState = boardAnalyser.calculateGameState(board);

        //TODO: remove counter from analyse
        if (gameState.getWinner() == maximisingCounter) {
            return boardAnalyser.analyse(board, maximisingCounter);
        } else if (gameState.getWinner() == minimisingCounter) {
            return boardAnalyser.analyse(board, maximisingCounter);
        } else if (gameState.isDraw() || depth == maxDepth) {
            return boardAnalyser.analyse(board, maximisingCounter);
        }
        List<Board> children;
        if (counter == maximisingCounter) {
            int maxScore = Integer.MIN_VALUE;

            children = getChildren(counter);

            for (Board child : children) {
                int score = minimax(depth + 1, minimisingCounter, child);
                if (maxScore < score) {
                    maxScore = score;
                }
            }

            return maxScore;

        } else {
            int minScore = Integer.MAX_VALUE;
            children = getChildren(counter);

            for (Board child : children) {
                int score = minimax(depth + 1, maximisingCounter, child);
                if (minScore > score) {
                    minScore = score;
                }
            }

            return minScore;
        }
    }


}
