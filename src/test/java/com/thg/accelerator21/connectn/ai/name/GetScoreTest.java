package com.thg.accelerator21.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.good_ai_mate.GoodAiMate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetScoreTest {
    @Test
    public void test() {
        Counter[][] placement = new Counter[][] {{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null},{Counter.X,Counter.O,Counter.X,Counter.O,null,null}};
        GameConfig myConfig = new GameConfig(10,6,4);
        Board board = new Board(placement,myConfig);
        GoodAiMate ai = new GoodAiMate(Counter.X);
        assertEquals(0,ai.getScore(board));
    }
}
