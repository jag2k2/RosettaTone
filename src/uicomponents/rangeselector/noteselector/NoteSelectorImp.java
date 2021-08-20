package uicomponents.rangeselector.noteselector;

import music.note.Note;
import uicomponents.rangeselector.noteselector.buttonfactory.LimitJButtonFactory;
import uicomponents.rangeselector.noteselector.selectorfactory.JComboNoteSelectorFactory;
import uicomponents.util.JSelector;
import uicomponents.util.SelectorFactory;
import uicomponents.util.State;
import javax.swing.*;
import java.awt.*;

public class NoteSelectorImp extends JComponent{
    public NoteSelectorImp(BoundSelectHandler boundSelectHandler, State<Note> previewLimit) {
        LimitButtonFactory limitButtonFactory = new LimitJButtonFactory();
        SelectorFactory<Note> selectorFactory = new JComboNoteSelectorFactory(previewLimit);

        JSelector<Note> noteSelector = boundSelectHandler.createNoteSelector(selectorFactory);
        AbstractButton incrementButton = boundSelectHandler.createIncrementButton(limitButtonFactory);
        AbstractButton decrementButton = boundSelectHandler.createDecrementButton(limitButtonFactory);

        this.setLayout(new BorderLayout());
        Dimension boxSize = new Dimension(80, 40);
        noteSelector.setPreferredSize(boxSize);

        this.add(BorderLayout.NORTH, incrementButton);
        this.add(BorderLayout.CENTER, noteSelector);
        this.add(BorderLayout.SOUTH, decrementButton);
    }
}
