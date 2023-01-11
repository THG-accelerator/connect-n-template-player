package com.thg.accelerator23.connectn.ai.ruglas.Manual;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;

public class RandomAI extends Player {
    Counter opponentCounter;

    public RandomAI(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        Random rand = new Random();
        int randomNumber = rand.nextInt(board.getConfig().getWidth());

        ChooseMove moveChooser = new ChooseMove(board, this.getCounter());

        try {
            moveChooser.findWinPosition();
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        if (moveChooser.getPlayLocation() != null) {
            return moveChooser.getPlayLocation();
        } else {
            try {
                moveChooser.findBlockPosition();
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }
            if (moveChooser.getPlayLocation() != null) {
                return moveChooser.getPlayLocation();
            }

//      neaten the code above maybe into a while loop
            return randomNumber;
        }

}}
