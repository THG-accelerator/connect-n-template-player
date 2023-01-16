package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

import java.util.Collections;
import java.util.Comparator;

public class MCTS_Selector {

    public double UCTValue(int nodeVisitCount, int nodeWinCount, int parentVisitCount) {
        if (nodeVisitCount == 0) {
            return Integer.MAX_VALUE;}
        return (nodeWinCount / (double)nodeVisitCount) + Math.sqrt(2) * Math.sqrt(Math.log(parentVisitCount) / (double)nodeVisitCount);
    }

    public Node findNode_UCT(Node node) {
        int parentVisitCount = node.getState().getVisitCount();
        int nodeVisitCount = node.getState().getVisitCount();
        int nodeWinCount = node.getState().getWinCount();
        node = Collections.max(
                node.getChildList(), Comparator.comparing(currentNode -> UCTValue(nodeVisitCount, nodeWinCount, parentVisitCount)));
//        return Collections.max(
//                node.getChildList(), Comparator.comparing(currentNode -> UCTValue(nodeVisitCount, nodeWinCount, parentVisitCount)))
//        System.out.println("Complete comparison");
        return node;
    }

    public Node selectPromisingNode(Node node) {
        System.out.println("In Select Promising Node");
        Node childNode = node;
        while (node.getChildList().size() != 0) {
            childNode = findNode_UCT(node);
        }


        return node;
    }



}
