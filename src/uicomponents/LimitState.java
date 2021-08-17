package uicomponents;

import statemodels.limitstate.LimitChangeNotifier;
import trainer.randomnotegenerator.BoundedNoteGenerator;
import uicomponents.rangeselector.noteselector.LimitModifier;
import uicomponents.renderer.limit.LimitDrawable;

public interface LimitState extends LimitModifier, LimitDrawable, BoundedNoteGenerator {
    void addLimitChangeNotifier(LimitChangeNotifier limitChangeNotifier);
}
