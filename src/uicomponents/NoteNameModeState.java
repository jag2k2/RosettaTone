package uicomponents;

import notification.FlashcardChangeObserver;
import notification.FlashcardSatisfiedObserver;
import statemodels.ConfigChangeNotifier;
import trainer.FlashcardAdvancer;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.renderer.grandstaff.NoteNameDrawable;
import uicomponents.util.CanSetMode;

public interface NoteNameModeState extends CanSetMode<NoteNameMode>, NoteNameDrawable, FlashcardAdvancer, FlashcardSatisfiedObserver, FlashcardChangeObserver {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
