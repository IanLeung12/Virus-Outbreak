public class Neighborhood {
    private char type;
    private int cycles;
    private double P;
    private int x;
    private int y;
    public Neighborhood(char type, int x, int y) {
        this.type = type;
        this.cycles = 0;
        this.P = 0;
        this.x = x;
        this.y = y;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public double getP() {
        return P;
    }

    public void setP(double p) {
        P = p;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
