package com.thg.accelerator23.connectn.ai.funconcerto;

public class AlphaBeta {
    if(game == 'over'|| level == 0){
        return score;
    }
    if(player == 1){
        children.forEach({
                score = minimax(level-1,2, alpha, beta);
                if (score > alpha) alpha = score;
                if (alpha >= beta) break;
        });
        return alpha;
        } else{
        children.forEach({
                Score score = minimax(level-1,1, alpha, beta);
        if (score < beta) beta = score;
        if (alpha >= beta) break;
        });
        return beta;
    }

}
