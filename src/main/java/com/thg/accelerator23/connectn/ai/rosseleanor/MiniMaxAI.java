package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MiniMaxAI implements AI {
    private final int maxDepth;
    private final Counter maximisingCounter;
    private final Random r;
    GameState gameState;
    private Counter minimisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private int column;


    public MiniMaxAI(int maxDepth, Counter maximisingCounter) {
        this.maxDepth = maxDepth;
        this.maximisingCounter = maximisingCounter;
        this.minimisingCounter = maximisingCounter.getOther();
        this.r = new Random();
        r.setSeed(10);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardAnalyser(BoardAnalyser boardAnalyser) {
        this.boardAnalyser = boardAnalyser;
    }

    private int getRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, board.getConfig().getWidth());
    }

    @Override
    public int getMove() throws InvalidMoveException {
        boolean isValid = false;
        int randomCol = 0;

        int score = max(board, 0);

        System.out.println(score + " " + column);


        if (boardAnalyser.isColumnFull(board, column)) {
            int count = 0;
            while (!isValid) {
                randomCol = getRandomInt();
                if (!boardAnalyser.isColumnFull(board, randomCol)) {
                    isValid = true;
                }
                count++;
                if (count > board.getConfig().getWidth()) {
                    return -1;
                }
            }
            return randomCol;
        }
        return column;
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

    public int max(Board board, int depth) throws InvalidMoveException {
        gameState = boardAnalyser.calculateGameState(board);
        int score = Integer.MIN_VALUE;

        if (gameState.isEnd() || (depth == maxDepth)) {
            return boardAnalyser.analyse(board, maximisingCounter);
        }

        List<Board> children = new ArrayList<>(getChildren(maximisingCounter));


        int childCol = 0;

        for (Board child : children) {
            int minScore = min(child, depth + 1);

            if (minScore >= score) {
                column = childCol;
                score = minScore;
            }
            childCol++;
        }
        return score;
    }

    public int min(Board board, int depth) throws InvalidMoveException {
        gameState = boardAnalyser.calculateGameState(board);
        int score = Integer.MAX_VALUE;

        if (gameState.isEnd() || (depth == maxDepth)) {
            return boardAnalyser.analyse(board, maximisingCounter);
        }

        List<Board> children = new ArrayList<>(getChildren(minimisingCounter));

        int childCol = 0;

        for (Board child : children) {
            int maxScore = max(child, depth + 1);
            if (maxScore <= score) {
                column = childCol;
                score = maxScore;
            }
            childCol++;
        }
        return score;
    }
}
