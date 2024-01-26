package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static java.lang.Math.pow;

public class AnitaImprove2 extends Player {

    private BitBoard currentBoard;
    private Simulator simulator;
    private int turn;

    private int[] lastFullCellPerCol;

    int lastMove;

    public AnitaImprove2(Counter counter) {
        // Anita Improve, created by Radek
        super(counter, AnitaImprove2.class.getName());
        this.currentBoard = new BitBoard();
        this.turn = 0;
    }

    private int[] getLastFullCellPerCol(Board board) {
        int[] thisArray = new int[10];
        for (int i = 0; i < 10; i++) {
            thisArray[i] = -1;
            for (int j = 0; j < 8; j++) {
                Position pos = new Position(i, j);
                if (board.hasCounterAtPosition(pos)) {
                    if (thisArray[i] < j) { // We shouldn't need this but just to be safe
                        thisArray[i] = j;
                    }
                }
            }
        }
        return thisArray;
    };

    private int playStrategy1(Board board) {
        // Check where do we have possible counters in board (maybe if we are playing second)
        lastFullCellPerCol = getLastFullCellPerCol(board);
        // We are going to play in the middle
        if (lastFullCellPerCol[4] < 0) {
            // This middle column is empty, play at 4
            // Play move in bitboard
            currentBoard.play(1, 4);
            lastMove = 4;   // Save this move
            return 4;
        }
        // It is the first turn, so if column 4 has a counter, column 5 is empty, play here
        // Play move in bitboard
        currentBoard.play(1, 5);
        lastMove = 5;   // save this move
        return 5;
    };

    private int playStrategy2(Board board) {
        // Check where do we have possible counters in board (maybe if we are playing second)
        lastFullCellPerCol = getLastFullCellPerCol(board);
        // WE WILL PLAY A STRATEGY
        return 5;
    };

    private int playStrategy3(Board board) {
        // Check where do we have possible counters in board (maybe if we are playing second)
        lastFullCellPerCol = getLastFullCellPerCol(board);
        // WE WILL PLAY A STRATEGY
        return 5;
    };

    // METHOD CALLED IN GAME TO MAKE A NEW MOVE
    @Override
    public int makeMove(Board board) {
        turn++;     // new turn to play
        if (turn == 1) {
            currentBoard.update(this.getCounter().getOther(), board);       // Update BitBoard with opponent move
            simulator = new Simulator();                                    // Create Simulator
            return playStrategy1(board);                                    // Play strategy
        }
        if (turn == 2) {
            currentBoard.update(this.getCounter().getOther(), board);       // Update BitBoard with opponent move
            return playStrategy2(board);                                    // Play strategy
        }
        if (turn == 3) {
            currentBoard.update(this.getCounter().getOther(), board);       // Update BitBoard with opponent move
            return playStrategy3(board);                                    // Play strategy
        }
        currentBoard.update(this.getCounter().getOther(), board);           // Update BitBoard with opponent move
        simulator.learn(currentBoard);                                      // Let Simulator study the board
        lastMove = simulator.bestMove();                                    // Save last move played
        currentBoard.play(1, lastMove);
        return lastMove;
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

        public void update(Counter opponentCounter, Board board) {
            // int opponentIndex = (opponentCounter == Counter.X) ? 0 : 1; // assumes that X is first to start
            int opponentIndex = 0;
            for (int i = 0; i < bitCounters[opponentIndex].length; i++) {
                bitCounters[opponentIndex][i] = getUpdatedColumn(board, i, opponentCounter);
            }
        }
    }

    // SIMULATOR CLASS (AI)
    public class Simulator {
        private final Random random = new Random();     // Field of the class
        private BitBoard currentBoard;                  // Set with setter at each turn
        private final float[] probability;              // Set to zero at each turn
        private int nTrials;                            // Set to zero at each turn
        private BitBoard tmpBoard;                      // Set equal to currentBoard at beginning of each trial, updated during trial
        private int initX;                              // Set at beginning of each trial

        // CONSTRUCTOR
        public Simulator() {
            this.nTrials = 0;                           // Initialise number of trials to zero
            this.probability = new float[10];
            Arrays.fill(probability, 0.0f);         // Initialise probability with zero
        }

        //SETTERS
        private void setCurrentBoard(BitBoard currentBoard) {
            this.currentBoard = currentBoard;
        }

        // RESET PROBABILITY TO ZERO
        private void resetProbability() {
            for (int i = 0; i < 10; i++) {
                probability[i] = 0.0f;
            }
        }

        // RESET NUMBER OF TRIALS TO ZERO
        private void resetNTrials() {
            this.nTrials = 0;
        }

        // RUN ONE SINGLE TRIAL GAME
        private void runTrial() {
            //  WE NEED TO DECIDE WHO IS OUR PLAYER AND WHO IS THE OPPONENT (EITHER 0 OR 1).
            // NOW I AM ASSUMING THAT WE ARE PLAYER 1 AND OPPONENT IS PLAYER 0!
            nTrials++;                                              // Accumulate one trial in counter
            tmpBoard = currentBoard.deepCopy();                     // Copy initial board
            // Updated at each turn in each trial
            int turn = 0;                                           // Start turn counter from 0
            boolean play = true;
            while (play) {
                turn++;                                             // New turn
                // Updated at each turn in each trial
                int currentX = -1;
                while (currentX < 0) {
                    currentX = random.nextInt(0, 10);   // Get random x position in board
                    if (tmpBoard.isFullAt(currentX)) {              // If that column is full
                        currentX = -1;                              // reject random currentX
                    }
                    if (turn == 1) {                                // If this is the first turn we are playing in this trial game,
                        initX = currentX;                           // Save played move at first turn
                    }
                }
                tmpBoard.play(turn % 2, currentX);        // Player plays turn
                if (tmpBoard.isWonBy(turn % 2)) {         // If current Player has won,
                    probability[initX] += (float) turn % 2;         // This should work fine, if our player is player 1 (100% of winning from initX if player 1 won, 0% of winning from initX if player 0 won)
                    play = false;                                   // Finish the trial.
                } else if (tmpBoard.isDraw()) {                     // If it's a draw
                    probability[initX] += 0.50f;                    // probability of winning from initX is 50%
                    play = false;                                   // Finish the trial
                }
            }
        }

        // EVALUATE BEST MOVE WITH PROBABILITY
        public int bestMove() {
            float max = -100.0f;
            int imax = -1;
            for (int i = 0; i < probability.length; i++) {
                probability[i] = probability[i] / nTrials;
                System.out.printf("i %d , P(i) : %.3f\n", i, probability[i]);
                System.out.printf("NTrials : %d", nTrials);
                if (probability[i] > max) {
                    max = probability[i];
                    imax = i;
                }
            }
            return imax;
        }

        public void learn(BitBoard currentBoard) {
            int TIME_LIMIT = 8;
            long start = System.currentTimeMillis();                        // Saving starting time
            boolean learn = true;                                           // Let AI learn something
            simulator.setCurrentBoard(currentBoard);                        // Set current board to simulator
            simulator.resetProbability();                                   // Reset probability
            simulator.resetNTrials();                                       // Reset number of trials
            while (learn) {
                for (int i = 0; i < 1000; i++) {                            // !!!! CHECK THIS !!! CHECK HOW MANY TRIALS WE CAN MAKE
                    simulator.runTrial();                                   // AT THE FIRST ATTEMPTED MOVE WITH MONTE CARLO SIMULATOR (WORST CASE)
                }
                long lap = System.currentTimeMillis();
                if (((lap - start) / 1000.0) > TIME_LIMIT) {
                    learn = false;
                }
            }
        }

    }

}
