package trainer;

import notification.KeyboardChangeObserver;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteSet;

public class SightReadTrainerImp implements KeyboardChangeObserver {
    private final KeyStateEvaluator keyboardState;
    private final FlashcardGenerator flashcardGenerator;
    private final FlashcardAdvancer flashcardAdvancer;
    private Maybe<FlashcardSatisfiedNotifier> flashcardSatisfiedNotifier = new Maybe<>();

    public SightReadTrainerImp(KeyStateEvaluator keyboardState, FlashcardGenerator flashcardGenerator, FlashcardAdvancer flashcardAdvancer){
        this.keyboardState = keyboardState;
        this.flashcardGenerator = flashcardGenerator;
        this.flashcardAdvancer = flashcardAdvancer;
        flashcardGenerator.generateAllNewFlashcards(RenderConstants.flashcardCount);
    }

    public void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier){
        this.flashcardSatisfiedNotifier = new Maybe<>(flashcardSatisfiedNotifier);
    }

    @Override
    public void KeyboardChanged() {
        for (NoteSet currentFlashcard : flashcardGenerator.peekAtTopFlashcard()){
            if(keyboardState.containsAll(currentFlashcard)){
                for (FlashcardSatisfiedNotifier notifier : flashcardSatisfiedNotifier){
                    notifier.notifyFlashcardSatisfied();
                }
                if(flashcardAdvancer.immediatelyAdvance()){
                    advanceFlashcards();
                }
            }
            if(!keyboardState.containsAll(currentFlashcard) && flashcardAdvancer.readyToAdvance()){
                advanceFlashcards();
            }
        }
    }

    protected void advanceFlashcards(){
        flashcardGenerator.removeTopFlashcard();
        flashcardGenerator.addNewGeneratedFlashcard();
    }
}
