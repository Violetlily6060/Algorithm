import java.lang.Comparable;

public class Person implements Comparable<Person> {
    // Person is the objects to be packed into the bin(elevator)
    private double area;
    private double weight;

    // Create a default person
    public Person() {
        area = 0;
        weight = 0;
    }

    // Create a person with input area and weight
    public Person(double area, double weight) {
        this.area = area;
        this.weight = weight;
    }

    // return the area occupied by the person
    public double getArea() {
        return area;
    }

    // return the weight of the person
    public double getWeight() {
        return weight;
    }

    // comparison between two person
    @Override
    public int compareTo(Person P) {
        return (int) this.area - (int) P.area;
    }
}