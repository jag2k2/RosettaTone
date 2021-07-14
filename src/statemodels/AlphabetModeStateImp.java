package statemodels;

import uicomponents.alphabetmode.AlphabetMode;
import uicomponents.alphabetmode.AlphabetModeChangeNotifier;
import uicomponents.alphabetmode.AlphabetModeModifier;
import uicomponents.renderer.AlphabetDrawable;
import utility.Maybe;

public class AlphabetModeStateImp implements AlphabetModeModifier, AlphabetDrawable {
    private AlphabetMode alphabetMode;
    private Maybe<AlphabetModeChangeNotifier> alphabetModeChangeNotifier = new Maybe<>();

    public AlphabetModeStateImp(AlphabetMode defaultMode) {
        this.alphabetMode = defaultMode;
    }

    public void addAlphabetModeChangeNotifier(AlphabetModeChangeNotifier alphabetModeChangeNotifier){
        this.alphabetModeChangeNotifier = new Maybe<>(alphabetModeChangeNotifier);
    }

    @Override
    public void setMode(AlphabetMode alphabetMode) {
        if(!this.alphabetMode.equals(alphabetMode)) {
            this.alphabetMode = alphabetMode;
            for (AlphabetModeChangeNotifier notifier : alphabetModeChangeNotifier) {
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return alphabetMode == AlphabetMode.Always;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AlphabetModeStateImp){
            AlphabetModeStateImp toCompare = (AlphabetModeStateImp) obj;
            return alphabetMode.equals(toCompare.alphabetMode);
        }
        return false;
    }

    @Override
    public String toString() {
        return alphabetMode.toString();
    }
}
