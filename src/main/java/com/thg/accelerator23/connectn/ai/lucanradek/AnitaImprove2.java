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

    byte[] myCounters = new byte[80];
    byte[] theirCounters = new byte[80];

    Int128 longInt = 223;


    public AnitaImprove2(Counter counter) {
        // Anita Improve, created by Radek
        super(counter, AnitaImprove2.class.getName());
    }

    protected int convertPositionToIndex(Position position) {
        return position.getX() * 10 + position.getY();
    }


    protected byte[] addPosition(byte[] positions, Position position) {
        positions[convertPositionToIndex(position)] = 1;
        return positions;
    }

    protected void updateTheirCounters(Position position) {
        this.theirCounters = addPosition(this.theirCounters, position);
    }

    protected void updateMyCounters(Position position) {
        this.myCounters = addPosition(this.myCounters, position);
    }

    protected boolean isPositionEmpty(Position position) {
        int index = convertPositionToIndex(position);
        return myCounters[index] == 0 || theirCounters[index] == 0;
    }

    protected void findTheirCounters(Board board) {
        for (int row = 0; row < board.getConfig().getWidth(); row++) {
            for (int col = 0; col < board.getConfig().getHeight(); col++) {
                Position tempPosition = new Position(row, col);
                boolean canUpdateTheirCounters = board.hasCounterAtPosition(tempPosition)
                        && !board.getCounterAtPosition(tempPosition).equals(this.getCounter())
                        && !this.isPositionEmpty(tempPosition);
                if (canUpdateTheirCounters) {
                    updateTheirCounters(tempPosition);
                    return;
                }
            }
        }
    }


    @Override
    public int makeMove(Board board) {
        findTheirCounters(board);


        int x = 1;
        int y = 2;


        updateMyCounters(new Position(x, y));
        return y;
    }
}
