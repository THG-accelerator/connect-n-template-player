package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

import java.util.List;
import java.util.Random;

public class MCTS_Expander {

    Random rand = new Random();

    public void expandNode(Node node) throws InvalidMoveException {
        Board board = node.getState().getBoard();
        List<Position> possibleMoves = node.getState().getPossibleMovesList(board);
        for (Position move : possibleMoves) {
            Board newBoard = new Board(board, move.getX(), node.getState().getOtherPlayer());
            Node expandedNode = new Node(newBoard, node.getState().getOtherPlayer());
            expandedNode.getState().setPosition(move);
            expandedNode.setParent(node);
            node.addChild(expandedNode);
        }
    }
}
