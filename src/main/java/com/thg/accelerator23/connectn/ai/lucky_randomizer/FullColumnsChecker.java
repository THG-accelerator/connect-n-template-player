package com.thg.accelerator23.connectn.ai.lucky_randomizer;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.List;

public class FullColumnsChecker {
    private final Board board;

    public  FullColumnsChecker(Board board){
        this.board = board;
    }

    public List<Integer> fullColumnChecker(){
        List<Integer> emptyColumns = new ArrayList<>();
        for (int column = 0; column < board.getConfig().getWidth(); column++){
            if(!board.hasCounterAtPosition(new Position(column,board.getConfig().getHeight() - 1))){
                emptyColumns.add(column);
            }
        }
        return emptyColumns;
    }
}
