package com.thg.accelerator23.connectn.ai.ajtracey;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thg.accelerator23.connectn.ai.ajtracey.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.ajtracey.minimax.MiniMax;

import java.util.HashMap;

public class Test {

    public static void main(String[] args) {
        GameConfig g = new GameConfig(10, 8, 4);
        Board b = new Board(g);
        BoardAnalyser BA = new BoardAnalyser(g);
        MiniMax miniMax = new MiniMax();
        miniMax.constructTree(2, b, Counter.X);

        miniMax.getTree().getRoot().getScore();
    }
}