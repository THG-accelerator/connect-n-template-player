package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.Connecty;
import com.thg.accelerator23.connectn.ai.ruglas.Manual.ChooseMove;
import com.thg.accelerator23.connectn.ai.ruglas.TestMove;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.GetScore;

import java.util.ArrayList;

    public class Main {

        static Connecty AI = new Connecty(Counter.O);
        static GameConfig config = new GameConfig(10, 8, 4);
        public static void main(String[] args) throws InvalidMoveException {

            Board dougieBoard = new Board(config);
            int[] firstColumn = {1,1,1,1,1,1,1,1};
            Board fullBoard = ChooseMove.placeSeveralCounters(Counter.X, firstColumn);

            for (int i=0; i<8; i++) {
                Position posn = new Position(0,i);
                System.out.println(fullBoard.getCounterAtPosition(posn));
            }

            }

        }


