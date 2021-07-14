package notification;

import uicomponents.alphabetmode.AlphabetModeChangeNotifier;

import java.util.HashSet;

public class AlphabetModeChangeNotifierImp implements AlphabetModeChangeNotifier {
    private final HashSet<AlphabetModeChangeObserver> observers;

    public AlphabetModeChangeNotifierImp() {
        this.observers = new HashSet<>();
    }

    @Override
    public void addObserver(AlphabetModeChangeObserver alphabetModeChangeObserver) {
        observers.add(alphabetModeChangeObserver);
    }

    @Override
    public void notifyObservers() {
        for (AlphabetModeChangeObserver observer : observers){
            observer.alphabetModeChanged();
        }
    }
}
