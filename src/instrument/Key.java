package instrument;

import music.Note;
import music.NoteAccidental;
import music.NoteName;

import java.util.Set;

public class Key {
    private final int midiNumber;

    public Key(int midiNumber){
        this.midiNumber = midiNumber;
    }

    public Key(int octave, NoteName noteName, NoteAccidental noteAccidental) {
        int midiSum = 12 + (octave * 12);
        int notePosition = noteName.getPosition();
        if (notePosition >= 3) {
            midiSum--;
        }
        midiSum += 2 * notePosition;

        if (noteAccidental.equals(NoteAccidental.SHARP)){
            midiSum++;
        }

        if (noteAccidental.equals(NoteAccidental.FLAT)){
            midiSum--;
        }
        this.midiNumber = midiSum;
    }

    public Note getNote(){
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

    public Key getNext(){
        return new Key(midiNumber + 1);
    }

    public Key getPrevious(){
        return new Key(midiNumber - 1);
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

    public boolean isNatural(){
        return (isCDorE() && isEven()) || (!isCDorE() && !isEven());
    }

    public boolean sharpExistsAlso(Set<Key> keys){
        if (getNaturalIndex() == 2 || getNaturalIndex() == 6)
            return false;
        Key nextKey = getNext();
        return keys.contains(nextKey);
    }

    public boolean naturalExistsAlso(Set<Key> keys){
        Key previousKey = getPrevious();
        return keys.contains(previousKey);
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
