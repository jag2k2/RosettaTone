package statemodels.limitstate;

import music.note.Note;
import uicomponents.rangeselector.noteselector.SteppableState;

public class LowerBoundedLimitStateImp extends AbstractBoundedLimit {

    public LowerBoundedLimitStateImp(Note lowerBound, SteppableState<Note> limit, Note upperBound) {
        super(lowerBound, limit, upperBound);
    }

    @Override
    public void limitChanged() {
        setUpperBound();
    }
}
