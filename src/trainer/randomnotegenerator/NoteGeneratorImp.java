package trainer.randomnotegenerator;

import music.Note;
import statemodels.RandomNoteGenerator;

public class NoteGeneratorImp implements RandomNoteGenerator {
    private final BoundedNoteGenerator lowerLimit;
    private final BoundedNoteGenerator upperLimit;

    public NoteGeneratorImp(BoundedNoteGenerator lowerLimit, BoundedNoteGenerator upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    public Note generateSingleNote() {
        return lowerLimit.generateRandomNote(upperLimit);
    }
}
