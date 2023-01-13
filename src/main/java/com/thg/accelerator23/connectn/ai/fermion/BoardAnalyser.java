package com.thg.accelerator23.connectn.ai.fermion;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

public class BoardAnalyser {
    private Board board;
//     public BoardAnalyser(Board board){
//          this.board = board;
//     }


    public boolean checkForFullColumn(int column,Board board){
        if(board.getCounterAtPosition(new Position(column,board.getConfig().getHeight()-1))==null){
            return true;
        }else {
            return false;
        }
    }

}
