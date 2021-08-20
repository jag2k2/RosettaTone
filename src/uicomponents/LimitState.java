package uicomponents;

import music.note.Note;
import statemodels.limitstate.LimitChangeNotifier;
import trainer.randomnotegenerator.BoundedNoteGenerator;
import uicomponents.rangeselector.noteselector.SteppableState;
import uicomponents.renderer.limit.LimitDrawable;

public interface LimitState extends SteppableState<Note>, LimitDrawable, BoundedNoteGenerator {
    void addLimitChangeNotifier(LimitChangeNotifier limitChangeNotifier);
}
