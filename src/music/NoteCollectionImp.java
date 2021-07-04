package music;

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
    public void clear() {
        notes.clear();
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
        Note adjacentNoteSharp = note.getPrevious(NoteAccidental.SHARP);
        Note adjacentNoteFlat = note.getPrevious(NoteAccidental.FLAT);
        if(notes.contains(adjacentNote) || notes.contains(adjacentNoteSharp) || notes.contains(adjacentNoteFlat)){
            return !isSqueezed(adjacentNote);
        }
        return false;
    }

    @Override
    public String toString() {
        return notes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteCollectionImp){
            NoteCollectionImp toCompare = (NoteCollectionImp) obj;
            return notes.equals(toCompare.notes);
        }
        return false;
    }
}
