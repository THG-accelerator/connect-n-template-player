package com.thg.accelerator23.connectn.ai.ruglas;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.Random;

<<<<<<< HEAD
    public class RandomAI extends Player {
        Counter opponentCounter;

        public RandomAI(Counter counter) {
            super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
        }

        @Override
        public int makeMove(Board board) {
            Random rand = new Random();
            return rand.nextInt(board.getConfig().getWidth());
        }

    }

=======
public class RandomAI extends Player {
    Counter opponentCounter;

    public RandomAI(Counter counter) {
        super(counter, com.thg.accelerator23.connectn.ai.ruglas.Connecty.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        Random rand = new Random();
        return rand.nextInt(board.getConfig().getWidth());
    }

}
>>>>>>> 7fe2fca42d1e1478483b5c5a8c2ff8b9ad5bd864
