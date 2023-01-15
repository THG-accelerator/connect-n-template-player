package com.thg.accelerator23.connectn.ai.funconcerto;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.funconcerto.analysis.GameState;
import com.thg.accelerator23.connectn.ai.funconcerto.movetree.MoveTree;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class FunConcertoAi extends Player {

  MoveTree tree = new MoveTree();
  MinMax mM;
  Board previousBoard = new Board(new GameConfig(10,8,4));

  public FunConcertoAi(Counter counter) {

    super(counter, FunConcertoAi.class.getName());
    this.mM = new MinMax();
  }

  public static int indexOf(ArrayList<Integer> arr, int val) {
    return IntStream.range(0, arr.size()).filter(i -> arr.get(i) == val).findFirst().orElse(-1);
  }

  @Override
  public int makeMove(Board board) {

    ArrayList<Integer> validMoves = getValidLocations(board);

    if(isBlankBoard(board) && tree.getChildren().size() == 0){ // Runs on the first move of the game
      addNodesToTree(board, 80);
    }
    else if(!isBlankBoard(board) && tree.getChildren().size() == 0) { // Checks if the player is going second
      try {
        tree.addNode(getPreviousMove(board));
      } catch (InvalidMoveException e) {
      }
      tree = tree.getChildren().get(0);
      addNodesToTree(board,80);
    } else {
      int previousMove = getPreviousMove(board);
      ArrayList<Integer> validMovesForPreviousBoard = getValidLocations(previousBoard);
      tree = tree.getChildAtPosition(indexOf(validMovesForPreviousBoard, previousMove));
      addNodesToTree(board,80);
    }

    int[] newMove = mM.miniMax(tree,System.currentTimeMillis(),true);

    try {
      previousBoard = new Board(board, newMove[1],getCounter());
    } catch (InvalidMoveException e) {
    }
    tree = tree.getChildAtPosition(indexOf(validMoves, newMove[1]));
    return newMove[1];
  }

  public void addNodesToTree(Board board, int depth){
    ArrayList<Integer> validMoves = getValidLocations(board);
    for (int i = 0; i < validMoves.size(); i++) {
      try {
        tree.addNode(validMoves.get(i));
      } catch (InvalidMoveException e) {
      }
    }
    for(int i = 0; i < depth-1; i++) {
      for (int j = 0; j < tree.getChildren().size(); j++) {
        Board secondMoveBoard = tree.getChildren().get(j).getState();
        ArrayList<Integer> validMovesForSecondMove = getValidLocations(secondMoveBoard);
        for (int k = 0; k < validMovesForSecondMove.size(); k++) {
          try {
            tree.getChildren().get(j).addNode(validMovesForSecondMove.get(k));
          } catch (InvalidMoveException e) {
          }
        }
      }
    }
  }

  public ArrayList<Integer> getValidLocations(Board board){
    ArrayList list = new ArrayList();
    for(int i = 0; i < 10; i++){
      if(!board.hasCounterAtPosition(new Position(i,7))){
        list.add(i);
      }
    }
    return list;
  }

    public int getPreviousMove(Board newBoard) {
      for(int i = 0; i < 10; i++){
        try {
          Board newBoard1 = new Board(previousBoard, i, getCounter().getOther());
          Counter counter = getTopCounterOnColumn(newBoard1, i);
          Counter counter1 = getTopCounterOnColumn(newBoard, i);
          int index1 = getTopIndexOfCounterOnColumn(newBoard1, i, counter);
          int index2 = getTopIndexOfCounterOnColumn(newBoard, i, counter);
          if(index1 == index2) {
            return i;
          }
        }catch(InvalidMoveException e){

        }
      }
      return -1;
    };

  public int getTopIndexOfCounterOnColumn(Board board, int column, Counter counter){
    for(int i = 7; i >= 0; i--){
      if(board.getCounterAtPosition(new Position(column, i)) == counter) {
        return i;
      }
    }
    return -1;
  }

  public Counter getTopCounterOnColumn(Board board, int column) {
    for(int i = 7; i >= 0; i--){
      if(board.hasCounterAtPosition(new Position(column, i))) {
        return board.getCounterAtPosition(new Position(column, i));
      }
    }
    return null;
  }

  public boolean isBlankBoard(Board board) {
    if(board.getCounterAtPosition(new Position(0,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(1,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(2,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(3,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(4,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(5,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(6,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(7,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(8,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(9,0)) != null) return false;
    if(board.getCounterAtPosition(new Position(10,0)) != null) return false;
    return true;
  }

}