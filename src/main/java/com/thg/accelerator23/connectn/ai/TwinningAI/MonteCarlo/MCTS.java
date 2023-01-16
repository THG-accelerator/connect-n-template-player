package com.thg.accelerator23.connectn.ai.TwinningAI.MonteCarlo;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.TwinningAI.Analysis.GameState;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Node;
import com.thg.accelerator23.connectn.ai.TwinningAI.Tree.Tree;

public class MCTS {
    MCTS_Selector selector = new MCTS_Selector();
    MCTS_Expander expander = new MCTS_Expander();
    MCTS_Simulator simulator = new MCTS_Simulator();
    MCTS_Updater updater = new MCTS_Updater();
    BoardAnalyser boardAnalyser = new BoardAnalyser(new GameConfig(10,8,4 ));


    public int MCTS_Searcher(Board board, Counter player) throws InvalidMoveException {
        System.out.println("In MCTS Searcher");
        int loop =0;
        Node rootNode = new Node(board, player);
        Tree tree = new Tree(rootNode);

        long endTime = System.currentTimeMillis() + 5000;
        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < endTime) {

            Node promisingNode = selector.selectPromisingNode(rootNode);

            GameState boardStatus = boardAnalyser.calculateGameState(promisingNode.getState().getBoard());
            if (!boardStatus.isEnd()){
                promisingNode = expander.expandNode(promisingNode);
//                promisingNode = expander.expand(promisingNode);
            }

            GameState randomPlayResult = simulator.simulateRandomGame(promisingNode);

            updater.update(promisingNode, randomPlayResult);
        }

//        Node likelyWinningNode = tree.getRoot().getChildWithMaxWinVisit();
        Node likelyWinningNode = tree.getRoot().getChildWithHighestScore();
        tree.setRoot(likelyWinningNode);

        loop += 1;
        System.out.println("Finish Loop " + loop);
        return likelyWinningNode.getState().getPosition().getX();
    }

}
