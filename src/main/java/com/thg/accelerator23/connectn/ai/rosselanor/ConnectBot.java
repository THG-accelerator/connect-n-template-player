package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;


public class ConnectBot extends Player {
    Counter myCounter;
    BoardAnalyser boardAnalyser;

    public ConnectBot(Counter counter) {
        super(counter, ConnectBot.class.getName());
        this.myCounter = counter;
    }

    @Override
    public int makeMove(Board board) {

        MiniMaxAI miniMaxAI = new MiniMaxAI(5, myCounter, board);

      return miniMaxAI.runMiniMax();
    };

}