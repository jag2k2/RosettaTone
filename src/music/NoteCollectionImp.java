package music;

import utility.NoteCollection;

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
    public boolean contains(NoteCollection noteCollection) {
        for (Note note : noteCollection){
            if (!notes.contains(note)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isSqueezed(Note note){
        Note adjacentNote = note.getPrevious(NoteAccidental.NATURAL);
        for (Note noteInSet : notes){
            if (noteInSet.noteHeadEquals(adjacentNote)){
                return !isSqueezed(adjacentNote);
            }
        }
        return false;
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
