/**
 * [Main.java]
 * This program simulates a virus outbreak
 * @author Ian Leung
 * @version 1.0 November 8, 2023
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("_______________________________________________________________________________");
        System.out.println("Welcome to Virus Outbreak.\n");

        System.out.println("Please enter your desired side length of your map (Recommended: 250 to 1080):");
        Scanner input = new Scanner(System.in);
        int length = input.nextInt();

        System.out.println("Please enter around how many ticks you want the program to run for (Recommended: 5x - 15x the length):");
        int targetTicks = input.nextInt();

        System.out.println("Do you want there to be a delay between ticks for accessibility? (Y/N)");
        boolean delay = input.next().equals("Y");
        input.close();

        System.out.println("\n_______________________________________________________________________________");

        City city = new City(length, targetTicks);
        DisplayGrid visualiser = new DisplayGrid(city.getMap(), length, targetTicks);
        while (city.getInfectedListSize() > 0) {
            city.runCycle();
            visualiser.refresh(city.getInfectedListSize());
            if (delay) {
                Thread.sleep((long) Math.ceil(1000.0/length));
            }
        }
    }
}