package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.ChooseMove;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.TrashTalk;

public class ConnectileDysfunction extends Player {
    Counter opponentCounter;

    public ConnectileDysfunction(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.ConnectileDysfunction.class.getName());
    }

    public int getMinY(int x, Board board) {
        for (int y = 0; y < board.getConfig().getHeight(); y++) {
            Position minYPosition = new Position(x, y);
            if (!board.hasCounterAtPosition(minYPosition)) {
                return y;
            }
        }
        return 100;
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
