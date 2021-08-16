package instrument.key;

import music.note.NoteAccidental;
import music.note.NoteDescribable;
import music.note.NoteName;

public class Key implements NoteDescribable {
    private final int midiNumber;

    public Key(int midiNumber){
        this.midiNumber = midiNumber;
    }

    public Key(MidiEnumerable note){
        this.midiNumber = note.getMidiNumber();
    }

    public int getOctave(){
        return (midiNumber / 12) - 1;
    }

    public NoteName getNoteName(){
       if (isCDorE()) {
           int naturalPosition = getOctavePosition() / 2;
           return NoteName.values()[naturalPosition];
       }
       else {
           int naturalPosition = (1 + getOctavePosition()) / 2;
           return NoteName.values()[naturalPosition];
       }
    }

    public NoteAccidental getAccidental(){
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
        return "Midi: " + midiNumber;
    }

    @Override
    public int hashCode() {
        return Integer.toString(midiNumber).hashCode();
    }
}
