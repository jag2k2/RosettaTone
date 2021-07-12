package statemodels.notelimit;

import music.Note;
import uicomponents.rangeselector.noteselector.NoteModifier;

public class UpperBoundedNoteModifierImp extends AbstractBoundedNoteModifier {
    private final NoteModifier otherLimit;

    public UpperBoundedNoteModifierImp(NoteModifier noteModifier, NoteModifier otherLimit, Note lowerBound, Note upperBound, LimitChangeNotifier boundChangeNotifier) {
        super(noteModifier, lowerBound, upperBound, boundChangeNotifier);
        this.otherLimit = otherLimit;
    }

    @Override
    public void rangeChanged() {
        setLowerBound(otherLimit);
    }
}
