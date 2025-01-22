package com.thg.accelerator23.connectn.ai.good_ai_mate;
import com.thehutgroup.accelerator.connectn.player.Counter;
public class Window {
    private int score;
    private final Counter[] array = new Counter[4];

    public Window(Counter a, Counter b, Counter c, Counter d) {
        this.array[0] = a;
        this.array[1] = b;
        this.array[2] = c;
        this.array[3] = d;
    }

    public int getWindowScore(Counter botPiece, Counter emptyPiece){
        //TODO logic for calculating score
        int score = 0;
        int botPieceCount = 0;
        int emptyPieceCount = 0;
        int oppPieceCount = 0;
        Counter oppPiece = botPiece.getOther();

        // Iterating over each counter in window
        for (Counter c : array) {
            if (c == botPiece) {
                botPieceCount++;
            } else if (c == emptyPiece) {
                emptyPieceCount++;
            } else {
                oppPieceCount++;
            }
        }

        // Scoring logic
        if (botPieceCount == 4) {
            score += 100;
        } else if (botPieceCount == 3 && emptyPieceCount == 1) {
            score += 5;
        } else if (botPieceCount == 2 && emptyPieceCount == 2) {
            score += 2;
        } else if (oppPieceCount == 3 && emptyPieceCount == 1) {
            score -= 4;
        }
        return score;
    }
}
