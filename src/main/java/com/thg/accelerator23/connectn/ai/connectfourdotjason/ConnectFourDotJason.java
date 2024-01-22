package com.thg.accelerator23.connectn.ai.connectfourdotjason;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.connectfourdotjason.analysis.GameState;

import java.util.Map;


public class ConnectFourDotJason extends Player {
  public ConnectFourDotJason(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectFourDotJason.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    return minimax((new BoardAnalyser(board.getConfig())).calculateGameState(board));
  }

  public int minimax(GameState gameState) {
    if ()
  }
}
