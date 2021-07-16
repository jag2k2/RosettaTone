package notification;

import uicomponents.notenamemode.NoteNameModeChangeNotifier;

import java.util.HashSet;

public class NoteNameModeChangeNotifierImp implements NoteNameModeChangeNotifier {
    private final HashSet<NoteNameModeChangeObserver> observers;

    public NoteNameModeChangeNotifierImp() {
        this.observers = new HashSet<>();
    }

    @Override
    public void addObserver(NoteNameModeChangeObserver noteNameModeChangeObserver) {
        observers.add(noteNameModeChangeObserver);
    }

    @Override
    public void notifyObservers() {
        for (NoteNameModeChangeObserver observer : observers){
            observer.alphabetModeChanged();
        }
    }
}
