package com.thg.accelerator23.connectn.ai.rosselanor;

import com.thehutgroup.accelerator.connectn.player.*;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.rosselanor.analysis.BoardLine;
import com.thg.accelerator23.connectn.ai.rosselanor.model.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.concurrent.ThreadLocalRandom;


public class ConnectBot extends Player {

  private final String teamCounter;

  public ConnectBot(Counter counter) {
    super(counter, ConnectBot.class.getName());
    String teamCounter = counter.getStringRepresentation();
    this.teamCounter = teamCounter;
  }





  @Override
  public int makeMove(Board board) {
  
    int position = ThreadLocalRandom.current().nextInt(0, 10);

    if (isColumnFull(board, position)){
      return position+1;
    }
    else if (horizontalTrio(board)){
      return position;
    }
    else {
      return position;
    }
  }

  private boolean isColumnFull(Board board, int position) {

    int i = position;
    return IntStream.range(0, board.getConfig().getHeight())
            .allMatch(
                    j -> board.hasCounterAtPosition(new Position(i, j))
            );
  }

  private boolean horizontalTrio(Board board) {
    BoardAnalyser analysis = new BoardAnalyser(board.getConfig());
    List<Line> lines = analysis.getLines(board);
    for (int i = 0; i < lines.size(); i++) {
      Map result = analysis.getBestRunByColour(lines.get(i));
      if (result.containsValue(3)) {
        System.out.println(result);
        int comparison = (int) result.get(Counter.X);
        if (comparison == 3) {
          System.out.println('X');
        }
        else {
          System.out.println('O');
        }
        return true;
      }
    }
    return false;
  }
}