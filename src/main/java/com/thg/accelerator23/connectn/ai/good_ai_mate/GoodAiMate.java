package com.thg.accelerator23.connectn.ai.good_ai_mate;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.ArrayList;


public class GoodAiMate extends Player {
  public GoodAiMate(Counter counter) {
    //TODO: fill in your name here
    super(counter, GoodAiMate.class.getName());
  }

  public int getScore(Board board) {
    int score = 0;
    ArrayList<Window> windows = new ArrayList<>();

    //Centre Column - for now we will treat as more important - for a 10-column board there are two central columns
    Window centre_array_left;
    Window centre_array_right;



    return score;

  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}
