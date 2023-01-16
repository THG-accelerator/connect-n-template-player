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
        Node rootNode = new Node(board, player);
        Tree tree = new Tree(rootNode);

        long endTime = System.currentTimeMillis() + 8500;

        Node promisingNode = rootNode;

        while (System.currentTimeMillis() < endTime) {

            if (isBoardEmpty(board) == 0) {
                return 4;
            } else {

                promisingNode = selector.selectPromisingNode(promisingNode);
                GameState boardStatus = boardAnalyser.calculateGameState(promisingNode.getState().getBoard());
                if (!boardStatus.isEnd()) {
                    expander.expandNode(promisingNode);
                    for (Node child : promisingNode.getChildList()) {
                        GameState randomPlayResult = simulator.simulateRandomGame(child);
                        updater.update(child, randomPlayResult);
                    }
                }
            }
        }
        Node likelyWinningNode = tree.getRoot().getChildWithMaxWinCount();
        return likelyWinningNode.getState().getPosition().getX();
    }

    public int isBoardEmpty(Board board){
        int boardEmpty = 0;
        for (int col = 0; col < board.getConfig().getWidth(); col++){
            for (int row = 0; row < board.getConfig().getHeight(); row++){
                if (board.hasCounterAtPosition(new Position(col, row))){
                    boardEmpty++;
                } else { boardEmpty = boardEmpty;}
            }
        }
        return boardEmpty;
    }

}
