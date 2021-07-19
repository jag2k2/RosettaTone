package trainer;

import notification.KeyboardChangeObserver;
import statemodels.ConfigChangeNotifier;
import uicomponents.renderer.grandstaff.Enableable;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.trainer.TrainerStateModifier;
import utility.Maybe;
import utility.NoteSet;

public class SightReadTrainerImp implements TrainerStateModifier, KeyboardChangeObserver, Enableable {
    private final KeyStateEvaluator keyboardState;
    private final FlashcardGenerator flashcardGenerator;
    private final FlashcardAdvancer flashcardAdvancer;
    private final ScoreKeepable score;
    private boolean enabled = false;
    private Maybe<FlashcardSatisfiedNotifier> flashcardSatisfiedNotifier = new Maybe<>();
    private Maybe<ConfigChangeNotifier> configChangeNotifier = new Maybe<>();

    public SightReadTrainerImp(KeyStateEvaluator keyboardState, FlashcardGenerator flashcardGenerator, FlashcardAdvancer flashcardAdvancer, ScoreKeepable score){
        this.keyboardState = keyboardState;
        this.flashcardGenerator = flashcardGenerator;
        this.flashcardAdvancer = flashcardAdvancer;
        this.score = score;
        flashcardGenerator.generateAllNewFlashcards(RenderConstants.flashcardCount);
    }

    public void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier){
        this.configChangeNotifier = new Maybe<>(configChangeNotifier);
    }

    public void addFlashcardSatisfiedNotifier(FlashcardSatisfiedNotifier flashcardSatisfiedNotifier){
        this.flashcardSatisfiedNotifier = new Maybe<>(flashcardSatisfiedNotifier);
    }

    @Override
    public void enable() {
        if(!enabled){
            this.enabled = true;
            for (ConfigChangeNotifier notifier : configChangeNotifier){
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public void disable() {
        if(enabled) {
            this.enabled = false;
            for (ConfigChangeNotifier notifier : configChangeNotifier) {
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void boardChangedWithKeyDown() {
        if (enabled){
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
        if (enabled){
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
