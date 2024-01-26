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

        public boolean isFullAt(int colIndex) {
            return getFirstEmptyCellInCol(bitCounters[colIndex]) > 128;
        }

        public boolean isRowWin(int col1, int col2, int col3, int col4) {
            int result = col1 & col2 & col3 & col4;
            return result != 0;
        }

        public boolean isDiagShiftRightWin(int col1, int col2, int col3, int col4) {
            return isRowWin(col1, col2 << 1, col3 << 2, col4 << 3);
        }

        public boolean isDiagShiftLeftWin(int col1, int col2, int col3, int col4) {
            return isRowWin(col1 << 3, col2 << 2, col3 << 1, col4);
        }

        public boolean isColWin(int colVal) {
            for (int i = 0b00001111; i <= 0b11110000; i = i << 1) {
                if ((colVal & i) != 0)
                    return true;
            }
            return false;
        }

        public boolean isWin() {
            // rows and diagonals winning
            for (int i = 0; i <= 4; i++) {
                if (isRowWin(bitCounters[i], bitCounters[i + 1], bitCounters[i + 2], bitCounters[i + 3]))
                    return true;
                if (isDiagShiftLeftWin(bitCounters[i], bitCounters[i + 1], bitCounters[i + 2], bitCounters[i + 3]))
                    return true;
                if (isDiagShiftRightWin(bitCounters[i], bitCounters[i + 1], bitCounters[i + 2], bitCounters[i + 3]))
                    return true;
            }
            // cols winning
            for (int i = 0; i < bitCounters.length; i++) {
                if (isColWin(bitCounters[i]))
                    return true;
            }
            return false;
        }

        public boolean isDraw() {
            return isFullAt(0) && isFullAt(1) && isFullAt(2) && isFullAt(3) &&
                    isFullAt(4) && isFullAt(5) && isFullAt(6) && isFullAt(7);
        }
    }
}
