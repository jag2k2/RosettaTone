package trainer;

import utility.Maybe;
import utility.NoteCollection;

public interface NoteCollectionList extends Iterable<NoteCollection>{
    void clear();
    void add(NoteCollection noteCollection);
    Maybe<NoteCollection> getFirstItem();
    void removeFirstItem();
}
