package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.Arrays;

import static java.lang.Math.pow;


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
        int[][] bitCounters;

        public BitBoard() {
            this.bitCounters = new int[2][10];
            Arrays.fill(this.bitCounters[0], 0b00000000);
            Arrays.fill(this.bitCounters[1], 0b00000000);
        }

        public BitBoard(int[][] input) {
            this.bitCounters = input;
        }

        public BitBoard deepCopy() {
            int[][] originalBitCounters = this.getBitCounters();
            int[][] copiedBitCounters = new int[originalBitCounters.length][originalBitCounters[0].length];
            System.arraycopy(originalBitCounters, 0, copiedBitCounters, 0, originalBitCounters.length);
            return new BitBoard(copiedBitCounters);
        }

        public int[][] getBitCounters() {
            return bitCounters;
        }

        public int getFirstEmptyCellInCol(int colVal) {
            for (int i = 1; i <= 128; i *= 2) {
                if (colVal < i)
                    return i;
            }
            return 128;
        }

        public int[] getFirstEmptyCellInAllCols(int playerIndex) {
            int[] firstEmptyCells = new int[10];
            for (int i = 0; i < firstEmptyCells.length; i++) {
                firstEmptyCells[i] = getFirstEmptyCellInCol(this.bitCounters[playerIndex][i]);
            }
            return firstEmptyCells;
        }

        public void play(int playerIndex, int colIndex) {
            int firstEmptyCell = getFirstEmptyCellInCol(this.bitCounters[playerIndex][colIndex]);
            this.bitCounters[playerIndex][colIndex] += firstEmptyCell;
        }

        public boolean isFullAt(int colIndex) {
            return getFirstEmptyCellInCol(bitCounters[0][colIndex]) > 128 || getFirstEmptyCellInCol(bitCounters[0][colIndex]) > 128;
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

        public boolean isWonBy(int playerIndex) {
            // rows and diagonals winning
            for (int i = 0; i <= 4; i++) {
                if (isRowWin(bitCounters[playerIndex][i], bitCounters[playerIndex][i + 1], bitCounters[playerIndex][i + 2], bitCounters[playerIndex][i + 3]))
                    return true;
                if (isDiagShiftLeftWin(bitCounters[playerIndex][i], bitCounters[playerIndex][i + 1], bitCounters[playerIndex][i + 2], bitCounters[playerIndex][i + 3]))
                    return true;
                if (isDiagShiftRightWin(bitCounters[playerIndex][i], bitCounters[playerIndex][i + 1], bitCounters[playerIndex][i + 2], bitCounters[playerIndex][i + 3]))
                    return true;
            }
            // cols winning
            for (int i = 0; i < bitCounters.length; i++) {
                if (isColWin(bitCounters[playerIndex][i]))
                    return true;
            }
            return false;
        }

        public boolean isDraw() {
            return isFullAt(0) && isFullAt(1) && isFullAt(2) && isFullAt(3) &&
                    isFullAt(4) && isFullAt(5) && isFullAt(6) && isFullAt(7);
        }

        public int getUpdatedColumn(Board board, int colIndex, Counter opponentCounter) { // relies on Position(0,0) being at lower left corner
            int result = 0b00000000;
            for (int i = 0; i < board.getConfig().getHeight(); i++) {
                Position tempPosition = new Position(i, colIndex);
                if (board.hasCounterAtPosition(tempPosition) && board.getCounterAtPosition(tempPosition) == opponentCounter)
                    result += (int) pow(2, i);
            }
            return result;
        }

        public void update(Counter opponetCounter, Board board) {
            int opponentIndex = (opponetCounter == Counter.X) ? 0 : 1; // assumes that X is first to start
            for (int i = 0; i < bitCounters[opponentIndex].length; i++) {
                bitCounters[opponentIndex][i] = getUpdatedColumn(board, i, opponetCounter);
            }
        }
    }
}
