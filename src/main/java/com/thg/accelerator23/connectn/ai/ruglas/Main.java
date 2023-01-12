package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.ruglas.Connecty;
import com.thg.accelerator23.connectn.ai.ruglas.TestMove;
import com.thg.accelerator23.connectn.ai.ruglas.miniMax.GetScore;

import java.util.ArrayList;

    public class Main {

        public static void main(String[] args) throws InvalidMoveException {
            Connecty AI = new Connecty(Counter.O);
            GameConfig config = new GameConfig(10, 8, 4);
            Board dougieBoard = new Board(config);

            Board board1 = new Board(dougieBoard, 0, Counter.X);

            Board board2 = new Board(board1, 1, Counter.X);
            Board board3 = new Board(board2, 1, Counter.X);

            Board board4 = new Board(board3, 2, Counter.O);
            Board board5 = new Board(board4,  2, Counter.O);

            Board board6 = new Board(board5, 3, Counter.O);
            Board board7 = new Board(board6, 3, Counter.O);
            Board board8 = new Board(board7, 3, Counter.X);
//            Board board9 = new Board(board8, 3, Counter.X);

            ArrayList<Position> positionList = new ArrayList<>();

            positionList.add(new Position(0,0));
            positionList.add(new Position(1,1));
            positionList.add(new Position(2,2));
            positionList.add(new Position(3,3));

            GetScore scorer = new GetScore(board8, AI.getCounter());

            System.out.println(scorer.getHeightOfWinPositionFromLine(board8, positionList));
            System.out.println(scorer.getHeightScore(board8, positionList));


            }
        }


