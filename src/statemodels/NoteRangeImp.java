package statemodels;

import music.Note;

public class NoteRangeImp implements NoteRangeModel, NoteRangeLimits{
    private final NoteLimitModel lowerLimitModel;
    private final NoteLimitModel upperLimitModel;

    public NoteRangeImp(NoteLimitModel lowerLimitModel, NoteLimitModel upperLimitModel){
        this.lowerLimitModel = lowerLimitModel;
        this.upperLimitModel = upperLimitModel;
    }

    @Override
    public NoteLimitModel getLowerLimitModel() {
        return lowerLimitModel;
    }

    @Override
    public NoteLimitModel getUpperLimitModel() {
        return upperLimitModel;
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
