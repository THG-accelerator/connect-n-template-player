package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.*;

public class ConnectBot extends Player {

  Counter myCounter;

  public ConnectBot(Counter counter) {
    super(counter, ConnectBot.class.getName());
    this.myCounter = myCounter;
  }

    @Override
    public int makeMove(Board board) {

        MiniMaxAI miniMaxAI = new MiniMaxAI(5, this.myCounter, board);

      return miniMaxAI.runMiniMax();
    };

}

