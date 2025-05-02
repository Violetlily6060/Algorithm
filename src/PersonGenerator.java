import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

// IMPORTANT: this is only used to generate the data for the people.txt file and is not part of the main program
public class PersonGenerator {
    public static void main(String[] args) {
        Random rand = new Random(727);
        double area = 0;
        double weight = 0;
        ArrayList<Person> people = new ArrayList<>();

        DecimalFormat numberFormat = new DecimalFormat("#0.00");

        try (FileWriter writer = new FileWriter("People.txt")) {
            for (int i = 0; i < 100000; i++) {
                area = rand.nextDouble(0.11) + 0.15;
                weight = rand.nextDouble(101) + 30;

                if (people.contains(new Person(area, weight))) {
                    i--;
                    continue;
                }

                people.add(new Person(area, weight));
                writer.write("\n" + numberFormat.format(area) + " " + numberFormat.format(weight));
            }

            System.out.println("100000 records of unique person has been created successfully");

            writer.close();

        } catch (IOException e) {
            System.out.println("Failed to generate people: " + e.getMessage());
        }
    }
}