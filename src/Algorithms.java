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

            case "Worst Fit":
                bestFit(people);
                break;
            
            case "First Fit":
                firstFit(people);
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
                elevators.add(new Elevator(p));
            }
        }
    }

    // Best Fit Algorithm execution
    public void bestFit(MyArrayList<Person> people) {
        // Add the first elevator into elevators
        elevators.add(new Elevator());

        for (Person p : people) {
            // The available elevators that can accept the person
            MyArrayList<Integer> available = new MyArrayList<>();

            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    available.add(i);
                }
            }

            // Open a new elevator to accept the person if no available elevator can
            if (available.isEmpty()) {
                elevators.add(new Elevator(p));
            }

            // Else put the person into the elevator with the least space remaining
            else {
                // initial index of available elevators
                int min = 0;

                // Search for index of available elevators with the least space remaining
                for (int i = 0; i < available.size(); i++) {
                    if (elevators.get(available.get(i)).getCurrentLoad() > elevators.get(available.get(min))
                            .getCurrentLoad()) {
                        min = i;
                    }
                }

                elevators.get(available.get(min)).push(p);
            }
        }
    }
 
 
    // Worst Fit Algorithm execution
    public void worstFit(MyArrayList<Person> people) {
        // Add the first elevator into elevators
        elevators.add(new Elevator());

        for (Person person : people) {
            // The available elevators that can accept the person
            MyArrayList<Integer> available = new MyArrayList<>();

            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(person)) {
                    available.add(i);
                }
            }

            // Open a new elevator to accept the person if no available elevator can
            if (available.isEmpty()) {
                elevators.add(new Elevator(person));
            }

            // Else put the person into the elevator with the most space remaining
            else {
                // initial index of available elevators
                int max = 0;

                // Search for index of available elevators with the most space remaining
                for (int i = 0; i < available.size(); i++) {
                    if (elevators.get(available.get(i)).getCurrentLoad() < elevators.get(available.get(max)).getCurrentLoad()) {
                        max = i;
                    }
                }

                elevators.get(available.get(max)).push(person);
            }
        }
    }
    // First Fit Algorithm execution
public void firstFit(MyArrayList<Person> people) {
    // Add the first elevator into elevators
    elevators.add(new Elevator());

    for (Person p : people) {
        boolean placed = false;

        // Try placing the person into the first elevator that can handle them
        for (int i = 0; i < elevators.size(); i++) {
            if (elevators.get(i).canHandle(p)) {
                elevators.get(i).push(p);
                placed = true;
                break;
            }
        }

        // If no available elevator could handle the person, open a new one
        if (!placed) {
            elevators.add(new Elevator(p));
        }
    }
}

}   