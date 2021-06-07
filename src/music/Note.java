package music;

public class Note {

    private final NoteName noteName;
    private final NoteAccidental noteAccidental;
    private final int key;
    private final int octave;

    public Note(int key) {
        this.key = key;
        this.octave = (key / 12)-1;

        NoteName[] noteNames = NoteName.values();
        int octaveIndex = key % 12;
        if (octaveIndex < 5){
            noteName = noteNames[octaveIndex / 2];
            if ((octaveIndex % 2) == 0){
                noteAccidental = NoteAccidental.Natural;
            }
            else{
                noteAccidental = NoteAccidental.SHARP;
            }
        }
        else{
            noteName = noteNames[(1 + octaveIndex) / 2];
            if ((octaveIndex % 2) == 0){
                noteAccidental = NoteAccidental.SHARP;
            }
            else{
                noteAccidental = NoteAccidental.Natural;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Note && this.key == ((Note) obj).key;
    }

    @Override
    public String toString() {
        return "Note: " + this.noteName + this.noteAccidental + this.octave + " key=" + this.key;
    }
}
