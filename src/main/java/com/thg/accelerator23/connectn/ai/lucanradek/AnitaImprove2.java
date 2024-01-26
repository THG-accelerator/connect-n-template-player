package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Arrays;


public class AnitaImprove2 extends Player {

    public AnitaImprove2(Counter counter) {
        // Anita Improvise, created by Radek
        super(counter, AnitaImprove2.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        return 4;
    }

    public class BitBoard {
        int[] bitCounters;

        public BitBoard() {
            this.bitCounters = new int[10];
            Arrays.fill(this.bitCounters, 0b00000000);
        }

        public int getFirstEmptyCellInCol(int colVal) {
            for (int i = 1; i <= 128; i *= 2) {
                if (colVal < i)
                    return i;
            }
            return 128;
        }

        public int[] getFirstEmptyCellInAllCols() {
            int[] firstEmptyCells = new int[10];
            for (int i = 0; i < firstEmptyCells.length; i++) {
                firstEmptyCells[i] = getFirstEmptyCellInCol(this.bitCounters[i]);
            }
            return firstEmptyCells;
        }

        public void addToCol(int colIndex) {
            int firstEmptyCell = getFirstEmptyCellInCol(this.bitCounters[colIndex]);
            this.bitCounters[colIndex] += firstEmptyCell;
        }


    }
}
