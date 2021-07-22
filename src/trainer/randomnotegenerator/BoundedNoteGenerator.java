package trainer.randomnotegenerator;

import music.note.Note;

public interface BoundedNoteGenerator {
    Note generateRandomNote(BoundedNoteGenerator upperLimit);
}
