package uicomponents;

import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.note.Note;
import music.note.NoteName;
import notification.*;
import statemodels.*;
import statemodels.limitstate.LimitChangeNotifier;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardSatisfiedNotifier;
import trainer.randomnotegenerator.NoteGeneratorImp;
import statemodels.limitstate.LowerBoundedLimitStateImp;
import statemodels.limitstate.LimitStateImp;
import statemodels.limitstate.UpperBoundedLimitStateImp;
import trainer.SightReadTrainerImp;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.NoteNameModeSelectorImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.noteselector.NoteSelectorImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.grandstaff.GrandRendererImp;
import uicomponents.renderer.text.NoteTextRenderer;
import uicomponents.renderer.limit.RangeRendererImp;
import uicomponents.staffmode.StaffModeHandler;
import uicomponents.staffmode.StaffModeSelectorImp;
import uicomponents.staffmode.StaffMode;
import uicomponents.staffmode.modehandler.StaffModeHandlerImp;
import uicomponents.trainer.ControlHandler;
import uicomponents.trainer.TrainerControlImp;
import uicomponents.trainer.controlhandler.ControlHandlerImp;

public class MainGUI {

    public Container makePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifier keyboardChangeNotifier = new KeyboardChangeNotifierImp();
        ConfigChangeNotifier configChangeNotifier = new ConfigChangeNotifierImp();
        LimitChangeNotifier lowerLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier upperLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier lowerBoundChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier upperBoundChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifier previewLimitNotifier = new LimitChangeNotifierImp();
        FlashcardSatisfiedNotifier flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifier flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        //State Models
        KeyboardStateImp keyboardStateImp = new KeyboardStateImp();
        keyboardStateImp.addKeyboardChangeNotifier(keyboardChangeNotifier);

        Note lowerBoundNote = new Note(NoteName.A, 0);
        Note upperBoundNote = new Note(NoteName.C, 8);
        Note defaultLowerLimitNote = new Note(NoteName.C, 4);
        Note defaultUpperLimitNote = new Note(NoteName.C, 5);
        StaffMode defaultStaffMode = StaffMode.Grand;

        LimitStateImp lowerLimitImp = new LimitStateImp(defaultLowerLimitNote);
        lowerLimitImp.addLimitChangeNotifier(lowerLimitChangeNotifier);

        LimitStateImp upperLimitImp = new LimitStateImp(defaultUpperLimitNote);
        upperLimitImp.addLimitChangeNotifier(upperLimitChangeNotifier);

        LimitStateImp lowerPreviewLimitImp = new LimitStateImp(defaultLowerLimitNote);
        lowerPreviewLimitImp.addLimitChangeNotifier(previewLimitNotifier);

        LimitStateImp upperPreviewLimitImp = new LimitStateImp(defaultUpperLimitNote);
        upperPreviewLimitImp.addLimitChangeNotifier(previewLimitNotifier);

        RangeStateImp limitRangeStateImp = new RangeStateImp(lowerLimitImp, upperLimitImp);
        RangeStateImp previewRangeStateImp = new RangeStateImp(lowerPreviewLimitImp, upperPreviewLimitImp);

        LowerBoundedLimitStateImp lowerBoundedNoteLimitImp = new LowerBoundedLimitStateImp(lowerLimitImp, upperLimitImp, lowerBoundNote, defaultUpperLimitNote);
        lowerBoundedNoteLimitImp.addBoundChangeNotifier(lowerBoundChangeNotifier);
        UpperBoundedLimitStateImp upperBoundedNoteLimitImp = new UpperBoundedLimitStateImp(upperLimitImp, lowerLimitImp, defaultLowerLimitNote, upperBoundNote);
        upperBoundedNoteLimitImp.addBoundChangeNotifier(upperBoundChangeNotifier);

        StaffModeState staffModeState = new StaffModeStateImp();
        staffModeState.addConfigChangeNotifier(configChangeNotifier);

        NoteNameModeStateImp noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addConfigChangeNotifier(configChangeNotifier);

        ScoreImp scoreImp = new ScoreImp();
        scoreImp.addConfigChangeNotifier(configChangeNotifier);

