package com.thg.accelerator23.connectn.ai.politicallyconnect;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.politicallyconnect.analyser.BoardAnalyser;

import java.util.concurrent.ThreadLocalRandom;


public class AlanisFourconnect extends Player {
  public AlanisFourconnect(Counter counter) {
    //TODO: fill in your name here
    super(counter, AlanisFourconnect.class.getName());
  }

  @Override
  public int makeMove(Board board) {
//    Position position = new Position(0,5);

//    int middleColumn = (int) Math.floor((board.getConfig().getWidth() / 2));
//    for (int i = 0; i < board.getConfig().getWidth(); i++) {
    if (!board.hasCounterAtPosition(new Position(5, 0))) {
//      System.out.println(board.getCounterAtPosition(new Position(5, 0)));
//      System.out.println(!board.hasCounterAtPosition(new Position(0, 5)));
      return (int) Math.floor((board.getConfig().getWidth() / 2));
    } else {
      return winOnNext(board);
//      System.out.println("lol");
//      return ThreadLocalRandom.current().nextInt(0, 9);
    }

  }

    public int winOnNext(Board board) {
      if ()
      return ThreadLocalRandom.current().nextInt(0, 9);
      //TODO: some crazy analysis
      //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    }
  }
