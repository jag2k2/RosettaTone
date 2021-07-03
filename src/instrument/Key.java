package instrument;

import music.Note;
import music.NoteAccidental;
import music.NoteName;

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

    public Key generateRandomKeyBetweenThisAnd(Key otherKey){
        int max = otherKey.midiNumber;
        int min = this.midiNumber;
        int randomMidiNumber = (int) Math.floor(Math.random()*(max-min+1) + min);
        return new Key(randomMidiNumber);
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

    protected int getOctavePosition(){
        return midiNumber % 12;
    }

    public boolean isCDorE(){
        return (getOctavePosition() < 5);
    }

    public boolean isEven(){
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
        return "midi: " + Integer.toString(midiNumber);
    }
}
