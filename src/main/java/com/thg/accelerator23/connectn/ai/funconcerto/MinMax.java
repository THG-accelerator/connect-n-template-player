package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;
import com.thg.accelerator23.connectn.ai.funconcerto.movetree.MoveTree;

public class MinMax {

    GameConfig config = new GameConfig(10,8,4);
    BoardAnalyser analyzer = new BoardAnalyser(config);

    public boolean checkWin(Board board, BoardAnalyser analyser, Counter counter){
        GameState state = analyser.calculateGameState(board);
        return state.isWin() && state.getWinner() == counter ;
    }

    public boolean checkDraw(Board board, BoardAnalyser analyser){
        GameState state = analyser.calculateGameState(board);
        return state.isDraw();
    }

    public boolean isTerminalNode(Board board, Counter counter){
        return checkWin(board, analyzer, counter) || checkWin(board,analyzer, counter.getOther()) || checkDraw(board,analyzer);
    }

    public int[] miniMax(MoveTree tree, Boolean maximizingPlayer) {
        System.out.println("Running miniMax");
        int value;
        int column = -1;
        Board board = tree.getState();
        boolean isTerminal = isTerminalNode(board, tree.getCounter().getOther());
        if(isTerminal){
            if(checkWin(board, analyzer, tree.getCounter().getOther())){
                System.out.println("terminal node1!");
                return new int[]{10000000, tree.getPosition()};
            }else if(checkWin(board, analyzer, tree.getCounter())){
                System.out.println("terminal node2!");
                return new int[]{-100000000,tree.getPosition()};
            }else{ // Game is a draw as there are no more valid moves
                System.out.println("terminal node3!");
                return new int[]{0, tree.getPosition()};
            }

        }else{
            if(tree.getChildren().size() == 0){
                return new int[]{Score.ScoreCalculator(board, tree.getCounter().getOther()), tree.getPosition()};
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
                System.out.println(value);
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
