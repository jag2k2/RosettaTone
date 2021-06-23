package music;

import utility.Maybe;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ActiveNotesImp implements ActiveNotes {
    private final List<Note> activeNotes;

    public ActiveNotesImp(){
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
    public boolean isShifted(Note note){
        for (Note previousNote : getPrevious(note)){
            if (note.isAdjacent(previousNote) && !isShifted(previousNote)){
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
        if (obj instanceof ActiveNotesImp){
            ActiveNotesImp toCompare = (ActiveNotesImp) obj;
            return activeNotes.equals(toCompare.activeNotes);
        }
        return false;
    }
}
