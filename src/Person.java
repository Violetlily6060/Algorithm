import java.lang.Comparable;

public class Person implements Comparable<Person> {
    private int area;
    private int weight;

    public Person() {
        area = 0;
        weight = 0;
    }

    public Person(int area, int weight) {
        this.area = area;
        this.weight = weight;
    }

    public int getArea() {
        return area;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Person P) {
        return this.area - P.area;
    }
}