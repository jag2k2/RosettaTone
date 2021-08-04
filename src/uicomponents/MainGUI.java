package uicomponents;

import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.note.Note;
import music.note.NoteName;
import notification.*;
import statemodels.*;
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
import uicomponents.staffmode.StaffModeSelectorImp;
import uicomponents.staffmode.StaffMode;
import uicomponents.trainer.GameController;
import uicomponents.trainer.TrainerControlImp;
import uicomponents.trainer.statechangers.GameControllerImp;

public class MainGUI {

    public Container makePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifierImp keyboardChangeNotifierImp = new KeyboardChangeNotifierImp();
        ConfigChangeNotifierImp configChangeNotifierImp = new ConfigChangeNotifierImp();
        LimitChangeNotifierImp lowerLimitChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperLimitChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp lowerBoundChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperBoundChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp previewLimitNotifierImp = new LimitChangeNotifierImp();
        FlashcardSatisfiedNotifierImp flashcardSatisfiedNotifierImp = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifierImp flashcardChangeNotifierImp = new FlashcardChangeNotifierImp();

        //State Models
        KeyboardStateImp keyboardStateImp = new KeyboardStateImp();
        keyboardStateImp.addKeyboardChangeNotifier(keyboardChangeNotifierImp);

        Note lowerBoundNote = new Note(NoteName.A, 0);
        Note upperBoundNote = new Note(NoteName.C, 8);
        Note defaultLowerLimitNote = new Note(NoteName.C, 4);
        Note defaultUpperLimitNote = new Note(NoteName.C, 5);
        StaffMode defaultStaffMode = StaffMode.Grand;

        LimitStateImp lowerLimitImp = new LimitStateImp(defaultLowerLimitNote);
        lowerLimitImp.addLimitChangeNotifier(lowerLimitChangeNotifierImp);

        LimitStateImp upperLimitImp = new LimitStateImp(defaultUpperLimitNote);
        upperLimitImp.addLimitChangeNotifier(upperLimitChangeNotifierImp);

        LimitStateImp lowerPreviewLimitImp = new LimitStateImp(defaultLowerLimitNote);
        lowerPreviewLimitImp.addLimitChangeNotifier(previewLimitNotifierImp);

        LimitStateImp upperPreviewLimitImp = new LimitStateImp(defaultUpperLimitNote);
        upperPreviewLimitImp.addLimitChangeNotifier(previewLimitNotifierImp);

        RangeStateImp limitRangeStateImp = new RangeStateImp(lowerLimitImp, upperLimitImp);
        RangeStateImp previewRangeStateImp = new RangeStateImp(lowerPreviewLimitImp, upperPreviewLimitImp);

        LowerBoundedLimitStateImp lowerBoundedNoteLimitImp = new LowerBoundedLimitStateImp(lowerLimitImp, upperLimitImp, lowerBoundNote, defaultUpperLimitNote);
        lowerBoundedNoteLimitImp.addBoundChangeNotifier(lowerBoundChangeNotifierImp);
        UpperBoundedLimitStateImp upperBoundedNoteLimitImp = new UpperBoundedLimitStateImp(upperLimitImp, lowerLimitImp, defaultLowerLimitNote, upperBoundNote);
        upperBoundedNoteLimitImp.addBoundChangeNotifier(upperBoundChangeNotifierImp);

        StaffModeStateImp staffModeStateImp = new StaffModeStateImp();
        staffModeStateImp.addConfigChangeNotifier(configChangeNotifierImp);

        NoteNameModeStateImp noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addConfigChangeNotifier(configChangeNotifierImp);

        ScoreImp scoreImp = new ScoreImp();
        scoreImp.addConfigChangeNotifier(configChangeNotifierImp);

        TrainerStateImp trainerStateImp = new TrainerStateImp();
        trainerStateImp.addConfigChangeNotifier(configChangeNotifierImp);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        NoteGeneratorImp noteGeneratorImp = new NoteGeneratorImp(lowerLimitImp, upperLimitImp);
        FlashcardsImp flashcardsImp = new FlashcardsImp(noteGeneratorImp);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifierImp);
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(keyboardStateImp, flashcardsImp, noteNameModeStateImp, scoreImp, trainerStateImp);
        sightReadTrainerImp.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifierImp);
        GameController gameController = new GameControllerImp(trainerStateImp, scoreImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyNoteReceiverImp);
        TrainerControlImp trainerControl = new TrainerControlImp(gameController);
        StaffModeSelectorImp staffModeSelector = new StaffModeSelectorImp(staffModeStateImp, defaultStaffMode);
        NoteSelectorImp lowerNoteSelector = new NoteSelectorImp(lowerBoundedNoteLimitImp, lowerPreviewLimitImp);
        NoteSelectorImp upperNoteSelector = new NoteSelectorImp(upperBoundedNoteLimitImp, upperPreviewLimitImp);
        RangeSelectorImp rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);
        NoteNameModeSelectorImp noteNameModeSelectorImp = new NoteNameModeSelectorImp(noteNameModeStateImp);

        //Renderers
        GrandRendererImp grandStaffRenderer = new GrandRendererImp(keyboardStateImp, flashcardsImp, staffModeStateImp, noteNameModeStateImp, scoreImp, trainerStateImp);
        RangeRendererImp rangeRenderer = new RangeRendererImp(limitRangeStateImp, previewRangeStateImp);
        NoteTextRenderer noteTextRenderer = new NoteTextRenderer(keyboardStateImp);


        //Add Observers
        keyboardChangeNotifierImp.addObserver(grandStaffRenderer);
        keyboardChangeNotifierImp.addObserver(noteTextRenderer);
        keyboardChangeNotifierImp.addObserver(sightReadTrainerImp);

        configChangeNotifierImp.addObserver(grandStaffRenderer);

        lowerLimitChangeNotifierImp.addObserver(rangeRenderer);
        lowerLimitChangeNotifierImp.addObserver(flashcardsImp);
        lowerLimitChangeNotifierImp.addObserver(lowerNoteSelector);
        lowerLimitChangeNotifierImp.addObserver(upperBoundedNoteLimitImp);
        lowerLimitChangeNotifierImp.addObserver(scoreImp);

        lowerBoundChangeNotifierImp.addObserver(lowerNoteSelector);

        upperLimitChangeNotifierImp.addObserver(rangeRenderer);
        upperLimitChangeNotifierImp.addObserver(flashcardsImp);
        upperLimitChangeNotifierImp.addObserver(upperNoteSelector);
        upperLimitChangeNotifierImp.addObserver(lowerBoundedNoteLimitImp);
        upperLimitChangeNotifierImp.addObserver(scoreImp);

        upperBoundChangeNotifierImp.addObserver(upperNoteSelector);

        previewLimitNotifierImp.addObserver(rangeRenderer);

        flashcardSatisfiedNotifierImp.addObserver(noteNameModeStateImp);

        flashcardChangeNotifierImp.addObserver(grandStaffRenderer);
        flashcardChangeNotifierImp.addObserver(noteNameModeStateImp);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowser.makeComponent());
        verticalPanel.add(trainerControl);
        verticalPanel.add(rangeSelector.makeComponent());
        verticalPanel.add(staffModeSelector.makeComponent());
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
