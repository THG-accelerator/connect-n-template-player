package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {
    private ArrayList<Integer> moveCounter = new ArrayList<>();
    private ArrayList<Integer> winCounter = new ArrayList<>();
    Board board;

    public MCTS(Board board) {
        this.board = board;
    }

//    public Node selectHighestUTCNode(Node parentNode) {
//        Node bestNode = new Node();
//        if(parentNode.getChildren().size() != 0) {
//            for(Node node : parentNode.getChildren()) {
//                if(Integer.MIN_VALUE < node.getUTCValue(parentNode))
//                   bestNode = node;
//            }
//        }
//        return bestNode;
//    }
//
    public Node selectHighestUTCNode(Node parentNode) {
        Node node = parentNode;
        while (node.getChildren().size() != 0) {
            node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUTCValue(parentNode)));
        }
        return node;
    }
//
//    public void expandNode(Node node) {
//        node.addChild();
//    }

    public int actualPlay(Counter counter) {
        Node rootNode = new Node(counter);
        Tree tree = new Tree(rootNode);

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);

        BoardAnalyser analyser = new BoardAnalyser(rootNode.getState().getBoard().getConfig());

        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {
            Node promisingNode = selectHighestUTCNode(rootNode);
            
            if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd()) {
                promisingNode.generateChildrenNodes();
            }
//            else {
//                System.out.println("terminal node");
//            }

            Node nodeToExplore = randomChildNode(promisingNode);

            Counter results = simulateMoves(nodeToExplore,rootNode.getState().getCounter());

            backpropagation(nodeToExplore,results);

        }

        for (Node child : rootNode.getChildren()) {
            System.out.println(child.getMove() +" "+ child.getState().getNodeVisits() +" "+ child.getState().getNodeWins());
        }

        Node winnerNode = rootNode.childMostVisits();

        tree.setRoot(winnerNode);
        return winnerNode.getMove();
    }


    private Counter simulateMoves(Node nodeToExplore, Counter rootCounter) {
        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
        GameState gameState = boardChecker.calculateGameState(nodeToExplore.getState().getBoard());

        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState();

//        if (gameState.getWinner() == rootCounter.getOther()){
//            nodeToExplore.getParent().getState().setNodeWins(Integer.MIN_VALUE);
//            return  nodeToExplore.getState().getCounter();
//        }
//
//        if (gameState.getWinner() == rootCounter) {
//            nodeToExplore.getParent().getState().setNodeWins(Integer.MAX_VALUE);
//            return  nodeToExplore.getState().getCounter();
//        }

        while(gameState.isEnd() == false){
            try {
                interState.invertCounter();
                int move = interNode.playRandomMove(interState.getBoard());

                interState.setBoard(new Board(interState.getBoard(), move, interState.getCounter()));

                gameState = boardChecker.calculateGameState(interState.getBoard());

            } catch(InvalidMoveException e) {
                System.out.println("Sim fail");
                throw new RuntimeException(e);
            }
        }
//        System.out.println(gameState.getWinner());
        moveCounter.add(nodeToExplore.getMove());
        if(gameState.getWinner() == nodeToExplore.getState().getCounter()){
            winCounter.add(nodeToExplore.getMove());
        }
//        System.out.println(moveCounter.contains(nodeToExplore.getMove())+"win "+winCounter.contains(nodeToExplore.getMove()));


        return gameState.getWinner();
//        if(gameState.getWinner() == nodeToExplore.getState().getCounter()){
////            System.out.println("Win");
//            return 1;
//        } else if (gameState.getWinner() == nodeToExplore.getState().getCounterOpposite()) {
////            System.out.println("Lost");
//            return -1;
//        } else {
////            System.out.println("Draw");
//            return 0;
//        }
    }


    public Node randomChildNode(Node rootNode) {
        if(rootNode.getChildren().size() != 0) {
            Random r = new Random();
            int nextNode = r.nextInt(rootNode.getChildren().size());
            Node n = rootNode.getChildren().get(nextNode);
            n.getState().addVisit();
            return n;
        }
        return rootNode;
    }

    public void backpropagation(Node endNode, Counter resultCounter) {
        Node upNode = endNode;
        while (upNode != null) {
            upNode.getState().addVisit();
            if(upNode.getState().getCounter() == resultCounter){
                upNode.getState().addWin();
            }else {

                upNode.getState().subWin();
            }
            upNode = upNode.getParent();

        }
    }
}


//
//    public int play(){
//        root = firstNode;
//        root.generateChildrenNodes();
//
//        for (int i = 0; i < 10; i++) {
//
//            root.simulate();
//            root.chooseHighValueNode();
//            root.simulation();
//            root.backpropagate();
//        }
//        root.chooseHighValueNode();
//        return root.getMove();
//        root.playRandomMove();
//        roo
//
//    }
//
//
//
//
//
//}

