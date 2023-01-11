package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;
import com.thg.accelerator23.connectn.ai.funconcerto.movetree.MoveTree;

public class MinMax {

    static GameConfig config = new GameConfig(10,8,4);
    static BoardAnalyser analyzer = new BoardAnalyser(config);
    static Counter playerCounter;

    public MinMax(Counter playerCounter) {
        MinMax.playerCounter = playerCounter;
    }

    public static boolean checkWin(Board board, BoardAnalyser analyser, Counter counter){
        GameState state = analyser.calculateGameState(board);
        return state.isWin() && state.getWinner() == counter ;
    }

    public static boolean checkDraw(Board board, BoardAnalyser analyser){
        GameState state = analyser.calculateGameState(board);
        return state.isDraw();
    }

    public static boolean isTerminalNode(Board board){
        return checkWin(board, analyzer, playerCounter) || checkWin(board,analyzer, playerCounter.getOther()) || checkDraw(board,analyzer);
    }

    public static int[] miniMax(MoveTree tree, Boolean maximizingPlayer) {
        int value;
        int column = -1;
        Board board = tree.getState();
        boolean isTerminal = isTerminalNode(board);
        if(isTerminal){
            if(checkWin(board, analyzer, playerCounter)){
                return new int[]{10000000, -1};
            }else if(checkWin(board, analyzer, playerCounter.getOther())){
                return new int[]{-100000000,-1};
            }else{ // Game is a draw as there are no more valid moves
                return new int[]{0, -1};
            }

        }else{
            if(tree.getChildren().size() == 0){
                return new int[]{Score.ScoreCalculator(board), tree.getPosition()};
            }
            if(maximizingPlayer) {
                value = -Integer.MAX_VALUE;
                for (int i = 0; i < tree.getChildren().size(); i++) {
                    int[] newScoreAndColumn = miniMax(tree.getChildren().get(i),  false);
                    if(newScoreAndColumn[0] > value){
                        value = newScoreAndColumn[0];
                        column = tree.getChildren().get(i).getPosition();
                    }
                }
            }else{
                value = Integer.MAX_VALUE;
                for (int i = 0; i < tree.getChildren().size(); i++) {
                    int[] newScoreAndColumn = miniMax(tree.getChildren().get(i),  true);
                    if(newScoreAndColumn[0] < value){
                        value = newScoreAndColumn[0];
                        column = tree.getChildren().get(i).getPosition();
                    }
                }
            }
            return new int[]{value, column};
        }
    }

}
