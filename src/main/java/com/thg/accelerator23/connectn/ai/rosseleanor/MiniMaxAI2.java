package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.rosseleanor.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MiniMaxAI2 implements AI {
    private final int maxDepth;
    private final Counter maximisingCounter;
    GameState gameState;
    private Counter minimisingCounter;
    private BoardAnalyser boardAnalyser;
    private Board board;
    private int column;


    public MiniMaxAI2(int maxDepth, Counter maximisingCounter) {
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

    private int getRandomInt() {
        return ThreadLocalRandom.current().nextInt(0, board.getConfig().getWidth());
    }

    @Override
    public int getMove() throws InvalidMoveException {

        int score = 0;

        List<Board> children = new ArrayList<>(getChildren(maximisingCounter));

        for (Board child: children) {
            score = minimax(5, maximisingCounter, child);
            System.out.println(score);
        }




        //        boolean isValid = false;
//        int randomCol = 0;
//        System.out.println(score + " " + column);


//        if (boardAnalyser.isColumnFull(board, column)) {
//            int count = 0;
//            while (!isValid) {
//                randomCol = getRandomInt();
//                if (!boardAnalyser.isColumnFull(board, randomCol)) {
//                    isValid = true;
//                }
//                count++;
//                if (count > board.getConfig().getWidth()) {
//                    return -1;
//                }
//            }
//            return randomCol;
//        }
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


    public int minimax(int depth, Counter counter, Board board) throws InvalidMoveException {
        GameState state = boardAnalyser.calculateGameState(board);
        if (state.getWinner() == maximisingCounter) {
            return boardAnalyser.analyse(board, maximisingCounter);
        } else if (state.getWinner() == minimisingCounter) {
            return boardAnalyser.analyse(board, maximisingCounter);
        } else if (state.isDraw() || depth == 0) {
            return boardAnalyser.analyse(board, maximisingCounter);
        }
        if (counter == maximisingCounter) {
            int maxEval = Integer.MIN_VALUE;
            List<Board> children = new ArrayList<>(getChildren(maximisingCounter));
            for (Board child : children) {
                int eval = minimax(depth - 1, minimisingCounter, child);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;
            List<Board> children = new ArrayList<>(getChildren(maximisingCounter));

            for (Board child : children) {
                int eval = minimax(depth - 1, maximisingCounter, child);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }


}
