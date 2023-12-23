package com.jag2k2.statemodels.limitstate;

import com.jag2k2.music.Note;
import com.jag2k2.uicomponents.rangeselector.noteselector.LimitModifier;

public class UpperBoundedLimitStateImp extends AbstractBoundedLimitState {
    private final LimitModifier otherLimit;

    public UpperBoundedLimitStateImp(LimitModifier limitModifier, LimitModifier otherLimit, Note lowerBound, Note upperBound) {
        super(limitModifier, lowerBound, upperBound);
        this.otherLimit = otherLimit;
    }

    @Override
    public void limitChanged() {
        setLowerBound(otherLimit);
    }
}
