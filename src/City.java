/**
 * [City.java]
 * This class represents a infected city of many neighborhoods
 * @author Ian Leung
 * @version 1.0 November 8, 2023
 */

import java.util.ArrayList;
import java.util.Stack;

public class City {
    private Neighborhood[][] map;
    private ArrayList<Neighborhood> infectedList;
    private ArrayList<Neighborhood> vaccineList;
    private int cycles;
    private int unaffectedNeighborhoods;

    public City(int length, int targetTicks) {
        this.map = new Neighborhood[length][length];
        this.cycles = 0;
        this.unaffectedNeighborhoods = length * length - 9;

        // Initial size based on length to ensure arraylists are efficient
        this.infectedList = new ArrayList<Neighborhood>(length * 3);
        this.vaccineList = new ArrayList<Neighborhood>(length * 3);
        this.setConstValues(length, targetTicks);

        for (int i = 0; i < this.map.length; i ++) {
            for (int j = 0; j < this.map[0].length; j ++) {
                this.map[i][j] = new Neighborhood(' ', j, i);
            }
        }

        //infects a 3x3 area at the center of the map (1x1 is too small)
        int x = length/2 - 1;
        int y = length/2 - 1;
        for (int i = y; i <= y + 2; i ++) {
            for (int j = x; j <= x + 2; j ++){
                map[i][j].setType('i');
                infectedList.add(map[i][j]);
            }
        }

    }

    /**
     * setConstValues
     * Changes P1, P2, A, D and V based on target ticks and map size
     * @param length map length
     * @param targetTicks target ticks
     */
    private void setConstValues(int length, int targetTicks) {
        Const.P1 = Math.max((0.004 * (-length/800.0 + 2) * (2000.0/targetTicks + 1)), 0.004);
        Const.P2 = Math.max((0.008 * (-length/600.0 + 2.4) * (1100.0/targetTicks + 1.1)), 0.009);
        Const.A = (int) ((length * length * 0.5)/targetTicks) + 1;
        Const.D = (targetTicks/4);
        Const.V = Math.min((targetTicks/130 + 30), targetTicks/2);
        System.out.println(Const.P1 + " " + Const.P2 + " " + Const.V);
    }

    public void runCycle() {
        Stack<Neighborhood> pStack = new Stack<Neighborhood>();
        for (int i = 0; i < infectedList.size(); i ++) {
            for (int y = infectedList.get(i).getY() - 1; y <= infectedList.get(i).getY() + 1; y++) { //3x3 around infected neighborhood
                for (int x = infectedList.get(i).getX() - 1; x <= infectedList.get(i).getX() + 1; x ++) {

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

            // Counts how long neighborhood has been infected
            infectedList.get(i).setCycles(infectedList.get(i).getCycles() + 1);
            if (infectedList.get(i).getCycles() > Const.V) {
                infectedList.get(i).setType('r');
                infectedList.remove(i);
                i = i - 1;
            }
        }

        // Stack handles at-risk neighborhoods
        for (int i = 0; i < pStack.size(); i ++) {
            Neighborhood currentNeighborhood = pStack.pop();
            if (Math.random() <= currentNeighborhood.getP()) {
                if (currentNeighborhood.getType() != 'v') {
                    this.unaffectedNeighborhoods = this.unaffectedNeighborhoods - 1;
                }
                currentNeighborhood.setType('i');
                currentNeighborhood.setCycles(0);
                infectedList.add(currentNeighborhood);
                vaccineList.remove(currentNeighborhood);
            } else {
                currentNeighborhood.setP(0);
            }
            i = i - 1;
        }

        for (int i = 0; i < vaccineList.size(); i ++) {
            vaccineList.get(i).setCycles(vaccineList.get(i).getCycles() + 1);
            if (vaccineList.get(i).getCycles() > Const.V) {
                vaccineList.get(i).setType('r');
                vaccineList.remove(i);
                i = i - 1;
            }
        }

        if (this.cycles > Const.D) { // Vaccination starts after D cycles
            int vaccinesAdministered = 0;

            // Picks A random neighborhoods, up to the amount of unvaccinated neighborhoods remaining
            while ((vaccinesAdministered < Const.A) && (vaccinesAdministered < this.unaffectedNeighborhoods)) {
                int x = (int) (Math.random() * (this.map[0].length));
                int y = (int) (Math.random() * (this.map.length));
                if (map[y][x].getType() == ' ') {
                    map[y][x].setType('v');
                    map[y][x].setCycles(0);
                    vaccinesAdministered = vaccinesAdministered + 1;
                    vaccineList.add(map[y][x]);
                    this.unaffectedNeighborhoods = this.unaffectedNeighborhoods - 1;
                }
            }
        }
        this.cycles = this.cycles + 1;

    }

    // Checks if tile is able to be infected
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

    public int getInfectedListSize() {
        return infectedList.size();
    }
}
