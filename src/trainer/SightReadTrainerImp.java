package trainer;

import notification.KeyboardChangeObserver;
import utility.Maybe;
import utility.NoteSet;

public class SightReadTrainerImp implements KeyboardChangeObserver {
    private final KeyStateEvaluator keyboardState;
    private final FlashcardGenerator flashcardGenerator;
    private Maybe<FlashcardSatisfiedNotifier> flashcardSatisfiedNotifier = new Maybe<>();


    private boolean satisfied = false;


    public SightReadTrainerImp(KeyStateEvaluator keyboardState, FlashcardGenerator flashcardGenerator){
        this.keyboardState = keyboardState;
        this.flashcardGenerator = flashcardGenerator;
    }

    public void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier){
        this.flashcardSatisfiedNotifier = new Maybe<>(flashcardSatisfiedNotifier);
    }

    @Override
    public void KeyboardChanged() {
        for (NoteSet currentFlashcard : flashcardGenerator.peekAtTopFlashcard()){
            if(keyboardState.containsAll(currentFlashcard)){
                satisfied = true;
                for (FlashcardSatisfiedNotifier notifier : flashcardSatisfiedNotifier){
                    notifier.notifyFlashcardSatisfied();
                }
            }
            if(!keyboardState.containsAll(currentFlashcard) && satisfied){
                satisfied = false;
                flashcardGenerator.removeTopFlashcard();
                flashcardGenerator.addNewGeneratedFlashcard();
            }
        }
    }
}
