package music;

public enum NoteName {
    C(0), D(1), E(2), F(3), G(4), A(5), B(6);
    private final int position;
    private NoteName(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
