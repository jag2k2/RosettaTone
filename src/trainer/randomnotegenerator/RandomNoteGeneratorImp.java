package trainer.randomnotegenerator;

import music.Note;
import trainer.RandomNoteGenerator;
import uicomponents.renderer.records.RenderConstants;

public class RandomNoteGeneratorImp implements RandomNoteGenerator {
    private final LineNumberable lowerLimit;
    private final LineNumberable upperLimit;

    public RandomNoteGeneratorImp(LineNumberable lowerLimit, LineNumberable upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    public Note generateSingleNote() {
        int max = Math.max(lowerLimit.getLineNumber(), upperLimit.getLineNumber());
        int min = Math.min(lowerLimit.getLineNumber(), upperLimit.getLineNumber());
        int randomLine = (int) Math.floor(Math.random()*(max-min+1) + min);
        return RenderConstants.getNote(randomLine);
    }
}
