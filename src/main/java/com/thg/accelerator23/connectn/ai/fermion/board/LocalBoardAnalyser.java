package com.thg.accelerator23.connectn.ai.fermion.board;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

public class LocalBoardAnalyser {
     private Board board;
     public LocalBoardAnalyser(Board board){
          this.board = board;
     }
     public boolean checkForFullColumn(int column){
          if(this.board.getCounterAtPosition(new Position(column,this.board.getConfig().getHeight()-1))!=null){
               return true;
          } else {
               return false;
          }
     }

     public boolean[] freeColumns() {
          boolean emptyColumns[] = new boolean[10];
          for(int i=0; i<10; i++) {
               emptyColumns[i] = true;
               if(checkForFullColumn(i)) {
                    emptyColumns[i] = false;
               }
          }
          return emptyColumns;
     }

     public boolean[] fullColumns() {
          boolean fullColumns[] = new boolean[10];
          for(int i=0; i<10; i++) {
               fullColumns[i] = false;
               if(checkForFullColumn(i)) {
                    fullColumns[i] = true;
               }
          }
          return fullColumns;
     }

     public int minMaxTreeSearch(int node, int depth, boolean isMaxPlayer, int alpha,int beta){
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


