import analysis.BoardAnalyser;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.ruglas.Connecty;
import com.thg.accelerator23.connectn.ai.ruglas.TestMove;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Connecty AI = new Connecty(Counter.X);
        GameConfig config = new GameConfig(10, 8, 4);
        Board dougieBoard = new Board(config);

        ArrayList<ArrayList<Position>> posnList = TestMove.getAdjacentNPositions(dougieBoard, new Position(0, 0), 4);

        for (ArrayList<Position> posns : posnList) {
            System.out.println("New line");
            for (Position posn : posns) {
                System.out.println("{" + posn.getX() + "," + posn.getY() + "}" );
            }

        }
    }
}
