import java.util.ArrayList;

public class PHashtable {
    private ArrayList<Patient>[] table;
    private int tableCapacity;
    private int size;

    // set the table size to the first
    // prime number p >= capacity
    public PHashtable(int capacity) {
        this.tableCapacity = getNextPrime(capacity);
        this.size = 0;
        this.table = new ArrayList[tableCapacity];
    }

    private int hashIndex(String name) {
        int hash = 0;
        hash = name.hashCode() % tableCapacity;
        if (hash < 0) {
            hash = hash + tableCapacity;
        }
        return hash;
    }

    // return the Patient with the given name
    // or null if the Patient is not in the table
    public Patient get(String name) {
        int index = hashIndex(name);
        if (table[index] != null) {
            for (int i = 0; i < table[index].size(); i++) {
                if (table[index].get(i).name().compareTo(name) == 0) {
                    return table[index].get(i);
                }
            }
        }
        return null;
    }

    // put Patient p into the table
    public void put(Patient p) {
        int index = hashIndex(p.name());
        if (table[index] == null) {
            table[index] = new ArrayList<>();
            table[index].add(p);
            size++;
        } else {
            int found = 0;
            for (int i = 0; i < table[index].size(); i++) {
                if (table[index].get(i).compareTo(p) == 0) {
                    found++;
                    return;
                }
            }
            if (found == 0) {
                table[index].add(p);
                size++;
            }
        }

    }

    // remove and returnthe Patient with the given name
    // from the table
    // return null if Patient doesn't exist
    public Patient remove(String name) {
        int index = hashIndex(name);
        if (table[index] == null) {
            return null;
        } else {
            for (int i = 0; i < table[index].size(); i++) {
                if (table[index].get(i).name().compareTo(name) == 0) {
                    Patient p = table[index].get(i);
                    table[index].remove(i);
                    size--;
                    return p;
                }
            }
        }
        return null;
    }

    // return the number of Patients in the table
    public int size() {
        return this.size;
    }

    // returns the underlying structure for testing
    public ArrayList<Patient>[] getArray() {
        return table;
    }

    // get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;
        int rem = num % 6;
        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }
        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }

    // determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;
        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
