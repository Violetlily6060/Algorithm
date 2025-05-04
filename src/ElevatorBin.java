public class ElevatorBin {
    // Elevator is used as the bin for the bin packing problem
    private ElevatorArrayList<Person> bin = new ElevatorArrayList<>();
    private double fullLoad = 0;
    private double fullArea = 0;
    private double currentLoad = 0;
    private double currentArea = 0;

    // Create a default elevator
    public ElevatorBin() {
        // Default values of the elevator
        fullLoad = 800;
        fullArea = 2;
    }

    public ElevatorBin(double load, double area) {
        // Create a elevator with the given load and area
        fullLoad = load;
        fullArea = area;
    }

    // Create a elevator with person, p inside
    public ElevatorBin(double load, double area, Person p) {
        fullLoad = load;
        fullArea = area;
        push(p);
    }

    // Return the bin of the elevator
    public ElevatorArrayList<Person> getBin() {
        return bin;
    }

    // Return number of people in the elevator
    public int getSize() {
        return bin.size();
    }

    // Return the current load of people in the elevator
    public double getCurrentLoad() {
        return currentLoad;
    }

    // Return the current occupied area of the elevator
    public double getCurrentArea() {
        return currentArea;
    }

    // Return the last person to enter the elevator
    public Person peek() {
        return bin.get(getSize() - 1);
    }

    // Add a new person to enter the elevator
    public void push(Person p) {
        if ((currentLoad + p.getWeight()) > fullLoad) {
            System.out.println("Elevator Load Limit Exceeded");
            return;
        }

        if ((currentArea + p.getArea()) > fullArea) {
            System.out.println("Elevator Area Limit Exceeded");
            return;
        }

        bin.add(p);
        currentLoad += p.getWeight();
        currentArea += p.getArea();
    }

    // Return and remove the last person to enter the elevator
    public Person pop() {
        try {
            Person person = bin.get(getSize() - 1);
            bin.remove(getSize() - 1);
            currentLoad -= person.getWeight();
            currentArea -= person.getArea();
            return person;
        } catch (Exception e) {
            System.out.println("The Elevator is Empty");
            return null;
        }
    }

    // Checking if the elevator is empty
    public boolean isEmpty() {
        return bin.isEmpty();
    }

    // Checking if the elevator can accept the person
    public boolean canHandle(Person p) {
        return ((currentArea + p.getArea()) < fullArea) && ((currentLoad + p.getWeight()) < fullLoad);
    }

    // Printing out the person in the elevator
    @Override
    public String toString() {
        return "Elevator: " + bin.toString();
    }
}
