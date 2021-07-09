package statemodels;

import music.Note;

public class NoteRangeLimitsImp implements NoteRangeLimits{
    private final NoteLimitModel lowerLimitModel;
    private final NoteLimitModel upperLimitModel;

    public NoteRangeLimitsImp(NoteLimitModel lowerLimitModel, NoteLimitModel upperLimitModel){
        this.lowerLimitModel = lowerLimitModel;
        this.upperLimitModel = upperLimitModel;
    }

    @Override
    public Note getLowerLimitNote() {
        return lowerLimitModel.getLimit();
    }

    @Override
    public Note getUpperLimitNote() {
        return upperLimitModel.getLimit();
    }
}
