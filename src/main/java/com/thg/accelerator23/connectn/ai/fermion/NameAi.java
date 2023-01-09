package com.thg.accelerator23.connectn.ai.fermion;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;


public class NameAi extends Player {
  public NameAi(Counter counter) {
    //TODO: fill in your name here
    super(counter, NameAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it


    //Things to do:
    /** Things to do:
     * Validate moves:
     *    Track moves made in each column
     *    Make move within boundary
     *
     * Generate move:
     *    Algorithim move maker
     *
     *
     * Play game:
     *      Upload dependancie
     *      Make working local version
     *
     * Return a random move that gets validated
     *
     *
     * */

    int randomColumn = (int) (Math.random() * (10));





//    return validMove.get(new Random().nextInt(validMove.size())

    return randomColumn;
  }
}

