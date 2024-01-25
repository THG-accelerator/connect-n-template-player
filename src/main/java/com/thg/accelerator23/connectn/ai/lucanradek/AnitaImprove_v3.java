package com.thg.accelerator23.connectn.ai.lucanradek;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class AnitaImprove_v3 extends Player {

    private StringBuilder myCounters;
    private StringBuilder theirCounters;

    private BitBoard currentBoard;
    private Simulator simulator;
    private final int turn;

    public AnitaImprove_v3(Counter counter) {
        // Anita Improve, created by Radek
        super(counter, AnitaImprove_v3.class.getName());
        this.turn = 0;
    }

    @Override
    public int makeMove(Board board) {
        if (turn == 0) {
            // Create our board
            currentBoard = new BitBoard(board);
            // Create Simulator
            simulator = new Simulator(board.getConfig().getWidth());
            // Play first move
            /*  ......  */
        } else if (turn == 1) {
            //play a specific strategy
            /*  ......  */
        } else if (turn < 4) {
            // play another specific strategy
            /*  ......  */
        } else {
            currentBoard.update(board);
            // Let Monte Carlo simulation decide
            long start = System.currentTimeMillis();                        // Saving starting time
            boolean learn = true;                                           // Let AI learn something
            simulator.setCurrentBoard(currentBoard);                        // Set current board to simulator
            simulator.resetProbability();                                   // Reset probability
            simulator.resetNtrials();                                       // Reset number of trials
            while (learn) {
                for (int i = 0; i < 1000; i++) {                            // !!!! CHECK THIS !!! CHECK HOW MANY TRIALS WE CAN MAKE
                    simulator.runTrial();                                   // AT THE FIRST ATTEMPTED MOVE WITH MONTE CARLO SIMULATOR (WORST CASE)
                }
                long lap = System.currentTimeMillis();
                if (((lap - start) / 1000.0) > 8.0) {
                    learn = false;
                }
            }
            return simulator.bestMove();
        }
    }
}
