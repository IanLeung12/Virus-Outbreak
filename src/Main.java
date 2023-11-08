// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int length = 1000;
        int targetTicks = 15000;
        Const.P1 = 0.005 * (-length/800.0 + 3);
        Const.P2 = 0.0010 * (-length/900.0 + 3);
        System.out.println(Const.P1 + " " + Const.P2);
        Const.A = (int) ((length * length * 0.25)/targetTicks) + 1;
        Const.D = targetTicks/3;
        Const.V = Math.min(length / 32 + 30, targetTicks);
        City city = new City(length, length);
        DisplayGrid visualiser = new DisplayGrid(city.getMap(), length, length);
        while (city.getInfectedQueueSize() > 0) {
            city.runCycle();
            visualiser.refresh();
        }
    }
}