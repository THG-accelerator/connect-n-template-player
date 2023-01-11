package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;

public class MiniMax {
    Board board;
    Counter counter;
    Counter oppositionCounter;
    int score;

    MiniMax(Board board, Counter counter) {
        this.board = board;
        this.counter = counter;
    }
}
