package uicomponents;

import notification.FlashcardChangeObserver;
import notification.FlashcardSatisfiedObserver;
import statemodels.ConfigChangeNotifier;
import trainer.FlashcardAdvancer;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.renderer.grandstaff.NoteNameDrawable;
import uicomponents.util.SelectableState;

public interface NoteNameModeState extends SelectableState<NoteNameMode>, NoteNameDrawable, FlashcardAdvancer, FlashcardSatisfiedObserver, FlashcardChangeObserver {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
