package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;

public class RandomAI extends Player {

    Counter counter;
    RandomAI(Counter pCounter) {
        super(pCounter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }
    @Override
    public int makeMove(Board board) {

        ChooseMove moveChooser = new ChooseMove(board, this.getCounter());

        try {
            moveChooser.findWinPosition();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        if (moveChooser.playLocation != null) {
            return moveChooser.playLocation;
        } else {
            try {
                moveChooser.findBlockPosition();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
            if (moveChooser.playLocation != null) {
                return moveChooser.playLocation;
            } else {
                Random Rand = new Random();

                int rand = Rand.nextInt(10);

                return rand;
            }
        }
    }
}
