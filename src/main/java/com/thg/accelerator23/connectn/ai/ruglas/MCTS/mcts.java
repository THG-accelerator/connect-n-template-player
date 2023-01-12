package com.thg.accelerator23.connectn.ai.ruglas.MCTS;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
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
        Node tree = new Node(board);

        while (computationsCounter < computations) {
            computationsCounter++;

            Node nextNode = selectNextNode(tree);


        }
    }

    private Node selectNextNode(Node tree) {
            Node node = tree;
            while (node.children.size() != 0) {
                if (node.children.size() != 0) {
                node = UCT.findBestNodeWithUCT(node);
            }
            return node;
}

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
//
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
//        private Node expandNodeAndReturnRandom(Node node) {
//            Node result = node;
//
//            Board board = node.board;
//
//            for (Board move : board.getAllLegalNextMoves()) {
//                Node child = new Node(move);
//                child.parent = node;
//                node.addChild(child);
//
//                result = child;
//            }
//
//            int random = Board.RANDOM_GENERATOR.nextInt(node.children.size());
//
//            return node.children.get(random);
//        }
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
//        /**
//         *
//         * "Light playout" is to indicate that each move is chosen totally randomly,
//         * in contrast to using some heuristic
//         *
//         */
//        private int simulateLightPlayout(Node promisingNode) {
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
//        private Node selectPromisingNode(Node tree) {
//            Node node = tree;
//            while (node.children.size() != 0) {
//                //if (node.children.size() != 0) {
//                node = UCT.findBestNodeWithUCT(node);
//            }
//            return node;
//        }
//    }
}
