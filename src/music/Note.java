package music;

import java.util.List;
import java.util.ArrayList;

public class Note {

    private final NoteClef noteClef;
    private final NoteName noteName;
    private int octave;
    private boolean natural = false;
    private boolean sharp = false;
    private boolean flat = false;

    public Note(NoteName noteName, int octave, NoteClef noteClef) {
        this.noteName = noteName;
        this.octave = octave;
        this.noteClef = noteClef;
    }

    public NoteName getName(){
        return noteName;
    }

    public int getOctave() {
        return octave;
    }

    public boolean isClef(NoteClef compareClef){
        return noteClef == compareClef;
    }

    public int getLineNumber() {
        return (noteName.getPosition() + (octave * 7) - 5);
    }

    public List<NoteAccidental> getActiveAccidentals() {
        List<NoteAccidental> accidentals = new ArrayList<>();
        if (natural){
            accidentals.add(NoteAccidental.NATURAL);
        }
        if (sharp){
            accidentals.add(NoteAccidental.SHARP);
        }
        if (flat){
            accidentals.add(NoteAccidental.FLAT);
        }
        return accidentals;
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

    public boolean isAdjacent(Note otherNote) {
        int notePosition = noteName.getPosition();
        int otherNotePosition = otherNote.getName().getPosition();
        int positionDifference = Math.abs(notePosition - otherNotePosition);
        int octaveDifference = Math.abs(octave - otherNote.octave);
        boolean sameOctaveAdjacent = (positionDifference == 1)  && (octaveDifference == 0) && (noteClef == otherNote.noteClef);
        boolean noteBCAdjacent = (positionDifference == 6) && (octaveDifference == 1) && (noteClef == otherNote.noteClef);
        return sameOctaveAdjacent || noteBCAdjacent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note){
            Note noteCompare = (Note) obj;
            return this.noteName == noteCompare.noteName &&
                    this.octave == noteCompare.octave &&
                    this.natural == noteCompare.natural &&
                    this.sharp == noteCompare.sharp &&
                    this.flat == noteCompare.flat &&
                    this.noteClef == noteCompare.noteClef;
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
