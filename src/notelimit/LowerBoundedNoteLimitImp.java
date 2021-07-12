package notelimit;

import music.Note;
import uicomponents.rangeselector.noteselector.LimitModifier;

public class LowerBoundedNoteLimitImp extends AbstractBoundedNoteLimit {
    private final LimitModifier otherLimit;

    public LowerBoundedNoteLimitImp(LimitModifier limitModifier, LimitModifier otherLimit, Note lowerBound, Note upperBound) {
        super(limitModifier, lowerBound, upperBound);
        this.otherLimit = otherLimit;
    }

    @Override
    public void rangeChanged() {
        setUpperBound(otherLimit);
    }
}
