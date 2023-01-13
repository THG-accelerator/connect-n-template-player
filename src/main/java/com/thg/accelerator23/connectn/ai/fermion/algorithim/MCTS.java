package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;
import com.thg.accelerator23.connectn.ai.fermion.renderer.ConsoleRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {
    Board board;

    public MCTS(Board board) {
        this.board = board;
    }

        public Node selectHighestUCTNode(Node parentNode) {
        ConsoleRenderer obj = new ConsoleRenderer();
            Node node = parentNode;
            while (node.getChildren().size() != 0) {
                node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUCTValue(parentNode)));
//                System.out.println("going down"+node.getChildren().size());
//                obj.render(null,node.getParent().getState().getBoard());
//                obj.render(null,node.getState().getBoard());
            }
        return node;
    }



    public Node expand(Node promisingNode, ArrayList<Integer> movesMade) {
        BoardAnalyser analyser = new BoardAnalyser(promisingNode.getState().getBoard().getConfig());

        if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd() && !promisingNode.hasChildren()) {
            promisingNode.generateChildrenNodes();

        }
        Node newNode = randomChildNode(promisingNode);
        movesMade.add(newNode.getMove());
        return newNode;
//        return  promisingNode;








    }


    public int actualPlay(Counter counter) {

        Tree tree = new Tree(counter);
        Node rootNode = tree.getRoot();

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);

        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {
//        for (int i = 0; i < 1000; i++) {
            ArrayList<Integer> movesMade = new ArrayList<>();

            Node promisingNode = selectHighestUCTNode(rootNode);

            BoardAnalyser analyser = new BoardAnalyser(promisingNode.getState().getBoard().getConfig());

            Node nodeToExplore = expand(promisingNode,movesMade);

            ConsoleRenderer obj = new ConsoleRenderer();


//            while (analyser.calculateGameState(nodeToExplore.getState().getBoard()).isEnd()) {
//                nodeToExplore = randomChildNode(promisingNode, movesMade);
//                movesMade.add(nodeToExplore.getMove());
//                System.out.println("a "+ nodeToExplore.getMove());
//                if (movesMade.size() == 10) {
////                    backpropagation(nodeToExplore, nodeToExplore.getState().getCounterOpposite());
//                    promisingNode.getState().setNodeWins(Integer.MAX_VALUE);
//                    nodeToExplore = expand(promisingNode.getParent(), movesMade);
//                    break;
//                }
//            }

//            System.out.println(analyser.calculateGameState(nodeToExplore.getState().getBoard()).isEnd());

                Counter results = simulateMoves(nodeToExplore, rootNode.getState().getCounter());

                backpropagation(nodeToExplore, results, rootNode.getState().getCounter());
            }
//        }





