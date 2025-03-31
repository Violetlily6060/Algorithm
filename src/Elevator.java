public class Elevator {
    // Elevator is used as the bin for the bin packing problem
    private MyArrayList<Person> bin = new MyArrayList<>();
    public static final double FULL_LOAD = 900;
    public static final double FULL_AREA = 2.25;
    private double currentLoad = 0;
    private double currentArea = 0;

    // Create a default elevator
    public Elevator() {
    }

    // return number of people in the elevator
    public int getSize() {
        return bin.size();
    }

    // return the current load of people in the elevator
    public double getCurrentLoad() {
        return currentLoad;
    }

    // return the current occupied area of the elevator
    public double getCurrentArea() {
        return currentArea;
    }

    // return the last person to enter the elevator
    public Person peek() {
        return bin.get(getSize() - 1);
    }

    // add a new person to enter the elevator
    public void push(Person person) {
        if ((currentLoad + person.getWeight()) > FULL_LOAD) {
            System.out.println("Elevator Load Limit Exceeded");
            return;
        }

        if ((currentArea + person.getArea()) > FULL_AREA) {
            System.out.println("Elevator Area Limit Exceeded");
            return;
        }

        bin.add(person);
        currentLoad += person.getWeight();
        currentArea += person.getArea();
    }

    // return and remove the last person to enter the elevator
    public Person pop() {
        Person person = bin.get(getSize() - 1);
        bin.remove(getSize() - 1);
        currentLoad -= person.getWeight();
        currentArea -= person.getArea();
        return person;
    }

    // checking if the elevator is empty
    public boolean isEmpty() {
        return bin.isEmpty();
    }

    // printing out the person in the elevator
    @Override
    public String toString() {
        return "Elevator: " + bin.toString();
    }
}
