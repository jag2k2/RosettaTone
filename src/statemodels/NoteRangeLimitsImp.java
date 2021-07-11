package statemodels;

import music.Note;
import music.NoteName;
import trainer.NoteRangeLimits;
import uicomponents.rangeselector.noteselector.NoteLimitModel;

public class NoteRangeLimitsImp implements NoteRangeLimits {
    private final NoteLimitModel lowerLimitModel;
    private final NoteLimitModel upperLimitModel;

    public NoteRangeLimitsImp(NoteLimitModel lowerLimitModel, NoteLimitModel upperLimitModel){
        this.lowerLimitModel = lowerLimitModel;
        this.upperLimitModel = upperLimitModel;
    }

    @Override
    public Note generateRandomNote() {
        return new Note(NoteName.A, 4);
    }
}
