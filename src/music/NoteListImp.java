package music;

import utility.Maybe;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class NoteListImp implements NoteList {
    private final List<Note> activeNotes;

    public NoteListImp(){
        activeNotes = new ArrayList<>();
    }

    @Override
    public Iterator<Note> iterator() {
        return activeNotes.iterator();
    }

    @Override
    public void add(Note note){
        activeNotes.add(note);
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
        for (Note note : activeNotes){
            if (note.equals(noteToFind) && i > 0){
                return new Maybe<>(activeNotes.get(i-1));
            }
            i++;
        }
        return new Maybe<>();
    }

    @Override
    public String toString() {
        return activeNotes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteListImp){
            NoteListImp toCompare = (NoteListImp) obj;
            return activeNotes.equals(toCompare.activeNotes);
        }
        return false;
    }
}
