package com.thg.accelerator23.connectn.ai.lucky_randomizer;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.*;

import java.util.List;
import java.util.Random;

//TODO: Once done, remember to add version to json file (line 29 of README) and JITPACKy thingy

public class LuckyRandomX extends Player {
  public LuckyRandomX(Counter counter) {
    //TODO: fill in your name here
    super(counter, LuckyRandomX.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    int generateRandomNumber = new Random().nextInt(0, 9);
    System.out.println(generateRandomNumber);
    FullColumnsChecker fullColumnsChecker = new FullColumnsChecker(board);
    List<Integer> emptyColumns = fullColumnsChecker.fullColumnChecker();
    System.out.println(emptyColumns);
    //if column is full, generate Random number again.
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
//    return generateRandomNumber;
    return emptyColumns.get(generateRandomNumber);
  }
}
