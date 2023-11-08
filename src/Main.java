// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int length = 100;
        int targetTicks = 300;
        Const.A =  (length * length);
        Const.D = targetTicks/4;
        Const.V = length / 30 + 30;
        City city = new City(length, length);
        DisplayGrid visualiser = new DisplayGrid(city.getMap(), length, length);
        while (city.getInfectedQueueSize() > 0) {
            city.runCycle();
            visualiser.refresh();
            Thread.sleep((long) Math.ceil(500.0/length));
        }
    }
}