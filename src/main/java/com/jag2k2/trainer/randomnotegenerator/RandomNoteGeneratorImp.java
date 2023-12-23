package com.jag2k2.trainer.randomnotegenerator;

import com.jag2k2.music.Note;
import com.jag2k2.trainer.RandomNoteGenerator;

public class RandomNoteGeneratorImp implements RandomNoteGenerator {
    private final LineNumerable lowerLimit;
    private final LineNumerable upperLimit;

    public RandomNoteGeneratorImp(LineNumerable lowerLimit, LineNumerable upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    public Note generateSingleNote() {
        int max = Math.max(lowerLimit.getLineNumber(), upperLimit.getLineNumber());
        int min = Math.min(lowerLimit.getLineNumber(), upperLimit.getLineNumber());
        int randomLine = (int) Math.floor(Math.random()*(max-min+1) + min);
        return new Note(randomLine);
    }
}
