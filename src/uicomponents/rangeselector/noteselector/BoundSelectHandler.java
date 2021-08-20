package uicomponents.rangeselector.noteselector;

import music.note.Note;
import notification.LimitChangeObserver;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.SelectorFactory;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface BoundSelectHandler extends ActionListener, LimitChangeObserver {
    JSelector<Note> createNoteSelector(SelectorFactory<Note> selectorFactory);
    AbstractButton createIncrementButton(LimitButtonFactory limitButtonFactory);
    AbstractButton createDecrementButton(LimitButtonFactory limitButtonFactory);
}
