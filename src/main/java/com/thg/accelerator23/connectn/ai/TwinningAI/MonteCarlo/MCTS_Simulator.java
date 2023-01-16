package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.GameState;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

public class MCTS_Simulator {

    public GameState simulateRandomGame(Node node) throws InvalidMoveException {
//        System.out.println("In Simulating Random Game");

        BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(10,8,4 ));
        GameState boardStatus = boardAnalyser.calculateGameState(node.getState().getBoard());

        while (!boardStatus.isEnd()) {
            Position randomMove = node.getState().getPossibleMove(node.getState().getBoard());
            Board tempBoard = new Board(node.getState().getBoard(), randomMove.getX(), node.getState().getOtherPlayer());
            node = new Node(tempBoard, node.getState().getOtherPlayer());
            node.getState().setPosition(randomMove);
            boardStatus = boardAnalyser.calculateGameState(node.getState().getBoard());
        }
        return boardStatus;

    }

}
