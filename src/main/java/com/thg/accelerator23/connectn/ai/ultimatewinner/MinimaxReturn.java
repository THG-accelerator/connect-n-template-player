package com.thg.accelerator23.connectn.ai.ultimatewinner;

import java.util.Optional;
import java.util.OptionalInt;

public class MinimaxReturn {
    private int column;
    private int value;

    public MinimaxReturn(int column, int value) {
        this.column = column;
        this.value = value;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }
}
