import java.util.ArrayList;
import java.util.Stack;

public class City {
    private Neighborhood[][] map;
    private ArrayList<Neighborhood> infectedQueue;
    private ArrayList<Neighborhood> vaccineQueue;
    private int cycles;
    private int emptyHoods;

    City(int length, int width) {
        this.map = new Neighborhood[length][width];
        this.cycles = 0;
        this.emptyHoods = length * width - 9;
        this.infectedQueue = new ArrayList<Neighborhood>(length);
        this.vaccineQueue = new ArrayList<Neighborhood>(length);

        for (int i = 0; i < this.map.length; i ++) {
            for (int j = 0; j < this.map[0].length; j ++) {
                this.map[i][j] = new Neighborhood(' ', j, i);
            }
        }
        int x = length/2 - 1;
        int y = length/2 - 1;
        for (int i = y; i <= y + 2; i ++) {
            for (int j = x; j <= x + 2; j ++){
                map[i][j].setType('i');
                infectedQueue.add(map[i][j]);
            }
        }

    }

    public void runCycle() {
        Stack<Neighborhood> pStack = new Stack<Neighborhood>();
        for (int i = 0; i < infectedQueue.size(); i ++) {
            for (int y = infectedQueue.get(i).getY() - 1; y <= infectedQueue.get(i).getY() + 1; y++) {
                for (int x = infectedQueue.get(i).getX() - 1; x <= infectedQueue.get(i).getX() + 1; x ++) {
                    if (validTile(x, y)) {
                        if (this.map[y][x].getP() == 0) {
                            this.map[y][x].setP(Const.P1);
                            pStack.push(map[y][x]);
                        } else if (map[y][x].getP() == Const.P1) {
                            this.map[y][x].setP(Const.P2);
                        }
                    }
                }
            }
            infectedQueue.get(i).setCycles(infectedQueue.get(i).getCycles() + 1);
            if (infectedQueue.get(i).getCycles() > Const.V) {
                infectedQueue.get(i).setType('r');
                infectedQueue.remove(i);
                i = i - 1;
            }
        }

        for (int i = 0; i < pStack.size(); i ++) {
            Neighborhood currentHood = pStack.pop();
            if (Math.random() <= currentHood.getP()) {
                if (currentHood.getType() != 'v') {
                    this.emptyHoods = this.emptyHoods - 1;
                }
                currentHood.setType('i');
                currentHood.setCycles(0);
                infectedQueue.add(currentHood);
                vaccineQueue.remove(currentHood);
            } else {
                currentHood.setP(0);
            }
            i = i - 1;
        }

        for (int i = 0; i < vaccineQueue.size(); i ++) {
            vaccineQueue.get(i).setCycles(vaccineQueue.get(i).getCycles() + 1);
            if (vaccineQueue.get(i).getCycles() > Const.V) {
                vaccineQueue.get(i).setType('r');
                vaccineQueue.remove(i);
                i = i - 1;
            }
        }

        if (this.cycles > Const.D) {
            int vaccinesAdministered = 0;
            while ((vaccinesAdministered < Const.A) && (vaccinesAdministered < this.emptyHoods)) {
                int x = (int) (Math.random() * (this.map[0].length));
                int y = (int) (Math.random() * (this.map.length));
                if (map[y][x].getType() == ' ') {
                    map[y][x].setType('v');
                    map[y][x].setCycles(0);
                    vaccinesAdministered = vaccinesAdministered + 1;
                    vaccineQueue.add(map[y][x]);
                    this.emptyHoods = this.emptyHoods - 1;
                }
            }
        }
        this.cycles ++;

    }

    private boolean validTile(int x, int y) {
        return ((x >= 0) && (x < this.map[0].length) && (y >= 0) && (y < this.map.length) && (map[y][x].getType() != 'r'));
    }


    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.map.length; i ++) {
            System.out.println(i);
            for (int j = 0; j < map[0].length; j ++) {
                str = str + map[i][j];
            }
            str = str + "\n";
        }
        return str;
    }

    public Neighborhood[][] getMap() {
        return map;
    }

    public int getInfectedQueueSize() {
        return infectedQueue.size();
    }
}
