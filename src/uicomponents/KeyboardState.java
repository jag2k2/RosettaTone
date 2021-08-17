package uicomponents;

import instrument.KeyStateManipulator;
import statemodels.KeyboardChangeNotifier;
import trainer.KeyStateEvaluator;
import uicomponents.renderer.grandstaff.KeyStateDrawable;

public interface KeyboardState extends KeyStateManipulator, KeyStateEvaluator, KeyStateDrawable{
    void addKeyboardChangeNotifier(KeyboardChangeNotifier keyboardChangeNotifier);
}
