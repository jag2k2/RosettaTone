package statemodels;

import music.Note;

public interface NoteLimitModel {
    void changeLimit(Note note);
    Note getLimit();
}
