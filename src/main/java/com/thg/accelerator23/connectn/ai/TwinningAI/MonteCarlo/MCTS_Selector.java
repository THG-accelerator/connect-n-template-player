package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

import java.util.Collections;
import java.util.Comparator;

public class MCTS_Selector {

    public double UCTValue(int nodeVisitCount, int nodeWinCount, int parentVisitCount) {
        if (nodeVisitCount == 0) {
            return Integer.MAX_VALUE;}
        return ((double)nodeWinCount / (double)nodeVisitCount) + Math.sqrt(2) * Math.sqrt(Math.log(parentVisitCount) / (double)nodeVisitCount);
    }

    public Node findNode_UCT(Node node) {
        int parentVisitCount = node.getState().getVisitCount();
        int nodeVisitCount = node.getState().getVisitCount();
        int nodeWinCount = node.getState().getWinCount();
        return Collections.max(
                node.getChildList(), Comparator.comparing(currentNode -> UCTValue(nodeVisitCount, nodeWinCount, parentVisitCount)));
    }

    public Node selectPromisingNode(Node node) {
        if (node.getParent() == null) {
            return node;
        } else {
            return findNode_UCT(node);
        }
    }

}
