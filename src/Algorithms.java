import java.text.DecimalFormat;

public class Algorithms {
    // All the elevator used will be stored in the elevators
    private MyArrayList<Elevator> elevators = new MyArrayList<>();

    // Number format of the result
    DecimalFormat numberFormat = new DecimalFormat("#0.00");

    // Constructor for specified algorithm
    public Algorithms(String algo, MyArrayList<Person> people) {
        switch (algo) {
            case "Next Fit":
                nextFit(people);
                break;

            case "Best Fit":
                bestFit(people);
                break;
        }
    }

    // Print out the statistic of the algorithm
    public void getResult() {
        System.out.println("Elevators needed  : " + elevators.size());

        // Calculate area wasted by each elevator
        Double wastedArea = elevators.size() * 2.25;
        for (Elevator elevator : elevators) {
            wastedArea -= elevator.getCurrentArea();
        }

        System.out.println("Area wasted (m^2) : " + numberFormat.format(wastedArea));

        // Calculate load wasted by each elevator
        Double wastedLoad = elevators.size() * 800.0;
        for (Elevator elevator : elevators) {
            wastedLoad -= elevator.getCurrentLoad();
        }

        System.out.println("Load wasted (Kg)  : " + numberFormat.format(wastedLoad));
        System.out.println("");
    }

    // Print out the detail of each elevator in elevators
    public void getBins() {
        int count = 1;

        for (Elevator elevator : elevators) {
            System.out.println("Elevator " + count + " (n=" + elevator.getSize() + ", Area="
                    + numberFormat.format(elevator.getCurrentArea()) + ", Load=" +
                    numberFormat.format(elevator.getCurrentLoad()) + ")");

            System.out.println("Area  Weight");

            MyArrayList<Person> bin = elevator.getBin();

            for (Person p : bin) {
                System.out.println(numberFormat.format(p.getArea()) + "  " + numberFormat.format(p.getWeight()));
            }

            System.out.println("");
            count++;
        }
    }

    // Next Fit Algorithm execution
    public void nextFit(MyArrayList<Person> people) {
        // Add the first elevator into elevators
        elevators.add(new Elevator());

        // Index of the currently used elevator
        int current = 0;

        for (Person p : people) {

            // If the elevator can accept the person, add the person
            if (elevators.get(current).canHandle(p)) {
                elevators.get(current).push(p);
            }

            // Else open a new elevator to accept the person
            else {
                current++;
                elevators.add(new Elevator());
                elevators.get(current).push(p);
            }
        }
    }

    // Best Fit Algorithm execution
    public void bestFit(MyArrayList<Person> people) {
        // Add the first elevator into elevators
        elevators.add(new Elevator());

        // The available elevators that can accept the person
        MyArrayList<Integer> available = new MyArrayList<>();

        for (Person p : people) {
            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    available.add(i);
                }
            }

            // Open a new elevator to accept the person if no available elevator can
            if (available.isEmpty()) {
                elevators.add(new Elevator());
                elevators.get(elevators.size() - 1).push(p);
            }
        }
    }
}