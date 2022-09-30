public class PatientQueue {
    private Patient[] array;
    private int queueSize;

    // constructor: set variables
    // capacity = initial capacity of array
    public PatientQueue(int capacity) {
        this.array = new Patient[capacity];
        queueSize = 0;
    }

    // insert Patient p into queue
    // return the final index at which the patient is stored
    // return -1 if the patient could not be inserted
    public int insert(Patient p) {
        // TO BE COMPLETED
        if (queueSize != array.length) {
            p.setPosInQueue(queueSize);
            array[queueSize++] = p;
            buildHeap();
            return p.posInQueue();
        }
        return -1;

    }

    // remove and return the patient with the highest urgency level
    // if there are multiple patients with the same urgency level,
    // return the one who arrived first
    public Patient delMax() {
        // TO BE COMPLETED
        if (isEmpty()) {
            return null;
        }
        swap(0, queueSize - 1);
        Patient temp = array[queueSize - 1];
        temp.setPosInQueue(-1);
        array[--queueSize] = null;
        buildHeap();
        return temp;
    }

    // return but do not remove the first patient in the queue
    public Patient getMax() {
        if (queueSize == 0) {
            return null;
        }
        return array[0];
    }

    // return the number of patients currently in the queue
    public int size() {
        return queueSize;
    }

    // return true if the queue is empty; false else
    public boolean isEmpty() {
        if (queueSize == 0) {
            return true;
        }
        return false;
    }

    // used for testing underlying data structure
    public Patient[] getArray() {
        return array;
    }

    private void heapify(int root) {
        int largest = root; // Initialize largest as root
        int l = 2 * root + 1; // left = 2*i + 1
        int r = 2 * root + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < queueSize && array[l].compareTo(array[largest]) > 0)
            largest = l;

        // If right child is larger than largest so far
        if (r < queueSize && array[r].compareTo(array[largest]) > 0)
            largest = r;

        // If largest is not root
        if (largest != root) {
            swap(root, largest);
            heapify(largest);
        }
    }

    private void buildHeap() {
        // Index of last non-leaf node
        int startIdx = (queueSize / 2) - 1;

        // Perform reverse level order traversal
        // from last non-leaf node and heapify
        // each node
        for (int i = startIdx; i >= 0; i--) {
            heapify(i);
        }
    }

    private void swap(int i, int j) {
        array[i].setPosInQueue(j);
        Patient temp = array[i];
        array[j].setPosInQueue(i);
        array[i] = array[j];
        array[j] = temp;
    }
}
