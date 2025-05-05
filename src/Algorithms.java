import java.text.DecimalFormat;

public class Algorithms {
    private String algoName;

    // List of elevators used
    private ElevatorArrayList<ElevatorBin> elevators = new ElevatorArrayList<>();

    // Number of people who cannot fit in elevator
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
        System.out.println("Elevators used = " + elevators.size());

        if (cannotFit > 0) {
            System.out.println("Number of people that cannot fit into any elevator = " + cannotFit);
        }
    }

    // Print out the statistic of the algorithm
    public void getBins() {

        // Calculate area wasted by each elevator
        Double wastedArea = elevators.size() * maxArea;
        for (ElevatorBin elevator : elevators) {
            wastedArea -= elevator.getCurrentArea();
        }
        System.out.println("Area wasted (m^2) = " + numberFormat.format(wastedArea));

        // Calculate load wasted by each elevator
        Double wastedLoad = elevators.size() * maxLoad;
        for (ElevatorBin elevator : elevators) {
            wastedLoad -= elevator.getCurrentLoad();
        }
        System.out.println("Load wasted (kg)  = " + numberFormat.format(wastedLoad) + "\n");

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

    // First Fit Algorithm Execution
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

            // Index of elevator with lowest area left
            int min = -1;

            // Search for available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    if (min == -1) {
                        min = i;
                    }

                    // Index of elevator with lower area left
                    else if (elevators.get(i).getCurrentArea() > elevators.get(min)
                    .getCurrentArea()) {
                        min = i;
                    }
                }
            }

            // Add a new elevator if there is no available elevators
            if (min == -1) {
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
            }

            // Insert person into elevator with lowest area left
            else {
                elevators.get(min).push(p);
            }
        }
    }

    // Next Fit Algorithm execution
    public void nextFit(ElevatorQueue people) {
        // Add the first elevator into elevators
        elevators.add(new ElevatorBin(maxLoad, maxArea));

        // Index of current elevator
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

            // Index of elevator with highest area left
            int max = -1;
            
            // Search for all available elevators
            for (int i = 0; i < elevators.size(); i++) {
                if (elevators.get(i).canHandle(p)) {
                    if (max == -1) {
                        max = i;
                    }

                    // Index of elevator with higher area left
                    else if (elevators.get(i).getCurrentArea() < elevators.get(max)
                    .getCurrentArea()) {
                        max = i;
                    }
                }
            }

            // Add a new elevator if there is no available elevator
            if (max == -1) {
                elevators.add(new ElevatorBin(maxLoad, maxArea, p));
            }

            // Insert person into elevator with highest area left
            else {
                elevators.get(max).push(p);
            }
        }
    }
}