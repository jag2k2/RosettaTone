package instrument;

import music.note.Note;
import music.note.NoteAccidental;
import music.note.NoteName;

public class Key {
    private final int midiNumber;

    public Key(int midiNumber){
        this.midiNumber = midiNumber;
    }

    public Key(Note note){
        this.midiNumber = note.getMidiNumber();
    }

    public Note convertToNote(){
        return new Note(NoteName.values()[getNaturalIndex()], getOctave(), getAccidental());
    }

    public int getOctave(){
        return (midiNumber / 12) - 1;
    }

    public int getNaturalIndex(){
       if (isCDorE())
           return getOctavePosition() / 2;
       else
           return (1 + getOctavePosition()) / 2;
    }

    protected NoteAccidental getAccidental(){
        if (isCDorE())
            if (getOctavePosition() % 2 == 1)
                return NoteAccidental.SHARP;
            else
                return NoteAccidental.NATURAL;
        else
            if (1 + getOctavePosition() % 2 == 1)
                return NoteAccidental.SHARP;
            else
                return NoteAccidental.NATURAL;
    }

    protected int getOctavePosition(){
        return midiNumber % 12;
    }

    protected boolean isCDorE(){
        return (getOctavePosition() < 5);
    }

    protected boolean isEven(){
        return ((midiNumber % 2) == 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key){
            Key compare = (Key) obj;
            return (midiNumber == compare.midiNumber);
        }
        return false;
    }

    @Override
    public String toString() {
        return "midi: " + midiNumber;
    }

    @Override
    public int hashCode() {
        return Integer.toString(midiNumber).hashCode();
    }
}
