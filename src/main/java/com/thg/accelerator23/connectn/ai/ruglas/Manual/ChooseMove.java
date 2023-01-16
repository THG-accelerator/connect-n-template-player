package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.GetScore;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.GetScoreTwo;

import java.util.ArrayList;
import java.util.Random;

public class ChooseMove {
    Board board;
    int boardWidth;
    int boardHeight;
    Counter counter;
    Counter opponentCounter;
    Integer playLocation;

    ChooseMove(Board board, Counter counter) {
        this.board = board;
        this.counter = counter;
        this.opponentCounter = counter.getOther();
        this.boardHeight = board.getConfig().getHeight();
        this.boardWidth = board.getConfig().getWidth();
        this.playLocation = null;
    }

    public void findWinPosition() throws InvalidMoveException {
        for (int column = 0; column < this.boardWidth; column++) {
            if (TestMove.isGameOverAfterMove(this.board, column, this.counter)) {
                this.playLocation = column;
                System.out.println("Win found");
            }
        }
    }

    public void findBlockPosition() throws InvalidMoveException {
        for (int column = 0; column < this.boardWidth; column++) {
            if (TestMove.isGameOverAfterMove(this.board, column, this.opponentCounter)) {
                this.playLocation = column;
                System.out.println("blocking win");
            }
        }
    }

    public void setBestMove() throws InvalidMoveException {
        findWinPosition();
        if (this.playLocation == null ) {
            findBlockPosition();
        }

        if (this.playLocation == null) {
            int bestScore = -1000;
            int bestColumn = 0;
            for (int i=0; i<this.boardWidth; i++) {

                Position positionToPlay = new Position(i,getMinY(i, this.board));

                if (this.board.isWithinBoard(positionToPlay)) {

                    System.out.println("\n Scoring for column" + i);

                    int positionScore = GetScoreTwo.getTotalScore(this.board, positionToPlay, this.counter);

                    Board boardAfterMove = new Board(this.board, i, this.counter );

                    int opponentBestScore = GetScoreTwo.getBestOpponentScore(boardAfterMove, this.opponentCounter);

                    System.out.println("opponent's best score on next move would be " + opponentBestScore);

                    int totalScore = positionScore - opponentBestScore;

                    if (totalScore > bestScore) {
                        bestScore = totalScore;
                        bestColumn = i;
                        System.out.println("\n The best move is in column " + i + " which scores " + bestScore + " points");
                    }
                }
                else {
                    System.out.println("Can't move here");
                    }
            }
            this.playLocation = bestColumn;
        }

        if (this.playLocation == null) {
            Random rand = new Random();
            this.playLocation = rand.nextInt(board.getConfig().getWidth());
        }
    }

    public Integer getPlayLocation() {
        return this.playLocation;
    }
    public static int getMinY(int x, Board board) {
        for (int y = 0; y < board.getConfig().getHeight(); y++) {
            Position minYPosition = new Position(x, y);
            if (!board.hasCounterAtPosition(minYPosition)) {
                return y;
            }
        }
        return 1000;
    }
}