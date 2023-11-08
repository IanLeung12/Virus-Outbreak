import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to Virus Outbreak.");

        System.out.println("Please enter your desired side length of your map:");
        Scanner input = new Scanner(System.in);
        int length = input.nextInt();

        System.out.println("Please enter around how many ticks you want the program to run for (Recommended: 5x - 15x the length):");
        int targetTicks = input.nextInt();

        System.out.println("Do you want there to be a delay between ticks? (Y/N)");
        boolean delay = input.next().equals("Y");

        City city = new City(length, targetTicks);
        DisplayGrid visualiser = new DisplayGrid(city.getMap(), length, length);
        while (city.getInfectedQueueSize() > 0) {
            city.runCycle();
            visualiser.refresh();
            if (delay) {
                Thread.sleep((long) Math.ceil(1000.0/length));
            }
        }
    }
}