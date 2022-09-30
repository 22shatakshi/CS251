public class StepQueue {

    private int size;
    private Node head;
    private Node tail;

    public StepQueue() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Step step) {
        Node newNode = new Node();
        newNode.step = step;
        newNode.next = null;
        if (isEmpty()) {
            tail = newNode;
            head = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
        size += 1;
    }

    public Step dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        Step step = head.step;
        head = head.next;
        size -= 1;
        return step;
    }

    /**
     * If the linked list is: Up->Down->Left->Right,
     * toString should return "UDLR"
     */
    @Override
    public String toString() {
        String linklist = new String();
        Node temp = head;
        while (temp != null) {
            linklist += temp.step.toString();
            temp = temp.next;
        }
        return linklist;
    }

    private static class Node {
        Step step;
        Node next;
    }
}
