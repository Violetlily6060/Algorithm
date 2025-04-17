import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class PersonGenerator {
    public static void main(String[] args) {
        double area = 0.15;
        double weight = 30.00;

        DecimalFormat numberFormat = new DecimalFormat("#0.00");

        try (FileWriter writer = new FileWriter("People.txt")) {
            writer.write(numberFormat.format(area) + " " + numberFormat.format(weight));

            for (int i = 0; i < 99; i++) {
                area += 0.001;
                weight += 0.8;
                writer.write("\n" + numberFormat.format(area) + " " + numberFormat.format(weight));
            }

            System.out.println("100 records of unique person has been created successfully");

            writer.close();

        } catch (IOException e) {
            System.out.println("Failed to generate people: " + e.getMessage());
        }
    }
}