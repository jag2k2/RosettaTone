package trainer;

import notification.KeyboardChangeObserver;
import uicomponents.renderer.grandstaff.Enableable;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteSet;

public class SightReadTrainerImp implements KeyboardChangeObserver {
    private final KeyStateEvaluator keyboardState;
    private final FlashcardGenerator flashcardGenerator;
    private final FlashcardAdvancer flashcardAdvancer;
    private final ScoreKeepable score;
    private final Enableable trainerState;
    private Maybe<FlashcardSatisfiedNotifier> flashcardSatisfiedNotifier = new Maybe<>();

    public SightReadTrainerImp(KeyStateEvaluator keyboardState, FlashcardGenerator flashcardGenerator, FlashcardAdvancer flashcardAdvancer, ScoreKeepable score, Enableable trainerState){
        this.keyboardState = keyboardState;
        this.flashcardGenerator = flashcardGenerator;
        this.flashcardAdvancer = flashcardAdvancer;
        this.score = score;
        this.trainerState = trainerState;
        flashcardGenerator.generateAllNewFlashcards(RenderConstants.flashcardCount);
    }

    public void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier){
        this.flashcardSatisfiedNotifier = new Maybe<>(flashcardSatisfiedNotifier);
    }

    @Override
    public void boardChangedWithKeyDown() {
        if (trainerState.isEnabled()){
            for (NoteSet currentFlashcard : flashcardGenerator.peekAtTopFlashcard()){
                if(keyboardState.containsAll(currentFlashcard)){
                    score.addHit();
                    for (FlashcardSatisfiedNotifier notifier : flashcardSatisfiedNotifier){
                        notifier.notifyFlashcardSatisfied();
                    }
                    if(flashcardAdvancer.immediatelyAdvance()){
                        advanceFlashcards();
                    }
                } else
                    score.addMiss();
            }
        }
    }

    @Override
    public void boardChangedWithKeyUp() {
        if (trainerState.isEnabled()){
            for (NoteSet currentFlashcard : flashcardGenerator.peekAtTopFlashcard())
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