//        for (Node child : tree.getRoot().getChildren()) {
//            System.out.println("1: "+child.getMove()+" size of child= "+child.getChildren().size()  +" UCT "+ child.getUTCValue(tree.getTrueRoot())+" "+child.getNumberOfChildrenLevels() +" wins "+ child.getState().getNodeWins()+ " vist "+ child.getState().getNodeVisits() );
//            if(child.getChildren().size()!=0 && child.getState().getNodeVisits()>100) {
//                for (Node childChild : child.getChildren()) {
//                    System.out.println("2: parent=>"+child.getMove()+" Size of child=" + childChild.getChildren().size()+" " +childChild.getMove()+"UCT "+ childChild.getUTCValue(tree.getTrueRoot())+" wins" +childChild.getState().getNodeWins() + " vists"+childChild.getState().getNodeVisits());
//                    if (childChild.getChildren().size() != 0) {
////                        System.out.println("2: parent= "+child.getMove()+" Size of child= "+childChild.getChildren().size());
//                        for (Node childChildChild : childChild.getChildren()) {
//                            System.out.println("3: parent=>"+childChild.getMove()+"vists=>"+childChildChild.getState().getNodeVisits()+" wins=>"+childChildChild.getState().getNodeWins()+" " +childChildChild.getMove()+" UCT: " +childChildChild.getUTCValue((tree.getTrueRoot())));
//                            if (childChildChild.getChildren().size() != 0 && childChildChild.getState().getNodeVisits()> 50) {
//                                for (Node childChildChildChild : childChildChild.getChildren()) {
//                                    System.out.println("4: parent=>"+childChildChild.getMove()+"vists=>"+childChildChildChild.getState().getNodeVisits()+" wins=>"+childChildChildChild.getState().getNodeWins()+" " +childChildChildChild.getMove()+" UCT: " +childChildChildChild.getUTCValue((tree.getTrueRoot())));
//                                    if (childChildChildChild.getChildren().size() != 0 && childChildChildChild.getState().getNodeVisits()>5) {
//                                        for (Node childChildChildChildChild : childChildChildChild.getChildren()) {
//                                            System.out.println("5: parent=>" + childChildChildChild.getMove() + "vists=>" + childChildChildChildChild.getState().getNodeVisits() + " wins=>" + childChildChildChildChild.getState().getNodeWins() + " " + childChildChildChildChild.getMove() + " UCT: " + childChildChildChildChild.getUTCValue((tree.getTrueRoot())));
//                                            if (childChildChildChildChild.getChildren().size() != 0 && childChildChildChildChild.getState().getNodeVisits()>5) {
//                                                for (Node childChildChildChildChildChild : childChildChildChildChild.getChildren()) {
//                                                    System.out.println("6: parent=>" + childChildChildChildChild.getMove() + "vists=>" + childChildChildChildChildChild.getState().getNodeVisits() + " wins=>" + childChildChildChildChildChild.getState().getNodeWins() + " " + childChildChildChildChildChild.getMove() + " UCT: " + childChildChildChildChildChild.getUTCValue((tree.getTrueRoot())));
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }



        System.out.println("_________________MIDDDLE");
        System.out.println(tree.getTrueRoot().getChildren().size());
        for (Node child : tree.getTrueRoot().getChildren()) {
            System.out.println("Move: "+child.getMove() +" Vists: "+ child.getState().getNodeVisits() +" Wins: "+ child.getState().getNodeWins()+" UCT: "+ child.getUCTValue(tree.getTrueRoot()));
        }

        Node winnerNode = tree.getTrueRoot().childMostVisits();

        tree.setRoot(winnerNode);
        return winnerNode.getMove();
    }




    private Counter simulateMoves(Node nodeToExplore, Counter rootCounter) {
        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
        GameState gameState = boardChecker.calculateGameState(nodeToExplore.getState().getBoard());
        ConsoleRenderer obj = new ConsoleRenderer();

        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState().clone();
//        if(gameState.isDraw()){
//            System.out.println("DRAW");
//            System.out.println(gameState.getWinner());
//        }
        if (rootCounter.getOther() == gameState.getWinner()) { //opponent has won
            nodeToExplore.getParent().getState().setNodeWins(Integer.MIN_VALUE);
            return gameState.getWinner();

        } else if (rootCounter == gameState.getWinner()) {
//            obj.render(null,nodeToExplore.getState().getBoard());
//            System.out.println(copynode == nodeToExplore);
//            System.out.println(nodeToExplore.getNumberOfParents()+"parents "+nodeToExplore.getMove());
//
//            System.out.println("Won game state");
        }

        while(!gameState.isEnd()){
            try {

                int move = interNode.playRandomMove(interState.getBoard());

                interState.setBoard(new Board(interState.getBoard(), move, interState.getCounter()));

                gameState = boardChecker.calculateGameState(interState.getBoard());

                interState.invertCounter();
            } catch(InvalidMoveException e) {
                System.out.println("Sim fail");
                throw new RuntimeException(e);
            }
        }
        if(gameState.isDraw()){
            System.out.println("DRAW");
            System.out.println(gameState.getWinner());
        }


        return gameState.getWinner();
    }


    public Node randomChildNode(Node rootNode) {
        if(rootNode.getChildren().size() != 0) {
            Random r = new Random();
//            int nextNode = r.nextInt(rootNode.getChildren().size());
//            Node n = rootNode.getChildren().get(nextNode);
            return rootNode.getChildren().get(r.nextInt(rootNode.getChildren().size()));
        }
        return rootNode;
    }

    public Node randomChildNode(Node rootNode, ArrayList<Integer> movesMode) {
        int childrenSize = rootNode.getChildren().size();
        Random r = new Random();
        int move = r.nextInt(childrenSize);
        if(childrenSize != 0) {
            while( movesMode.contains(move)) {
                move = r.nextInt(childrenSize);
            }
            return rootNode.getChildren().get(move);
        }
        return rootNode;
    }


    public void backpropagation(Node endNode, Counter simWinnerNode, Counter rootCounter) {
        Node upNode = endNode;
//        System.out.println("Start from terminal");
        while (upNode != null) {
//            System.out.println(upNode.getState().getNodeVisits());/**/
            upNode.getState().addVisit();
            if (upNode.getState().getCounterOpposite() == simWinnerNode) {
                upNode.getState().addWin();
            } else {
//                upNode.getState().subWin();
            }
            upNode = upNode.getParent();
        }

    }
}



