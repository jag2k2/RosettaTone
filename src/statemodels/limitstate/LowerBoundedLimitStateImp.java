package statemodels.limitstate;

import music.Note;
import uicomponents.rangeselector.noteselector.LimitModifier;

public class LowerBoundedLimitStateImp extends AbstractBoundedLimitState {
    private final LimitModifier otherLimit;

    public LowerBoundedLimitStateImp(LimitModifier limitModifier, LimitModifier otherLimit, Note lowerBound, Note upperBound) {
        super(limitModifier, lowerBound, upperBound);
        this.otherLimit = otherLimit;
    }

    @Override
    public void limitChanged() {
        setUpperBound(otherLimit);
    }
}
