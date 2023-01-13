package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.Random;

public class RandomAI extends Player {
    Counter opponentCounter;

    public RandomAI(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }

    public int getMinY(int x, Board board) {
        for (int y = 0; y < board.getConfig().getHeight(); y++) {
            Position minYPosition = new Position(x, y);
            if (!board.hasCounterAtPosition(minYPosition)) {
                return y;
            }
        }
        throw new RuntimeException("no y is vacant");
    }

    @Override
    public int makeMove(Board board) {

        ChooseMove moveChooser = new ChooseMove(board, this.getCounter());

        try {
            moveChooser.setBestMove();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }

        int result;
        Position positionToPlay;

        do {
            result = moveChooser.getPlayLocation();
            positionToPlay = new Position(result, getMinY(result, board));
        } while (!board.isWithinBoard(positionToPlay));

        TrashTalk.talkTrash();
        return result;
    }

}
