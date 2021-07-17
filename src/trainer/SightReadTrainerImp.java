package trainer;

import notification.KeyboardChangeObserver;
import uicomponents.renderer.ScoreDrawable;
import uicomponents.renderer.records.RenderConstants;
import utility.Maybe;
import utility.NoteSet;

public class SightReadTrainerImp implements KeyboardChangeObserver {
    private final KeyStateEvaluator keyboardState;
    private final FlashcardGenerator flashcardGenerator;
    private final FlashcardAdvancer flashcardAdvancer;
    private final ScoreKeepable score;
    private Maybe<FlashcardSatisfiedNotifier> flashcardSatisfiedNotifier = new Maybe<>();

    private int hits = 0;
    private int misses = 0;

    public SightReadTrainerImp(KeyStateEvaluator keyboardState, FlashcardGenerator flashcardGenerator, FlashcardAdvancer flashcardAdvancer, ScoreKeepable score){
        this.keyboardState = keyboardState;
        this.flashcardGenerator = flashcardGenerator;
        this.flashcardAdvancer = flashcardAdvancer;
        this.score = score;
        flashcardGenerator.generateAllNewFlashcards(RenderConstants.flashcardCount);
    }

    public void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier){
        this.flashcardSatisfiedNotifier = new Maybe<>(flashcardSatisfiedNotifier);
    }

    @Override
    public void boardChangedWithKeyDown() {
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
        System.out.println("Hits: " + hits + ", Misses: " + misses);
    }

    @Override
    public void boardChangedWithKeyUp() {
        for (NoteSet currentFlashcard : flashcardGenerator.peekAtTopFlashcard())
            if(!keyboardState.containsAll(currentFlashcard) && flashcardAdvancer.readyToAdvance()){
                advanceFlashcards();
            }
    }

    protected void advanceFlashcards(){
        flashcardGenerator.removeTopFlashcard();
        flashcardGenerator.addNewGeneratedFlashcard();
    }
}
