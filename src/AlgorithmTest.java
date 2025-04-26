import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

public class AlgorithmTest {
    public static void main(String[] args) {
        // Create a queue of people from the file
        QueueLinkedList<Person> people = new QueueLinkedList<>();
        DecimalFormat numberFormat = new DecimalFormat("#0.00");

        try {
            File file = new File("People.txt");
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                double pArea = fileReader.nextFloat();
                double pWeight = fileReader.nextFloat();

                people.add(new Person(pArea, pWeight));
            }

            // Prints information about the dataset of people
            hline();
            System.out.println("Data Successfully Imported");
            bline();
            System.out.println("There are " + people.size() + " unique records in people");
            System.out.println("");
            System.out.println("Range of Standing Area (m^2) = (" + numberFormat.format(people.get(0).getArea()) + ", "
                    + numberFormat.format(people.get(99).getArea()) + ")");
            System.out.println("Maximum Elevator Area (m^2) = 2.25");
            System.out.println("");
            System.out.println("Range of Body Weight (Kg) = (" + numberFormat.format(people.get(0).getWeight()) + ", "
                    + numberFormat.format(people.get(99).getWeight()) + ")");
            System.out.println("Maximum Elevator Load (Kg) = 800");
            bline();
            System.out.println("");

            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Failed to find People.txt");
            e.printStackTrace();
        }

        // Create a queue of random people
        ElevatorQueue queueOfPeople = new ElevatorQueue();
        Random rand = new Random(727);

        // Randomly select 500,000 people from the list of 100 people
        // A large number of people is used see the difference in performance
        for (int i = 0; i < 500000; i++) {
            queueOfPeople.enqueue(people.get(rand.nextInt(100)));
        }

        // Create an array of all the algorithms to be tested
        String[] algorithms = new String[] { "Next Fit", "Best Fit", "Worst Fit", "First Fit" };

        // Fitting all the algorithms and printing their performance
        // NOTE: some algorithms may take a long time to run, so please wait patiently
        for (String algo : algorithms) {
            hline();
            System.out.println(algo + " Algorithm");
            bline();
            Algorithms a = new Algorithms(algo, queueOfPeople);
            a.getResult();
            // a.getBins();
            bline();
            System.out.println("");
        }
    }

    // Prints header line
    public static void hline() {
        System.out.println("===========================================");
    }

    // Prints body line
    public static void bline() {
        System.out.println("-------------------------------------------");
    }
}