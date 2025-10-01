import javax.swing.*;
import java.util.*;

public class Board {
    public Map<Integer, Integer> snakes = new HashMap<>();
    public Map<Integer, Integer> ladders = new HashMap<>();

    public Board() {
        // งู 2 ตัว
        snakes.put(14, 7);
        snakes.put(12, 3);

        // บันได 2 ตัว
        ladders.put(2, 6);
        ladders.put(5, 11);
    }

    public int checkPosition(int pos, JTextArea log, Player p) {
        if (ladders.containsKey(pos)) {
            log.append(p.getName() + " climbs a ladder! " + pos + " -> " + ladders.get(pos) + "\n");
            return ladders.get(pos);
        }
        if (snakes.containsKey(pos)) {
            log.append(p.getName() + " bitten by snake! " + pos + " -> " + snakes.get(pos) + "\n");
            return snakes.get(pos);
        }
        return pos;
    }
}
