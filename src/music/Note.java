package music;

import java.util.Set;
import java.util.HashSet;

public class Note implements Comparable<Note> {

    private final NoteName noteName;
    private final int octave;
    private final HashSet<NoteAccidental> accidentals = new HashSet<>();

    public Note(NoteName noteName, int octave) {
        this.noteName = noteName;
        this.octave = octave;
        accidentals.add(NoteAccidental.NATURAL);
    }

    public Note(NoteName noteName, int octave, NoteAccidental noteAccidental) {
        this.noteName = noteName;
        this.octave = octave;
        accidentals.add(noteAccidental);

    }

    public Note(NoteName noteName, int octave, Set<NoteAccidental> noteAccidentals){
        this.noteName = noteName;
        this.octave = octave;
        accidentals.addAll(noteAccidentals);
    }

    /*public Note(Key key){
        this.noteName = NoteName.values()[key.getNaturalIndex()];
        this.octave = key.getOctave();
        accidentals.add(key.getAccidental());
    }*/

    public int getOctave() {
        return octave;
    }

    public NoteName getNoteName(){
        return noteName;
    }

    public Set<NoteAccidental> getActiveAccidentals() {
        return accidentals;
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
            return this.noteName == noteCompare.noteName
                    && this.octave == noteCompare.octave
                    && this.accidentals.equals(noteCompare.accidentals);
        }
        return false;
    }

    public boolean noteHeadEquals(Note note){
        return this.noteName == note.noteName && this.octave == note.octave;
    }

    @Override
    public int hashCode() {
        String nameOctave = noteName.name() + octave;
        return nameOctave.hashCode();
    }

    @Override
    public String toString() {
        String noteString = noteName.toString();
        if (accidentals.contains(NoteAccidental.NATURAL) && (accidentals.contains(NoteAccidental.FLAT) || accidentals.contains(NoteAccidental.SHARP))){
            noteString += "n";
        }
        if (accidentals.contains(NoteAccidental.SHARP)) {
            noteString += "#";
        }
        if (accidentals.contains(NoteAccidental.FLAT)) {
            noteString += "b";
        }
        noteString += Integer.toString(octave);
        return noteString;
    }

    public Note getNext(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() >= NoteName.B.getPosition()){
            newOctave++;
            newNoteName = NoteName.C;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() + 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    public Note getPrevious(NoteAccidental noteAccidental) {
        int newOctave = octave;
        NoteName newNoteName;

        if (noteName.getPosition() <= NoteName.C.getPosition()){
            newOctave--;
            newNoteName = NoteName.B;
        }
        else {
            newNoteName = NoteName.values()[noteName.getPosition() - 1];
        }
        return new Note(newNoteName, newOctave, noteAccidental);
    }

    @Override
    public int compareTo(Note o) {
        return noteValue() - o.noteValue();
    }

    protected int noteValue(){
        return octave * 10 + noteName.getPosition();
    }
}
