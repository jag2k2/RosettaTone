package statemodels.notelimit;

import music.Note;
import uicomponents.rangeselector.noteselector.NoteModifier;

public class LowerBoundedNoteModifierImp extends AbstractBoundedNoteModifier {
    private final NoteModifier otherLimit;

    public LowerBoundedNoteModifierImp(NoteModifier noteModifier, NoteModifier otherLimit, Note lowerBound, Note upperBound, LimitChangeNotifier boundChangeNotifier) {
        super(noteModifier, lowerBound, upperBound, boundChangeNotifier);
        this.otherLimit = otherLimit;
    }

    @Override
    public void rangeChanged() {
        setUpperBound(otherLimit);
    }
}
