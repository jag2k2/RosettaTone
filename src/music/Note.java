package music;

public class Note {

    private final NoteName noteName;
    private int octave;
    boolean natural = false;
    boolean sharp = false;
    boolean flat = false;

    public Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
    }

    public void setAccidental(NoteAccidental accidental, boolean activated){
        if(accidental == NoteAccidental.NATURAL){
            natural = activated;
        }
        if(accidental == NoteAccidental.SHARP){
            sharp = activated;
        }
        if(accidental == NoteAccidental.FLAT){
            flat = activated;
        }
    }

    public boolean isActive(){
        return (natural || flat || sharp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note){
            Note noteCompare = (Note) obj;
            return this.noteName == noteCompare.noteName &&
                    this.octave == noteCompare.octave &&
                    this.natural == noteCompare.natural &&
                    this.sharp == noteCompare.sharp &&
                    this.flat == noteCompare.flat;
        }
        return false;
    }

    @Override
    public String toString() {
        String noteString = "";
        if (natural){
            noteString += noteName.toString() + octave;
        }
        if (sharp) {
            noteString += noteName.toString() + "#" + octave;
        }
        if (flat) {
            noteString += noteName.toString() + "b" + octave;
        }
        return noteString;
    }
}
