package collections;

import music.Note;
import music.NoteAccidental;
import statemodels.NoteDrawable;
import utility.NoteCollection;

import java.awt.*;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class NoteCollectionImp implements NoteCollection {
    private final Set<Note> notes;

    public NoteCollectionImp(){
        notes = new HashSet<>();
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    @Override
    public void add(Note note){
        notes.add(note);
    }

    @Override
    public boolean containsAll(NoteCollection noteCollection) {
        for (Note note : noteCollection){
            if (!notes.contains(note)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteCollectionImp){
            NoteCollectionImp compareTo = (NoteCollectionImp) obj;
            return notes.equals(compareTo.notes);
        }
        return false;
    }

    @Override
    public String toString() {
        return notes.toString();
    }
}
