package music;

import utility.Maybe;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class NoteListImp implements NoteList {
    private final List<Note> notes;

    public NoteListImp(){
        notes = new ArrayList<>();
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
    public boolean contains(NoteList noteList) {
        for (Note note : noteList){
            if (!notes.contains(note)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isSandwiched(Note note){
        for (Note previousNote : getPrevious(note)){
            if (note.isAdjacent(previousNote) && !isSandwiched(previousNote)){
                return true;
            }
        }
        return false;
    }

    protected Maybe<Note> getPrevious(Note noteToFind) {
        int i = 0;
        for (Note note : notes){
            if (note.equals(noteToFind) && i > 0){
                return new Maybe<>(notes.get(i-1));
            }
            i++;
        }
        return new Maybe<>();
    }

    @Override
    public String toString() {
        return notes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteListImp){
            NoteListImp toCompare = (NoteListImp) obj;
            return notes.equals(toCompare.notes);
        }
        return false;
    }
}
