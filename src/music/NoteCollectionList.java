package music;

import utility.Maybe;

public interface NoteCollectionList extends Iterable<NoteCollection>{
    void clear();
    void add(NoteCollection noteCollection);
    Maybe<NoteCollection> getFirstItem();
    void removeFirstItem();
}
