package com.thg.accelerator23.connectn.ai.fermion.board;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

public class localBoardAnalyser {
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

     public boolean[] freeColumns() {
          boolean emptyColumns[] = new boolean[11];
          for(int i=1; i<10; i++) {
               emptyColumns[i] = true;
               if(checkForFullColumn(i, board)) {
                    emptyColumns[i] = false;
               }
          }
          return emptyColumns;
     }

     public int minMaxTreeSeach(int node, int depth, boolean isMaxPlayer, int alpha,int beta){
          if (depth == 0){
               return alpha;
          }
          if (isMaxPlayer){

          } else {


          }


          return 1;
     }

//     public int MTTS(int node,)

}


