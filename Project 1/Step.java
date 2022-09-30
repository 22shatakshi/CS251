public enum Step {
    UP("Up", "U"),
    DOWN("Down", "D"),
    LEFT("Left", "L"),
    RIGHT("Right", "R");

    private String value;
    private String letter;

    Step(String value, String letter) {
        this.value = value;
        this.letter = letter;
    }

    @Override
    public String toString() {
        return letter;
    }
}
