import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class City {
    private Neighborhood[][] map;
    private LinkedList<Neighborhood> infectedQueue;
    private int cycles;

    City(int length, int width) {
        this.map = new Neighborhood[length][width];
        this.cycles = 0;
        this.infectedQueue = new LinkedList<Neighborhood>();

        for (int i = 0; i < this.map.length; i ++) {
            for (int j = 0; j < this.map.length; j ++) {
                if (Math.random() <= 0.01) {
                    this.map[i][j] = new Neighborhood("i", j, i);
                    this.infectedQueue.offer(map[i][j]);
                } else {
                    this.map[i][j] = new Neighborhood("r", j, i);
                }
            }
        }
    }

    City(Neighborhood[][] map) {
        this.map = map;
        this.cycles = 0;
    }

    public void runCycle() {
        Stack<Neighborhood> pStack = new Stack<Neighborhood>();
        for (int i = 0; i < infectedQueue.size(); i ++) {
            for (int y = infectedQueue.get(i).getY() - 1; y < infectedQueue.get(i).getY() + 1; y++) {
                for (int x = infectedQueue.get(i).getX() - 1; x < infectedQueue.get(i).getX() + 1; x ++) {
                    if (pointInBounds(x, y)) {
                        if (this.map[y][x].getType().equals("r")) {
                            this.map[y][x].setType("P1");
                            pStack.add(map[y][x]);
                        } else if (map[y][x].getType().equals("P1")) {
                            this.map[y][x].setType("P2");
                        }
                    }
                }
            }
        }
        for (int i = 0; i < pStack.size(); i ++) {
            Neighborhood currentHood = pStack.pop();
            if (currentHood.getType().equals("P1")) {
                if (Math.random() <= Values.getP1()) {
                    currentHood.setType("i");
                    currentHood.setTypeCycles(Values.getV() - infectedQueue.peek().getTypeCycles());
                    infectedQueue.offer(currentHood);
                }
            } else {
                if (Math.random() <= Values.getP2()) {
                    currentHood.setType("i");
                    currentHood.setTypeCycles(Values.getV() - infectedQueue.peek().getTypeCycles());
                    infectedQueue.offer(currentHood);
                }
            }
        }

        if (this.cycles > Values.getD()) {
            int vaccinesAdministered = 0;
            while (vaccinesAdministered < Values.getA()) {
                int x = (int) (Math.random() * (this.map[0].length + 1));
                int y = (int) (Math.random() * (this.map.length + 1));
                if (map[y][x].getType().equals("r")) {
                    map[y][x].setType("v");
                    vaccinesAdministered = vaccinesAdministered + 1;
                }
            }
        }
        this.cycles ++;

    }

    private boolean pointInBounds(int x, int y) {
        return ((x >= 0) && (x < this.map[0].length) && (y >= 0) && (y < this.map.length));
    }


    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.map.length; i ++) {
            for (int j = 0; j < map[0].length; j ++) {
                if (map[i][j].getType().equals(".")) {
                    str = str + "   ";
                } else if ((!map[i][j].getType().equals("v")) && (!map[i][j].getType().equals("i"))) {
                    str = str + " r ";
                } else {
                    str = str + " " + map[i][j].getType() + " ";
                }
            }
            str = str + "\n";
        }
        return str;
    }
}
