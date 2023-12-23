package com.jag2k2.trainer;

import com.jag2k2.utility.Maybe;
import com.jag2k2.utility.NoteSet;

public interface FlashcardGenerator {
    void removeTopFlashcard();
    void addNewGeneratedFlashcard();
    Maybe<NoteSet> peekAtTopFlashcard();
    void generateAllNewFlashcards(int count);
}
