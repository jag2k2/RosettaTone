package music;

public class Note {

    private final NoteName name;
    private final int key;
    private final int octave;
    private final int velocity;

    public Note(int key, int velocity) {
        this.key = key;
        this.octave = (key / 12)-1;
        int note = key % 12;
        NoteName[] noteNames = NoteName.values();
        this.name = noteNames[note];
        this.velocity = velocity;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Note && this.key == ((Note) obj).key;
    }

    @Override
    public String toString() {
        return "Note -> " + this.name + this.octave + " key=" + this.key + " velocity=" + this.velocity;
    }
}
