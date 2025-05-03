import java.text.DecimalFormat;

public class Algorithms {
    private String algoName;

    // All the elevator used will be stored in the elevators
    private ElevatorArrayList<ElevatorBin> elevators = new ElevatorArrayList<>();

    // Number of people that cannot fit into any elevator
    private int cannotFit = 0;

    // Specification of the elevators
    double maxLoad = 0;
    double maxArea = 0;

    // Number format of the result
    DecimalFormat numberFormat = new DecimalFormat("#0.00");

    // Constructor for specified algorithm
    public Algorithms(String algo, double maxLoad, double maxArea, ElevatorQueue people) {
        algoName = algo;
        this.maxLoad = maxLoad;
        this.maxArea = maxArea;

        switch (algo) {
            case "First Fit":
                firstFit(people);
                break;

            case "Best Fit":
                bestFit(people);
                break;

            case "Next Fit":
                nextFit(people);
                break;

            case "Worst Fit":
                worstFit(people);
                break;

            default:
                System.out.println("Invalid algorithm name: " + algo);
                System.out.println("Please use one of the following algorithms:");
                System.out.println("Next Fit, Best Fit, Worst Fit, First Fit");
                break;
        }
    }

    // Print out the number of elevators used by the algorithm
    public void getResult() {
        System.out.println(algoName);
        System.out.println("Elevators needed = " + elevators.size());

        if (cannotFit > 0) {
            System.out.println("Number of people that cannot fit into any elevator = " + cannotFit);
        }
    }

    // Print out the statistic of the algorithm
    public void getBins() {
        // Calculate area wasted by each elevator
        Double wastedArea = elevators.size() * 2.25;
        for (ElevatorBin elevator : elevators) {
            wastedArea -= elevator.getCurrentArea();
        }

        System.out.println("Area wasted (m^2) = " + numberFormat.format(wastedArea));

        // Calculate load wasted by each elevator
        Double wastedLoad = elevators.size() * 800.0;
        for (ElevatorBin elevator : elevators) {
            wastedLoad -= elevator.getCurrentLoad();
        }

        System.out.println("Load wasted (kg)  = " + numberFormat.format(wastedLoad));
        System.out.println("");

        // Print out the detail of each elevator in elevators
        int count = 1;

        for (ElevatorBin elevator : elevators) {
            System.out.println("Elevator " + count + " (Number of People= " + elevator.getSize() + ", Area Occupied= "
                    + numberFormat.format(elevator.getCurrentArea()) + ", Total Load= " +
                    numberFormat.format(elevator.getCurrentLoad()) + ")");

            System.out.println("Detail of Each Person");
            System.out.println("=====================");
            System.out.println("Area  Weight");
            System.out.println("---------------------");

            ElevatorArrayList<Person> bin = elevator.getBin();

            for (Person p : bin) {
                System.out.println(numberFormat.format(p.getArea()) + "  " + numberFormat.format(p.getWeight()));
            }

            System.out.println("---------------------");
            System.out.println("");
            count++;
        }
    }

    // First Fit Algorithm execution
    public void firstFit(ElevatorQueue people) {
        // Add the first elevator into elevators
        elevators.add(new ElevatorBin(maxLoad, maxArea));

        for (Person p : people) {
            // If the person is too big for the elevator, increment cannotFit and continue
            if (p.getArea() > maxArea || p.getWeight() > maxLoad) {
                cannotFit++;
                continue;
            }

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
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
            }
        }
    }

    // Best Fit Algorithm execution
    public void bestFit(ElevatorQueue people) {
        // Add the first elevator into elevators
        elevators.add(new ElevatorBin(maxLoad, maxArea));

        for (Person p : people) {
            // If the person is too big for the elevator, increment cannotFit and continue
            if (p.getArea() > maxArea || p.getWeight() > maxLoad) {
                cannotFit++;
                continue;
            }

            // The available elevators that can accept the person
            ElevatorArrayList<Integer> available = new ElevatorArrayList<>();

            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    available.add(i);
                }
            }

            // Open a new elevator to accept the person if no available elevator can
            if (available.isEmpty()) {
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
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

    // Next Fit Algorithm execution
    public void nextFit(ElevatorQueue people) {
        // Add the first elevator into elevators
        elevators.add(new ElevatorBin(maxLoad, maxArea));

        // Index of the currently used elevator
        int current = 0;

        for (Person p : people) {
            // If the person is too big for the elevator, increment cannotFit and continue
            if (p.getArea() > maxArea || p.getWeight() > maxLoad) {
                cannotFit++;
                continue;
            }

            // If the elevator can accept the person, add the person
            if (elevators.get(current).canHandle(p)) {
                elevators.get(current).push(p);
            }

            // Else open a new elevator to accept the person
            else {
                current++;
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
            }
        }
    }

    // Worst Fit Algorithm execution
    public void worstFit(ElevatorQueue people) {
        // Add the first elevator into elevators
        elevators.add(new ElevatorBin(maxLoad, maxArea));

        for (Person p : people) {
            // If the person is too big for the elevator, increment cannotFit and continue
            if (p.getArea() > maxArea || p.getWeight() > maxLoad) {
                cannotFit++;
                continue;
            }

            // The available elevators that can accept the person
            ElevatorArrayList<Integer> available = new ElevatorArrayList<>();

            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    available.add(i);
                }
            }

            // Open a new elevator to accept the person if no available elevator can
            if (available.isEmpty()) {
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
            }

            // Else put the person into the elevator with the most space remaining
            else {
                // initial index of available elevators
                int max = 0;

                // Search for index of available elevators with the most space remaining
                for (int i = 0; i < available.size(); i++) {
                    if (elevators.get(available.get(i)).getCurrentLoad() < elevators.get(available.get(max))
                            .getCurrentLoad()) {
                        max = i;
                    }
                }

                elevators.get(available.get(max)).push(p);
            }
        }
    }
}