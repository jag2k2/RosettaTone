package com.jag2k2.instrument;

import com.jag2k2.music.Note;
import com.jag2k2.music.NoteAccidental;
import com.jag2k2.music.NoteName;

import java.util.Set;

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
