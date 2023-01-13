package com.thg.accelerator23.connectn.ai.rosseleanor;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;

public interface AI {
    int getMove() throws InvalidMoveException;

}
