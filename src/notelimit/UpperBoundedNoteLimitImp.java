package notelimit;

import music.Note;
import uicomponents.rangeselector.noteselector.LimitModifier;

public class UpperBoundedNoteLimitImp extends AbstractBoundedNoteLimit {
    private final LimitModifier otherLimit;

    public UpperBoundedNoteLimitImp(LimitModifier limitModifier, LimitModifier otherLimit, Note lowerBound, Note upperBound) {
        super(limitModifier, lowerBound, upperBound);
        this.otherLimit = otherLimit;
    }

    @Override
    public void rangeChanged() {
        setLowerBound(otherLimit);
    }
}
