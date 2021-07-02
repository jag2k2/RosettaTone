package music;

import instrument.Key;
import utility.Decrementable;
import utility.Incrementable;

import java.util.List;
import java.util.ArrayList;

public class Note implements Comparable<Note>, Incrementable, Decrementable {

    private NoteName noteName;
    private int octave;
    private boolean natural = false;
    private boolean sharp = false;
    private boolean flat = false;

    public Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
    }

    public Note(Key key){
        this.noteName = NoteName.values()[key.getNaturalIndex()];
        this.octave = key.getOctave();
    }

    public Note(Note note){
        this.noteName = note.noteName;
        this.octave = note.octave;
    }

    public int getOctave() {
        return octave;
    }

    public NoteName getNoteName(){
        return noteName;
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
        int otherNotePosition = otherNote.noteName.getPosition();
        int positionDifference = Math.abs(notePosition - otherNotePosition);
        int octaveDifference = Math.abs(octave - otherNote.octave);
        boolean sameOctaveAdjacent = (positionDifference == 1)  && (octaveDifference == 0);
        boolean noteBCAdjacent = (positionDifference == 6) && (octaveDifference == 1);
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
                    this.flat == noteCompare.flat;
        }
        return false;
    }

    @Override
    public String toString() {
        String noteString = noteName.toString();
        if (natural && (sharp || flat)){
            noteString += "nat";
        }
        if (sharp) {
            noteString += "#";
        }
        if (flat) {
            noteString += "b";
        }
        noteString += Integer.toString(octave);
        return noteString;
    }

    @Override
    public void increment() {
        if (noteName.getPosition() >= NoteName.B.getPosition()){
            octave++;
            noteName = NoteName.C;
        }
        else {
            noteName = NoteName.values()[noteName.getPosition() + 1];
        }
    }

    @Override
    public void decrement() {
        if (noteName.getPosition() <= NoteName.C.getPosition()){
            octave--;
            noteName = NoteName.B;
        }
        else {
            noteName = NoteName.values()[noteName.getPosition() - 1];
        }
    }

    @Override
    public int compareTo(Note o) {
        return noteValue() - o.noteValue();
    }

    protected int noteValue(){
        return octave * 10 + noteName.getPosition();
    }
}
