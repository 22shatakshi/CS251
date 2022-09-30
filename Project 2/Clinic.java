public class Clinic {
    private NewPatientQueue pq;
    private int er_threshold;
    private int capacity;
    private int processed = 0;
    private int seenByDoctor = 0;
    private int sentToER = 0;
    private int walkedOut = 0;

    public Clinic(int cap, int er_threshold) {
        // TO BE COMPLETED
        this.capacity = cap;
        this.er_threshold = er_threshold;
        pq = new NewPatientQueue(capacity);
    }

    public int er_threshold() {
        return this.er_threshold;
    }

    public int capacity() {
        return this.capacity;
    }

    /*
     * process a new patient:
     * if their urgency level is higher than the er_threshold,
     * send them directly to the emergency room and return null
     * otherwise, try to insert them into the queue
     * if the queue is full, compare their urgency to the highest
     * urgency currently in the queue; if their urgency is higher,
     * send them to the ER and return null; if the current max
     * is higher, send the max patient to the ER, insert
     * the new patient into the queue, and return the name
     * of the max patient
     */
    public String process(String name, int urgency) {
        // TO BE COMPLETED
        processed++;
        Patient newPatient = new Patient(name, urgency, 0);
        if (urgency > er_threshold) {
            sendToER(newPatient);
            return null;
        }
        if (pq.size() != capacity) {
            pq.insert(newPatient);
            return name;
        } else {
            if (urgency > pq.getMax().urgency()) {
                sendToER(newPatient);
                return null;
            }
            Patient temp = pq.delMax();
            sendToER(temp);
            pq.insert(newPatient);
            return pq.getMax().name();
        }

    }

    /*
     * a doctor is available--send the patient with
     * highest urgency to be seen; return the name
     * of the Patient or null if the queue is empty
     */
    public String seeNext() {
        // TO BE COMPLETED
        if (pq.isEmpty()) {
            return null;
        }
        Patient temp = pq.delMax();
        seeDoctor(temp);
        return temp.name();
    }

    /*
     * Patient experiences an emergency, raising their
     * urgency level; if the urgency level exceeds the
     * er_threshold, send them directly to the emergency room;
     * else update their urgency status in the queue;
     * return true if the Patient is removed from the queue
     * and false otherwise
     */
    public boolean handle_emergency(String name, int urgency) {
        // TO BE COMPLETED
        if (urgency > er_threshold) {
            Patient temp = pq.remove(name);
            temp.setUrgency(urgency);
            sendToER(temp);
            return true;
        } else {
            pq.update(name, urgency);
            return false;
        }

    }

    /*
     * Patient decides to walk out
     * remove them from the queue
     */
    public void walk_out(String name) {
        // TO BE COMPLETED
        pq.remove(name);
        walkedOut++;
    }

    /* Indicates that Patient p has been sent to the ER */
    private void sendToER(Patient p) {
        System.out.println("Patient " + p + " sent to ER.");
        sentToER++;
    }

    /* Indicates that a patient is being seen by a doctor */
    private void seeDoctor(Patient p) {
        System.out.println("Patient " + p + " is seeing a doctor.");
        seenByDoctor++;
    }

    public int processed() {
        return processed;
    }

    public int sentToER() {
        return sentToER;
    }

    public int seenByDoctor() {
        return seenByDoctor;
    }

    public int walkedOut() {
        return walkedOut;
    }
}