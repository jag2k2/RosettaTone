package flashcard;

import music.Note;
import music.NoteList;

public interface RandomNoteGenerator {
    NoteList generateRandomNote(Note lowerNote, Note upperNote);
}
