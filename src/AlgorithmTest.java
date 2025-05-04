import java.util.Scanner;

public class AlgorithmTest {
    public static void main(String[] args) {
        long startTime = 0;
        Scanner input = new Scanner(System.in);

        // Loop until the user decides to exit
        while (true) {

            // Clear console
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Create a queue of people from the file
            ElevatorQueue people = new ElevatorQueue();

            // Load the unique people from People.txt
            people.load("People.txt");
            System.out.println("There is " + people.getSize() + " unique records in the queue of people.\n");

            // Get the specification of the elevator from user
            System.out.println("Please enter the specifications of the elevator");

            // Input for elevator capacity
            System.out.print("Max Elevator Load (kg): ");
            double elevatorCapacity = 0;
            while (true) {
                try {
                    elevatorCapacity = Double.parseDouble(input.nextLine());
                    if (elevatorCapacity > 0) {
                        break;
                    } else {
                        System.out.print("Please enter a positive number: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            // Input for elevator area
            System.out.print("Max Elevator Area (m^2): ");
            double elevatorArea = 0;
            while (true) {
                try {
                    elevatorArea = Double.parseDouble(input.nextLine());
                    if (elevatorArea > 0) {
                        System.out.println("");
                        break;
                    } else {
                        System.out.print("Please enter a positive number: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            // Get the algorithm to be tested from user
            System.out.println("Please select the algorithm to be tested");
            System.out.println(
                    "1. First Fit\n2. Best Fit\n3. Next Fit\n4. Worst Fit\n5. All Algorithms");
            System.out.print("Algorithm (1-5): ");
            int algoChoice = 0;
            while (true) {
                try {
                    algoChoice = Integer.parseInt(input.nextLine());
                    if (algoChoice >= 1 && algoChoice <= 5) {
                        System.out.println("");
                        break;
                    } else {
                        System.out.print("Please enter a number between 1 and 5: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a valid number: ");
                }
            }

            // Executing the selected algorithm
            String[] algoSelected = null;
            switch (algoChoice) {
                case 1:
                    algoSelected = new String[] { "First Fit" };
                    break;

                case 2:
                    algoSelected = new String[] { "Best Fit" };
                    break;

                case 3:
                    algoSelected = new String[] { "Next Fit" };
                    break;

                case 4:
                    algoSelected = new String[] { "Worst Fit" };
                    break;

                case 5:
                    algoSelected = new String[] { "First Fit", "Best Fit", "Next Fit", "Worst Fit" };
                    break;
            }

            Algorithms singleAlgo = null;

            for (String algo : algoSelected) {
                startTime = System.currentTimeMillis();
                Algorithms algorithm = new Algorithms(algo, elevatorCapacity, elevatorArea, people);
                algorithm.getResult();
                printRuntime(startTime);

                singleAlgo = algorithm;
            }

            // Ask if the user wants to see the details of each elevator
            if (algoChoice != 5) {
                System.out.println("Would you like to see the details of each elevator bin?");
                System.out.println("Note: Not recommended if large number of elevators is used");
                System.out.print("Show bins (y/n): ");

                String choice = "";
                while (true) {
                    choice = input.nextLine().toLowerCase();
                    if (choice.equals("y") || choice.equals("n")) {
                        System.out.println("");
                        break;
                    } else {
                        System.out.print("Please enter y or n: ");
                    }
                }

                if (choice.equals("y")) {
                    singleAlgo.getBins();
                }
            }

            // Ask if the user wants to test another algorithm
            System.out.println("Would you like to test another algorithm?");
            System.out.print("Continue (y/n): ");

            String continueChoice = "";
            while (true) {
                continueChoice = input.nextLine().toLowerCase();
                if (continueChoice.equals("y") || continueChoice.equals("n")) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Please enter y or n: ");
                }
            }

            if (continueChoice.equals("n")) {
                break;
            }
        }

        // Exit the program
        System.out.println("Thank you for using the Elevator Packing Algorithm Tester!");
        input.close();
    }

    public static void printRuntime(long startTime) {
        System.out.println("Time taken (ms): " + (System.currentTimeMillis() - startTime) + "\n");
    }
}