package uicomponents.alphabetmode;

import notification.AlphabetModeChangeObserver;

public interface AlphabetModeChangeNotifier {
    void addObserver(AlphabetModeChangeObserver alphabetModeChangeObserver);
    void notifyObservers();
}
