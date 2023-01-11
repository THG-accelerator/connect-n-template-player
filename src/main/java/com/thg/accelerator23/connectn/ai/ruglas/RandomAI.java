package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
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
        return rand.nextInt(board.getConfig().getWidth());
    }

}
