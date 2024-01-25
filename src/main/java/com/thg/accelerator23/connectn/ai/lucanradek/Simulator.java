package com.thg.accelerator23.connectn.ai.lucanradek;

import java.util.Arrays;
import java.util.Random;

public class Simulator {
    private final Random random = new Random();     // Field of the class
    private BitBoard currentBoard;                  // Set with setter at each turn
    private final float[] probability;              // Set to zero at each turn
    private int ntrials;                            // Set to zero at each turn
    private BitBoard tmpBoard;                      // Set equal to currentBoard at beginning of each trial, updated during trial
    private int initX;                              // Set at beginning of each trial
    private final int width;                        // width of board

    // CONSTRUCTOR
    public Simulator(int width) {
        this.ntrials = 0;                           // Initialise number of trials to zero
        this.width = width;
        this.probability = new float[width];
        Arrays.fill(probability, 0.0f);         // Initialise probability with zero
    }

    //SETTERS
    public void setCurrentBoard(BitBoard currentBoard) {
        this.currentBoard = currentBoard;
    }

    // RESET PROBABILITY TO ZERO
    public void resetProbability() {
        for (int i = 0; i < width; i++) {
            probability[i] = 0.0f;
        }
    }

    // RESET NUMBER OF TRIALS TO ZERO
    public void resetNtrials() {
        this.ntrials = 0;
    }

    // RUN ONE SINGLE TRIAL GAME
    public void runTrial() {
        //  WE NEED TO DECIDE WHO IS OUR PLAYER AND WHO IS THE OPPONENT (EITHER 0 OR 1).
        // NOW I AM ASSUMING THAT WE ARE PLAYER 1 AND OPPONENT IS PLAYER 0!
        ntrials++;                                          // Accumulate one trial in counter
        tmpBoard.deepCopy(currentBoard);                    // Copy initial board
        // Updated at each turn in each trial
        int turn = 0;                                       // Start turn counter from 0
        boolean play = true;
        while (play) {
            turn++;                                         // New turn
            // Updated at each turn in each trial
            int currentX = -1;
            while (currentX < 0) {
                currentX = random.nextInt(0, width);  // Get random x position in board
                if (tmpBoard.isFullAt(currentX)) {          // If that column is full
                    currentX = -1;                          // reject random currentX
                }
                if (turn == 1) {                            // If this is the first turn we are playing in this trial game,
                    initX = currentX;                       // Save played move at first turn
                }
            }
            tmpBoard.play(turn % 2, currentX);   // Player plays turn
            if (tmpBoard.isWonBy(turn % 2)) {    // If current Player has won,
                probability[initX] += (float) turn % 2;     // This should work fine, if our player is player 1 (100% of winning from initX if player 1 won, 0% of winning from initX if player 0 won)
                play = false;                               // Finish the trial.
            } else if (tmpBoard.isDraw()) {                 // If it's a draw
                probability[initX] += 0.50f;                // probability of winning from initX is 50%
                play = false;                               // Finish the trial
            }
        }
    }

    // EVALUATE BEST MOVE WITH PROBABILITY
    public int bestMove() {
        float max = -100.0f;
        int imax = -1;
        for (int i = 0; i < probability.length; i++) {
            probability[i] = probability[i] / ntrials;
            if (probability[i] > max) {
                max = probability[i];
                imax = i;
            }
        }
        return imax;
    }

}