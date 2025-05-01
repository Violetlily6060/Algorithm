public class ElevatorQueue implements Iterable<Person> {
    private QueueLinkedList<Person> queue = new QueueLinkedList<>();

    // Create a default elevator queue
    public ElevatorQueue() {
    }

    // return the queue of the elevator
    public QueueLinkedList<Person> getQueue() {
        return queue;
    }

    // add a person to the queue
    public void enqueue(Person p) {
        queue.addLast(p);
    }

    // remove and return the first person in the queue
    public Person dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;
        }

        Person person = queue.getFirst();
        queue.removeFirst();

        return person;
    }

    // return number of people in the elevator queue
    public int getSize() {
        return queue.size();
    }

    // return true if the elevator queue is empty
    public boolean isEmpty() {
        return queue.size() == 0;
    }

    @Override
    public String toString() {
        return "Queue: " + queue.toString();
    }

    @Override
    public java.util.Iterator<Person> iterator() {
        return queue.iterator();
    }
}