package uicomponents.notenamemode;

import notification.NoteNameModeChangeObserver;

public interface NoteNameModeChangeNotifier {
    void addObserver(NoteNameModeChangeObserver noteNameModeChangeObserver);
    void notifyObservers();
}
