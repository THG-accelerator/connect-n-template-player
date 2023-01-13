package com.thg.accelerator23.connectn.ai.ruglas.MCTS;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.ruglas.analysis.BoardAnalyser;

public class mcts {
    private Board board;
    private Counter counter;

    private int computationsCounter;
    private int computations;

    public mcts(Board board, Counter counter, int computations) {
        this.board = board;
        this.counter = counter;
        this.computations = computations;
    }

    public void mctsBestMove() {
        computationsCounter = 0;
        Node tree = new Node(board, 0);

        while (computationsCounter < computations) {
            computationsCounter++;

            Node nextNode = selectNextNode(tree);

            //If node is not game over
            //Expand board


            //Simulate on next node


        }
    }

    private Node selectNextNode(Node tree) {
            Node node = tree;
            while (node.children.size() != 0) {
                if (node.children.size() != 0) {
                node = UCTcalculator.findBestNodeWithUCT(node);
            }}
            return node;
}

private Node expandNodeRando(Node node) {
    Node result = node;
    Board board = node.getBoard();

    for (int column = 0; column < node.getBoard().getConfig().getWidth(); column++) {
        //make move if available
        try {
            Board boardWithMove = new Board(board, column, counter);
            Node child = new Node(boardWithMove, column);
            child.setParent(node);
            result = child;
        } catch (InvalidMoveException e) {
        }
    }
    return result;}

    private void simulatePlay(Node node){
        //While game in progress get random next move and make new node
    }


}



//    private int simulateLightPlayout(Node promisingNode) {
//            Node node = new Node(promisingNode.board);
//            node.parent = promisingNode.parent;
//            int boardStatus = node.board.getStatus();
//
//            if (boardStatus == opponentId) {
//                node.parent.score = Integer.MIN_VALUE;
//                return node.board.getStatus();
//            }
//
//            while (node.board.getStatus() == Board.GAME_IN_PROGRESS) {
//                //game.ConnectFourBoard nextMove = node.board.getWinningMoveOrElseRandom();
//                Board nextMove = node.board.getRandomLegalNextMove();
//
//                Node child = new Node(nextMove);
//                child.parent = node;
//                node.addChild(child);
//
//                node = child;
//            }
//
//            return node.board.getStatus();
//        }
//
//


//        private void backPropagation(int playerNumber, Node selected) {
//            Node node = selected;
//
//            while (node != null) { // look for the root
//                node.visits++;
//                if (node.board.getLatestMovePlayer() == playerNumber) {
//                    node.score++;
//                }
//
//                node = node.parent;
//            }
//        }

//
//        public Board doMcts() {
//            System.out.println("MCTS working.");
//            Instant start = Instant.now();
//
//            long counter = 0l;
//
//            Node tree = new Node(board);
//
//            while (counter < computations) {
//                counter++;
//
//                //SELECT
//                Node promisingNode = selectPromisingNode(tree);

//                //EXPAND
//                Node selected = promisingNode;
//                if (selected.board.getStatus() == Board.GAME_IN_PROGRESS) {
//                    selected = expandNodeAndReturnRandom(promisingNode);
//                }
//
//                //SIMULATE
//                int playoutResult = simulateLightPlayout(selected);
//
//                //PROPAGATE
//                backPropagation(playoutResult, selected);
//            }
//
//            Node best = tree.getChildWithMaxScore();
//
//            Instant end = Instant.now();
//            long milis = end.toEpochMilli() - start.toEpochMilli();
//
//            System.out.println("Did " + counter + " expansions/simulations within " + milis + " milis");
//            System.out.println("Best move scored " + best.score + " and was visited " + best.visits + " times");
//
//            return best.board;
//        }
//
//        // if node is already a leaf, return the leaf

//            int random = Board.RANDOM_GENERATOR.nextInt(node.children.size());
//
//            return node.children.get(random);
//        }
//

