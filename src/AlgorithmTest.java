import java.util.Random;
import java.util.Scanner;

public class AlgorithmTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Create a queue of people from the file
        QueueLinkedList<Person> people = new QueueLinkedList<>();

        // Load the unique people from People.txt
        people.load("People.txt");

        // Get the number of people from user
        System.out.println("How many people would you like to pack fit into the elevators?");
        System.out.print("Amount: ");

        int numOfPeople = 0;
        while (true) {
            if (input.hasNextInt()) {
                numOfPeople = input.nextInt();
                if (numOfPeople > 0) {
                    System.out.println("");
                    if (numOfPeople >= 200000) {
                        System.out.println("You have entered a large number");
                        System.out.println("The algorithms may take a long time to run, please wait patiently\n");
                    }
                    break;
                } else {
                    System.out.print("Please enter a number larger than 0: ");
                }
            } else {
                System.out.print("Please enter a valid number: ");
                input.next();
            }
        }

        // Create a queue of random people with the size of numOfPeople
        ElevatorQueue queueOfPeople = new ElevatorQueue();
        Random rand = new Random(727);
        for (int i = 0; i < numOfPeople; i++) {
            queueOfPeople.enqueue(people.get(rand.nextInt(100)));
        }

        // Get the algorithm to be tested from user
        System.out.println("Please select the algorithm to be tested");
        System.out.println(
                "1. Next Fit\n2. Best Fit\n3. Worst Fit\n4. First Fit\n5. All Algorithms");
        System.out.print("Algorithm (1-5): ");

        int algoChoice = 0;
        while (true) {
            if (input.hasNextInt()) {
                algoChoice = input.nextInt();
                if (algoChoice >= 1 && algoChoice <= 5) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Please enter a number between 1 and 5: ");
                }
            } else {
                System.out.print("Please enter a valid number: ");
                input.next();
            }
        }

        // Executing the selected algorithm
        Algorithms algoSelected = null;

        switch (algoChoice) {
            case 1:
                algoSelected = new Algorithms("Next Fit", queueOfPeople);
                algoSelected.getResult();
                break;

            case 2:
                algoSelected = new Algorithms("Best Fit", queueOfPeople);
                algoSelected.getResult();
                break;

            case 3:
                algoSelected = new Algorithms("Worst Fit", queueOfPeople);
                algoSelected.getResult();
                break;

            case 4:
                algoSelected = new Algorithms("First Fit", queueOfPeople);
                algoSelected.getResult();
                break;

            case 5:
                for (String algo : new String[] { "Next Fit", "Best Fit", "Worst Fit", "First Fit" }) {
                    Algorithms algorithm = new Algorithms(algo, queueOfPeople);
                    algorithm.getResult();
                }
        }

        // Ask if the user wants to see the details of each elevator
        if (algoChoice != 5) {
            System.out.println("Would you like to see the details of each elevator bin?");
            System.out.println("Note: Not recommended if large number of elevators is used");
            System.out.print("(y/n): ");

            String choice = "";
            while (true) {
                choice = input.next().toLowerCase();
                if (choice.equals("y") || choice.equals("n")) {
                    System.out.println("");
                    break;
                } else {
                    System.out.print("Please enter y or n: ");
                }
            }

            if (choice.equals("y")) {
                algoSelected.getBins();
            }
        }

        System.out.println("Thank you for using the Elevator Packing Algorithm Tester!");
        input.close();
    }
}