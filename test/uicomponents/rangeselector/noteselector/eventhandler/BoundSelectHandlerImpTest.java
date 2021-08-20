package uicomponents.rangeselector.noteselector.eventhandler;

import music.note.Note;
import music.note.NoteName;
import notification.LimitChangeNotifierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statemodels.limitstate.*;
import uicomponents.LimitState;
import uicomponents.rangeselector.noteselector.BoundSelectHandler;
import uicomponents.rangeselector.noteselector.LimitButtonFactory;
import uicomponents.rangeselector.noteselector.SteppableState;
import uicomponents.rangeselector.noteselector.buttonfactory.LimitJButtonFactory;
import uicomponents.rangeselector.noteselector.selectorfactory.JComboNoteSelectorFactory;
import uicomponents.util.SelectorFactory;
import uicomponents.util.selectors.JComboSelectorImp;
import uicomponents.util.selectors.JSelector;
import javax.swing.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoundSelectHandlerImpTest {
    private BoundSelectHandler lowerBoundHandler;
    private BoundSelectHandler upperBoundHandler;

    private LimitState lowerLimit;
    private LimitState upperLimit;
    private AbstractBoundedLimit lowerBoundedLimit;
    private AbstractBoundedLimit upperBoundedLimit;
    private SteppableState<Note> lowerPreviewLimit;
    private SteppableState<Note> upperPreviewLimit;

    private AbstractButton lowerDecrement;
    private JSelector<Note> lowerSelector;
    private AbstractButton lowerIncrement;

    private AbstractButton upperDecrement;
    private JSelector<Note> upperSelector;
    private AbstractButton upperIncrement;

    private Note lowerBoundNote;
    private Note defaultLowerLimitNote;
    private Note defaultUpperLimitNote;
    private Note upperBoundNote;

    @BeforeEach
    void setup(){
        LimitChangeNotifier lowerLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier upperLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier lowerBoundChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier upperBoundChangeNotifier = new LimitChangeNotifierImp();

        lowerBoundNote = new Note(NoteName.A, 3);
        defaultLowerLimitNote = new Note(NoteName.C, 4);
        defaultUpperLimitNote = new Note(NoteName.C, 5);
        upperBoundNote = new Note(NoteName.C, 6);

        lowerLimit = new LimitStateImp(defaultLowerLimitNote);
        lowerLimit.addLimitChangeNotifier(lowerLimitChangeNotifier);

        upperLimit = new LimitStateImp(defaultUpperLimitNote);
        upperLimit.addLimitChangeNotifier(upperLimitChangeNotifier);

        lowerBoundedLimit = new LowerBoundedLimitStateImp(lowerBoundNote, lowerLimit, defaultUpperLimitNote);
        lowerBoundedLimit.addBoundChangeNotifier(lowerBoundChangeNotifier);
        lowerBoundedLimit.setOtherLimit(upperLimit);

        upperBoundedLimit = new UpperBoundedLimitStateImp(defaultLowerLimitNote, upperLimit, upperBoundNote);
        upperBoundedLimit.addBoundChangeNotifier(upperBoundChangeNotifier);
        upperBoundedLimit.setOtherLimit(lowerLimit);

        lowerPreviewLimit = new LimitStateImp(defaultLowerLimitNote);
        upperPreviewLimit = new LimitStateImp(defaultLowerLimitNote);

        lowerBoundHandler = new BoundSelectHandlerImp(lowerBoundedLimit, lowerPreviewLimit);
        upperBoundHandler = new BoundSelectHandlerImp(upperBoundedLimit, upperPreviewLimit);

        lowerLimitChangeNotifier.addObserver(lowerBoundHandler);
        lowerLimitChangeNotifier.addObserver(upperBoundedLimit);

        upperLimitChangeNotifier.addObserver(upperBoundHandler);
        upperLimitChangeNotifier.addObserver(lowerBoundedLimit);

        lowerBoundChangeNotifier.addObserver(lowerBoundHandler);
        upperBoundChangeNotifier.addObserver(upperBoundHandler);

        LimitButtonFactory buttonFactory = new LimitJButtonFactory();
        SelectorFactory<Note> lowerSelectorFactory = new JComboNoteSelectorFactory(lowerPreviewLimit);
        SelectorFactory<Note> upperSelectorFactory = new JComboNoteSelectorFactory(upperPreviewLimit);

        lowerDecrement = lowerBoundHandler.createDecrementButton(buttonFactory);
        lowerSelector = lowerBoundHandler.createNoteSelector(lowerSelectorFactory);
        lowerIncrement = lowerBoundHandler.createIncrementButton(buttonFactory);

        upperDecrement = upperBoundHandler.createDecrementButton(buttonFactory);
        upperSelector = upperBoundHandler.createNoteSelector(upperSelectorFactory);
        upperIncrement = upperBoundHandler.createIncrementButton(buttonFactory);
    }

    @Test
    void canStepLowerLimit(){
        lowerDecrement.doClick();
        LimitState expected = new LimitStateImp(new Note(NoteName.B, 3));
        assertEquals(expected, lowerLimit);

        lowerIncrement.doClick();
        lowerIncrement.doClick();
        expected = new LimitStateImp(new Note(NoteName.D, 4));
        assertEquals(expected, lowerLimit);
    }

    @Test
    void canSelectLowerLimit(){
        Note noteToSelect = new Note(NoteName.B, 3);
        Note[] options = lowerBoundedLimit.getOptions();
        int index = Arrays.asList(options).indexOf(noteToSelect);
        lowerSelector.setSelectedIndex(index);

        LimitState expected = new LimitStateImp(noteToSelect);
        assertEquals(expected, lowerLimit);
    }

    @Test
    void selectingLowerLimitUpdatesUpperSelectorOptions(){
        Note noteToSelect = new Note(NoteName.B, 3);
        Note[] options = lowerBoundedLimit.getOptions();
        int index = Arrays.asList(options).indexOf(noteToSelect);
        lowerSelector.setSelectedIndex(index);

        LimitState expectedLimit = new LimitStateImp(defaultUpperLimitNote);
        AbstractBoundedLimit expectedBoundedLimit = new UpperBoundedLimitStateImp(noteToSelect, expectedLimit, upperBoundNote);
        JSelector<Note> expected = new JComboSelectorImp<>(expectedBoundedLimit);
        expected.refreshSelections();

        assertEquals(expected, upperSelector);
    }

    @Test
    void selectingUpperLimitUpdatesLowerSelectorOptions(){
        Note noteToSelect = new Note(NoteName.C, 5);
        Note[] options = upperBoundedLimit.getOptions();
        int index = Arrays.asList(options).indexOf(noteToSelect);
        upperSelector.setSelectedIndex(index);

        LimitState expectedLimit = new LimitStateImp(defaultLowerLimitNote);
        AbstractBoundedLimit expectedBoundedLimit = new LowerBoundedLimitStateImp(lowerBoundNote, expectedLimit, noteToSelect);
        JSelector<Note> expected = new JComboSelectorImp<>(expectedBoundedLimit);
        expected.refreshSelections();

        assertEquals(expected, lowerSelector);
    }
}