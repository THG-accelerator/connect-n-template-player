package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;

import java.util.concurrent.ThreadLocalRandom;


public class RandomBot extends Player {
    Counter myCounter;
    BoardAnalyser boardAnalyser;


    public RandomBot(Counter myCounter) {
        super(myCounter, RandomBot.class.getName());
        this.myCounter = myCounter;
    }

    int getRandomInt(){
        return ThreadLocalRandom.current().nextInt(0, 10);
    }

    @Override
    public int makeMove(Board board) {
        boardAnalyser = new BoardAnalyser(board.getConfig());
        boolean isValid = false;
        Integer position = null;

        while(!isValid){
            position = getRandomInt();
            if (!boardAnalyser.isColumnFull(board, position)) {
                isValid = true;
            }
        }
        return position;

    }


}

