//TODO: replace name with firstnamesurname -- the artifactId from your pom.xml
package com.thg.accelerator.connectn.ai.name;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

//TODO: Rename this class
public class NameAI extends Player {
    public NameAI(Counter counter) {
        //TODO: fill in your name here
        super(counter, NameAI.class.getName());
    }

    @Override
    public int makeMove(Board board) {
        //TODO: some crazy analysis
        //TODO: make sure said analysis uses less than 2G of heap and returns within 2 seconds on Shaun's laptop
        return 0;
    }
}
