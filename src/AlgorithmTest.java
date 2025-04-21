import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

public class AlgorithmTest {
    public static void main(String[] args) {
        MyArrayList<Person> people = new MyArrayList<>();
        DecimalFormat numberFormat = new DecimalFormat("#0.00");

        try {
            File file = new File("People.txt");
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                double pArea = fileReader.nextFloat();
                double pWeight = fileReader.nextFloat();

                people.add(new Person(pArea, pWeight));
            }

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

        MyArrayList<Person> test = new MyArrayList<>();
        Random rand = new Random(42);

        for (int i = 0; i < 100; i++) {
            test.add(people.get(rand.nextInt(100)));
        }

        String[] algorithms = new String[] { "Next Fit", "Best Fit", "Worst Fit" };

        for (String algo : algorithms) {
            hline();
            System.out.println(algo + " Algorithm");
            bline();
            Algorithms a = new Algorithms(algo, test);
            a.getResult();
            a.getBins();
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