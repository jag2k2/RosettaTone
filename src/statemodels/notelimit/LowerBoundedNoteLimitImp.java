package statemodels.notelimit;

import music.Note;
import uicomponents.rangeselector.noteselector.NoteModifier;

public class LowerBoundedNoteLimitImp extends AbstractBoundedNoteLimit {
    private final NoteModifier otherLimit;

    public LowerBoundedNoteLimitImp(NoteModifier noteModifier, NoteModifier otherLimit, Note lowerBound, Note upperBound, LimitChangeNotifier boundChangeNotifier) {
        super(noteModifier, lowerBound, upperBound, boundChangeNotifier);
        this.otherLimit = otherLimit;
    }

    @Override
    public void rangeChanged() {
        setUpperBound(otherLimit);
    }
}
