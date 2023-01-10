package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.concurrent.ThreadLocalRandom;


public class AlanisFourconnect extends Player {
  public AlanisFourconnect(Counter counter) {
    //TODO: fill in your name here
    super(counter, AlanisFourconnect.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    Position position = new Position(0,5);
    System.out.printf(String.valueOf(board.hasCounterAtPosition(position)));
//    int middleColumn = (int) Math.floor((board.getConfig().getWidth() / 2));
//    for (int i = 0; i < board.getConfig().getWidth(); i++) {
    if (!board.hasCounterAtPosition(position)){
      return (int) Math.floor((board.getConfig().getWidth() / 2));
    } else {
      System.out.println("lol");
      return ThreadLocalRandom.current().nextInt(0, 9);
    }
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
  }
}