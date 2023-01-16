package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.GameState;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;

public class MCTS_Updater {

    public void update(Node expandedNode, GameState boardStatus) {
//        System.out.println("In Updating");
        Node node = expandedNode;

        while (node != null){
            node.getState().addVisitCount();
            if (!boardStatus.isDraw() || boardStatus.getWinner() == expandedNode.getState().getPlayer()) {
                node.getState().addWinCount(1);
            } else if (!boardStatus.isDraw() || boardStatus.getWinner() != node.getState().getPlayer()) {
                node.getState().addWinCount(-1);
            } else {
                node.getState().addWinCount(0);
            }
            node = node.getParent();
        }


    }

}
