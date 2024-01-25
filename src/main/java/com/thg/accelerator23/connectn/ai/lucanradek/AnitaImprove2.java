package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thehutgroup.accelerator.connectn.

import javax.sound.sampled.Line;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnitaImprove2 extends Player {

    public class ByteBoard {
        byte[] byteCounters;
        public ByteBoard() {:
            this.byteCounters = new byte[10]{0b00000000, 0b00000000, 0b00000000, 0b00000000, 0b00000000,
                    0b00000000, 0b00000000, 0b00000000, 0b00000000, 0b00000000};
        }
    }
    public AnitaImprove2(Counter counter) {
        // Anita Improvise, created by Radek
        super(counter, AnitaImprove2.class.getName());
    }



    @Override
    public int makeMove(Board board) {
        return 4;
    }
}
