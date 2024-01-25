package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class AnitaImprove extends Player {

    private StringBuilder myCounters;
    private StringBuilder theirCounters;

    public AnitaImprove(Counter counter) {
        // Anita Improve, created by Radek
        super(counter, AnitaImprove.class.getName());


    }

    @Override
    public int makeMove(Board board) {

        return 4;
    }
}
