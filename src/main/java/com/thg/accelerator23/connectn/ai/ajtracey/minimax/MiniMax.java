package com.thg.accelerator23.connectn.ai.ajtracey.minimax;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.BoardAnalyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiniMax {
    private Tree tree;

    public Tree getTree() {
        return tree;
    }

    public void constructTree(int numberOfMovesSimulated, Board board, Counter counter) {
        tree = new Tree();
        Node root = new Node(0, numberOfMovesSimulated, true, board, counter);
        tree.setRoot(root);
        constructTree(root);

    }

    private void constructTree(Node parentNode) {
        Map<Integer, Map<Integer, Board>> possibleMovesNextTurnWithScoresAndBoards = new HashMap<>();
        BoardAnalyser analyser = new BoardAnalyser(parentNode.getBoard().getConfig());
        for (int i = 0; i < 3; i++) {
            try {
                Map<Integer, Board> thisMovesScoreAndNextBoard = new HashMap<>();
                thisMovesScoreAndNextBoard.put(analyser.returnsBinaryValueOfOurMoveForAGivenX(i, parentNode.getBoard(), parentNode.getCounter()),
                        new Board(parentNode.getBoard(), i, parentNode.getCounter()));
                possibleMovesNextTurnWithScoresAndBoards.put(i, thisMovesScoreAndNextBoard);
            } catch (InvalidMoveException e) {
            }
        }
        boolean childMaxOrMin = !parentNode.getIsMaxPlayer();

        for (int move : possibleMovesNextTurnWithScoresAndBoards.keySet()) {
            Node newNode = new Node(parentNode.getRoundsAhead() + 1, parentNode.getTotalRounds(),
                    childMaxOrMin,
                    possibleMovesNextTurnWithScoresAndBoards.get(move).values().stream().toList().get(0),
                    parentNode.getCounter().getOther());
            newNode.setMove(move);
            newNode.setParent(parentNode);
            newNode.initialScore();
            parentNode.addChild(newNode);


            if (!analyser.calculateGameState(newNode.getBoard()).isEnd() &&
                    newNode.getRoundsAhead() < parentNode.getTotalRounds()) {
                constructTree(newNode);
            } else {
                if (newNode.getIsMaxPlayer()) {
                    newNode.setScore(possibleMovesNextTurnWithScoresAndBoards.get(move).keySet().stream().toList().get(0));
                } else {
                    newNode.setScore(-possibleMovesNextTurnWithScoresAndBoards.get(move).keySet().stream().toList().get(0));
                }
            }
        }
        propagateBestChildUpTree(parentNode);
    }

    private void findBestChild(Node node) {
        List<Node> listOfChildren = node.getChildren();
        int bestScore;
        if (node.getIsMaxPlayer()) {
            bestScore = Integer.MIN_VALUE;
            for (Node nodeChild : listOfChildren) {
                if (nodeChild.getScore() > bestScore) {
                    bestScore = node.getScore();
                    if (nodeChild.getScore() > node.getScore()) {
                        node.setScore(bestScore);
                    }
                }
            }
        }else{
                bestScore = Integer.MAX_VALUE;
                for (Node nodeChild : listOfChildren) {
                    if (nodeChild.getScore() < bestScore) {
                        bestScore = node.getScore();
                        if (nodeChild.getScore() < node.getScore()) {
                            node.setScore(bestScore);
                        }
                    }
                }
            }
    }

    private void propagateBestChildUpTree(Node node){
        while (node.getParent()!=null){
            findBestChild(node);
            propagateBestChildUpTree(node.getParent());
        }
    }



}
