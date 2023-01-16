package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.GameState;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

public class MCTS_Updater {

    public void update(Node expandedNode, GameState result) {
        Node node = expandedNode;

        while (node != null){
            node.getState().addVisitCount();
            if (!result.isDraw() && result.getWinner() == expandedNode.getState().getPlayer()) {
                node.getState().addWinCount(1);
            } else if (!result.isDraw() && result.getWinner() != node.getState().getPlayer()) {
                node.getState().addWinCount(-1);
            } else {
                node.getState().addWinCount(0);
            }
            node = node.getParent();
        }
    }

}
