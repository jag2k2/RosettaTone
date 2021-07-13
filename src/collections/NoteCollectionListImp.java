package collections;

import utility.Maybe;
import utility.NoteCollection;
import trainer.NoteCollectionList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoteCollectionListImp implements NoteCollectionList {
    private final List<NoteCollection> noteCollections;

    public NoteCollectionListImp(){
        this.noteCollections = new ArrayList<>();
    }

    @Override
    public void clear() {
        noteCollections.clear();
    }

    @Override
    public void add(NoteCollection noteCollection) {
        noteCollections.add(noteCollection);
    }

    @Override
    public Maybe<NoteCollection> getFirstItem() {
        if (noteCollections.size() > 0)
            return new Maybe<>(noteCollections.get(0));
        return new Maybe<>();
    }

    @Override
    public void removeFirstItem() {
        if (noteCollections.size() > 0)
            noteCollections.remove(0);
    }

    @Override
    public Iterator<NoteCollection> iterator() {
        return noteCollections.iterator();
    }
}
