public class StepStack {
    private Step[] stack;
    private int size;

    public StepStack() {
        this.size = 0;
        this.stack = new Step[1];
    }

    public void push(Step step) {
        if (size == stack.length) {
            Step[] newStack = new Step[this.stack.length * 2];
            for (int i = 0; i < this.size; i += 1) {
                newStack[i] = this.stack[i];
            }
            this.stack = newStack;
        }
        this.stack[size] = step;
        size++;
    }

    public Step peek() throws EmptyStackException {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.stack[this.size - 1];
    }

    public Step pop() throws EmptyStackException {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
        Step popped = this.stack[--size];
        if (this.size < this.stack.length / 4) {
            Step[] newStack = new Step[this.stack.length / 2];
            for (int i = 0; i < this.size; i += 1) {
                newStack[i] = this.stack[i];
            }
            this.stack = newStack;
        }
        return popped;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        String path = "";
        for (int i = 0; i < size; i++) {
            if (stack[i] != null) {
                {
                    path += stack[i];
                }
            }
        }
        return path;
    }
}