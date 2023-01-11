package com.thg.accelerator23.connectn.ai.lucky_randomizer;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

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
    CheckWhichColumnsAreEmpty checkWhichColumnsAreEmpty = new CheckWhichColumnsAreEmpty(board);
    List<Integer> emptyColumns = checkWhichColumnsAreEmpty.fullColumnChecker();
    int randomNumber = new Random().nextInt(0, emptyColumns.size());

    System.out.println("Random number: " + randomNumber);
    System.out.println("Available columns: " + emptyColumns);

    return emptyColumns.get(randomNumber);
  }
}