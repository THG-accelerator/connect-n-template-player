package com.thg.accelerator23.connectn.ai.ajtracey;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SuAIcidal extends Player {
    public SuAIcidal(Counter counter) {
        super(counter.getOther(), LiveAndDirect.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        BoardAnalyser BA = new BoardAnalyser(board.getConfig());
        if (BA.winningPositionExists(this.getCounter(), board)){
            return BA.winningPosition(this.getCounter(), board);
        } else if (BA.isNInARowPossible(this.getCounter(), board, 3)){
            return BA.NInARowPosition(this.getCounter(), board, 3);
        } else if (BA.isNInARowPossible(this.getCounter(), board, 2)){
            return BA.NInARowPosition(this.getCounter(), board, 2);
        } else return 4;
    }
    }