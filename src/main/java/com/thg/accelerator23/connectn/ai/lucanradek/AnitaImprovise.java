package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;

public class AnitaImprovise extends Player {
    public AnitaImprovise(Counter counter) {
        // Anita Improvise, created by Radek
        super(counter, AnitaImprove.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        Random random = new Random();
        return random.nextInt(0, 10);
    }
}
