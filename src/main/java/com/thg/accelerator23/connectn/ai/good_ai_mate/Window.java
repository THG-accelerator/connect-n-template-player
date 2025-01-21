package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Counter;

public class Window {
    private int score;
    private final Counter[] array = new Counter[4];

    public Window(Counter a, Counter b, Counter c, Counter d) {
        this.array[0] = a;
        this.array[1] = b;
        this.array[2] = c;
        this.array[3] = d;
    }

    public int getWindowScore(){
        //TODO logic for calculating score
        return 1;
    }

}