        TrainerStateImp trainerStateImp = new TrainerStateImp();
        trainerStateImp.addConfigChangeNotifier(configChangeNotifier);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        NoteGeneratorImp noteGeneratorImp = new NoteGeneratorImp(lowerLimitImp, upperLimitImp);
        FlashcardsImp flashcardsImp = new FlashcardsImp(noteGeneratorImp);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifier);
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(keyboardStateImp, flashcardsImp, noteNameModeStateImp, scoreImp, trainerStateImp);
        sightReadTrainerImp.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifier);

        //Event Handlers
        ControlHandler controlHandler = new ControlHandlerImp(trainerStateImp, scoreImp);
        StaffModeHandler staffModeHandler = new StaffModeHandlerImp(staffModeState);

        //Selectors
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyNoteReceiverImp);
        JComponent trainerControl = new TrainerControlImp(controlHandler);
        JComponent staffModeSelector = new StaffModeSelectorImp(staffModeHandler, defaultStaffMode);
        NoteSelectorImp lowerNoteSelector = new NoteSelectorImp(lowerBoundedNoteLimitImp, lowerPreviewLimitImp);
        NoteSelectorImp upperNoteSelector = new NoteSelectorImp(upperBoundedNoteLimitImp, upperPreviewLimitImp);
        RangeSelectorImp rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);
        NoteNameModeSelectorImp noteNameModeSelectorImp = new NoteNameModeSelectorImp(noteNameModeStateImp);

        //Renderers
        GrandRendererImp grandStaffRenderer = new GrandRendererImp(keyboardStateImp, flashcardsImp, staffModeState, noteNameModeStateImp, scoreImp, trainerStateImp);
        RangeRendererImp rangeRenderer = new RangeRendererImp(limitRangeStateImp, previewRangeStateImp);
        NoteTextRenderer noteTextRenderer = new NoteTextRenderer(keyboardStateImp);


        //Add Observers
        keyboardChangeNotifier.addObserver(grandStaffRenderer);
        keyboardChangeNotifier.addObserver(noteTextRenderer);
        keyboardChangeNotifier.addObserver(sightReadTrainerImp);

        configChangeNotifier.addObserver(grandStaffRenderer);

        lowerLimitChangeNotifier.addObserver(rangeRenderer);
        lowerLimitChangeNotifier.addObserver(flashcardsImp);
        lowerLimitChangeNotifier.addObserver(lowerNoteSelector);
        lowerLimitChangeNotifier.addObserver(upperBoundedNoteLimitImp);
        lowerLimitChangeNotifier.addObserver(scoreImp);

        lowerBoundChangeNotifier.addObserver(lowerNoteSelector);

        upperLimitChangeNotifier.addObserver(rangeRenderer);
        upperLimitChangeNotifier.addObserver(flashcardsImp);
        upperLimitChangeNotifier.addObserver(upperNoteSelector);
        upperLimitChangeNotifier.addObserver(lowerBoundedNoteLimitImp);
        upperLimitChangeNotifier.addObserver(scoreImp);

        upperBoundChangeNotifier.addObserver(upperNoteSelector);

        previewLimitNotifier.addObserver(rangeRenderer);

        flashcardSatisfiedNotifier.addObserver(noteNameModeStateImp);

        flashcardChangeNotifier.addObserver(grandStaffRenderer);
        flashcardChangeNotifier.addObserver(noteNameModeStateImp);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowser.makeComponent());
        verticalPanel.add(trainerControl);
        verticalPanel.add(rangeSelector.makeComponent());
        verticalPanel.add(staffModeSelector);
        verticalPanel.add(noteNameModeSelectorImp.makeComponent());
        JPanel configPanel = new JPanel(new BorderLayout());
        configPanel.add(BorderLayout.NORTH, verticalPanel);

        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.add(BorderLayout.WEST, rangeRenderer.makeComponent());
        staffPanel.add(BorderLayout.CENTER, grandStaffRenderer.makeComponent());
        staffPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainPanel.add(BorderLayout.WEST, configPanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.add(BorderLayout.SOUTH, noteTextRenderer.makeComponent());

        return mainPanel;
    }
}
