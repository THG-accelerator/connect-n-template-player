package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MCTS_Expander {

    Random rand = new Random();
    public Node expandNode(Node node) throws InvalidMoveException {
//        System.out.println("In Expand Node");
        if (node.getState().getVisitCount() == 0 || node.getParent() == null) {
            Board board = node.getState().getBoard();
            List<Position> possibleMoves = node.getState().getPossibleMovesList(board);
            Position expandedMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            Board newBoard = new Board(board, expandedMove.getX(), node.getState().getOtherPlayer());
            Node expandedNode = new Node(newBoard, node.getState().getOtherPlayer());
            expandedNode.getState().setPosition(expandedMove);
            node.addChild(expandedNode);

            return node;
        } else {
            Board board = node.getParent().getState().getBoard();
            List<Position> allMoves = node.getParent().getState().getPossibleMovesList(board);
            List<Position> possibleMoves = new ArrayList<>();
            node.getChildList().forEach(child -> {
                if (!allMoves.contains(child.getState().getPosition())) {
                    possibleMoves.add(child.getState().getPosition());
                }
            });
            Position expandedMove = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            Board newBoard = new Board(board, expandedMove.getX(), node.getState().getPlayer());
            Node expandedNode = new Node(newBoard, node.getState().getPlayer());
            expandedNode.getState().setPosition(expandedMove);

            expandedNode.setParent(node.getParent());
            expandedNode.getParent().addChild(expandedNode);
            return expandedNode;
        }
    }


}
