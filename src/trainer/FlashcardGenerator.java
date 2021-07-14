package trainer;

import utility.Maybe;
import utility.NoteSet;

public interface FlashcardGenerator {
    void removeTopFlashcard();
    void addNewGeneratedFlashcard();
    Maybe<NoteSet> peekAtTopFlashcard();
    void generateAllNewFlashcards(int count);
}
