package uicomponents.rangeselector.noteselector.eventhandler;

import music.note.Note;
import statemodels.limitstate.AbstractBoundedLimit;
import uicomponents.rangeselector.noteselector.LimitButtonFactory;
import uicomponents.rangeselector.noteselector.SteppableState;
import uicomponents.rangeselector.noteselector.BoundSelectHandler;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.SelectorFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

public class BoundSelectHandlerImp implements BoundSelectHandler {
    private final AbstractBoundedLimit boundedNoteLimit;
    private final SteppableState<Note> previewLimit;
    private final Set<JSelector<Note>> selectors;

    public BoundSelectHandlerImp(AbstractBoundedLimit boundedNoteLimit, SteppableState<Note> previewLimit) {
        this.boundedNoteLimit = boundedNoteLimit;
        this.previewLimit = previewLimit;
        this.selectors = new HashSet<>();
    }

    @Override
    public JSelector<Note> createNoteSelector(SelectorFactory<Note> selectorFactory) {
        JSelector<Note> selector = selectorFactory.makeSelector(boundedNoteLimit);
        selectors.add(selector);
        return selector;
    }

    @Override
    public AbstractButton createIncrementButton(LimitButtonFactory controlButtonFactory) {
        AbstractButton incrementButton = controlButtonFactory.constructIncrementButton();
        incrementButton.addActionListener(this);
        incrementButton.setActionCommand("up");
        return incrementButton;
    }

    @Override
    public AbstractButton createDecrementButton(LimitButtonFactory controlButtonFactory) {
        AbstractButton decrementButton = controlButtonFactory.constructDecrementButton();
        decrementButton.addActionListener(this);
        decrementButton.setActionCommand("down");
        return decrementButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("up")){
            boundedNoteLimit.increment();
        }
        else if (command.equals("down")){
            boundedNoteLimit.decrement();
        }
        previewLimit.update(boundedNoteLimit.getActive());
    }

    @Override
    public void limitChanged() {
        for (JSelector<Note> selector : selectors){
            selector.refreshSelections();
        }
    }
}
