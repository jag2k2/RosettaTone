package tuples;

import music.Note;
import utility.NoteSet;

import java.util.*;

public class NoteSetImp implements NoteSet {
    private final Set<Note> notes;

    public NoteSetImp(){
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
    public boolean containsAll(NoteSet noteSet) {
        for (Note note : noteSet){
            if (!notes.contains(note)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NoteSetImp){
            NoteSetImp compareTo = (NoteSetImp) obj;
            return notes.equals(compareTo.notes);
        }
        return false;
    }

    @Override
    public String toString() {
        List<Note> noteList = new ArrayList<>(notes);
        Collections.sort(noteList);
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Note note : noteList){
            stringJoiner.add(note.toString());
        }
        return stringJoiner.toString();
    }
}
