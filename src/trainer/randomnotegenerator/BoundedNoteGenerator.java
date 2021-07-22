package trainer.randomnotegenerator;

import music.Note;

public interface BoundedNoteGenerator {
    Note generateRandomNote(BoundedNoteGenerator upperLimit);
}
