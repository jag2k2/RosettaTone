package uicomponents;

import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.*;
import notification.*;
import statemodels.*;
import trainer.randomnotegenerator.RandomNoteGeneratorImp;
import statemodels.limitstate.LowerBoundedLimitStateImp;
import statemodels.limitstate.LimitStateImp;
import statemodels.limitstate.UpperBoundedLimitStateImp;
import trainer.SightReadTrainerImp;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.NoteNameModeSelectorImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.noteselector.NoteSelectorImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import uicomponents.renderer.NoteTextRenderer;
import uicomponents.renderer.LimitRendererImp;
import uicomponents.staffmode.StaffModeSelectorImp;
import uicomponents.staffmode.StaffMode;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifierImp keyboardChangeNotifierImp = new KeyboardChangeNotifierImp();
        StaffModeChangeNotifierImp staffModeChangeNotifierImp = new StaffModeChangeNotifierImp();
        NoteNameModeChangeNotifierImp alphabetModeChangeNotifierImp = new NoteNameModeChangeNotifierImp();
        LimitChangeNotifierImp lowerLimitChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperLimitChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp lowerBoundChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperBoundChangeNotifierImp = new LimitChangeNotifierImp();
        LimitChangeNotifierImp limitPreviewNotifierImp = new LimitChangeNotifierImp();
        FlashcardSatisfiedNotifierImp flashcardSatisfiedNotifierImp = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifierImp flashcardChangeNotifierImp = new FlashcardChangeNotifierImp();

        //State Models
        KeyboardStateImp keyboardStateImp = new KeyboardStateImp();
        keyboardStateImp.addKeyboardChangeNotifier(keyboardChangeNotifierImp);

        Note lowerBoundNote = new Note(NoteName.A, 0);
        Note upperBoundNote = new Note(NoteName.C, 8);
        Note defaultLowerLimitNote = new Note(NoteName.C, 4);
        Note defaultUpperLimitNote = new Note(NoteName.C, 5);

        LimitStateImp lowerNoteLimitImp = new LimitStateImp(defaultLowerLimitNote);
        lowerNoteLimitImp.addLimitChangeNotifier(lowerLimitChangeNotifierImp);
        lowerNoteLimitImp.addPreviewChangeNotifier(limitPreviewNotifierImp);

        LimitStateImp upperNoteLimitImp = new LimitStateImp(defaultUpperLimitNote);
        upperNoteLimitImp.addLimitChangeNotifier(upperLimitChangeNotifierImp);
        upperNoteLimitImp.addPreviewChangeNotifier(limitPreviewNotifierImp);

        LowerBoundedLimitStateImp lowerBoundedNoteLimit = new LowerBoundedLimitStateImp(lowerNoteLimitImp, upperNoteLimitImp, lowerBoundNote, defaultUpperLimitNote);
        lowerBoundedNoteLimit.addBoundChangeNotifier(lowerBoundChangeNotifierImp);
        UpperBoundedLimitStateImp upperBoundedNoteLimit = new UpperBoundedLimitStateImp(upperNoteLimitImp, lowerNoteLimitImp, defaultLowerLimitNote, upperBoundNote);
        upperBoundedNoteLimit.addBoundChangeNotifier(upperBoundChangeNotifierImp);

        StaffModeStateImp staffModeStateImp = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.addStaffModeChangeNotifier(staffModeChangeNotifierImp);

        NoteNameModeStateImp noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addAlphabetModeChangeNotifier(alphabetModeChangeNotifierImp);

        ScoreImp scoreImp = new ScoreImp();

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        RandomNoteGeneratorImp randomNoteGeneratorImp = new RandomNoteGeneratorImp(lowerNoteLimitImp, upperNoteLimitImp);
        FlashcardsImp flashcardsImp = new FlashcardsImp(randomNoteGeneratorImp);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifierImp);
        SightReadTrainerImp sightReadTrainer = new SightReadTrainerImp(keyboardStateImp, flashcardsImp, noteNameModeStateImp, scoreImp);
        sightReadTrainer.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifierImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyNoteReceiverImp);
        StaffModeSelectorImp modeSelector = new StaffModeSelectorImp(staffModeStateImp);
        NoteSelectorImp lowerNoteSelector = new NoteSelectorImp(lowerBoundedNoteLimit, lowerNoteLimitImp);
        NoteSelectorImp upperNoteSelector = new NoteSelectorImp(upperBoundedNoteLimit, upperNoteLimitImp);
        RangeSelectorImp rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);
        NoteNameModeSelectorImp noteNameModeSelectorImp = new NoteNameModeSelectorImp(noteNameModeStateImp);

        //Renderers
        GrandStaffRendererImp grandStaffRenderer = new GrandStaffRendererImp(keyboardStateImp, flashcardsImp, staffModeStateImp, noteNameModeStateImp, scoreImp);
        LimitRendererImp rangeRenderer = new LimitRendererImp(lowerNoteLimitImp, upperNoteLimitImp);
        NoteTextRenderer noteTextRenderer = new NoteTextRenderer(keyboardStateImp);


        //Add Observers
        keyboardChangeNotifierImp.addObserver(grandStaffRenderer);
        keyboardChangeNotifierImp.addObserver(noteTextRenderer);
        keyboardChangeNotifierImp.addObserver(sightReadTrainer);

        staffModeChangeNotifierImp.addObserver(grandStaffRenderer);
        alphabetModeChangeNotifierImp.addObserver(grandStaffRenderer);

        lowerLimitChangeNotifierImp.addObserver(rangeRenderer);
        lowerLimitChangeNotifierImp.addObserver(flashcardsImp);
        lowerLimitChangeNotifierImp.addObserver(lowerNoteSelector);
        lowerLimitChangeNotifierImp.addObserver(upperBoundedNoteLimit);
        lowerLimitChangeNotifierImp.addObserver(scoreImp);

        lowerBoundChangeNotifierImp.addObserver(lowerNoteSelector);

        upperLimitChangeNotifierImp.addObserver(rangeRenderer);
        upperLimitChangeNotifierImp.addObserver(flashcardsImp);
        upperLimitChangeNotifierImp.addObserver(upperNoteSelector);
        upperLimitChangeNotifierImp.addObserver(lowerBoundedNoteLimit);
        upperLimitChangeNotifierImp.addObserver(scoreImp);

        upperBoundChangeNotifierImp.addObserver(upperNoteSelector);

        limitPreviewNotifierImp.addObserver(rangeRenderer);
        limitPreviewNotifierImp.addObserver(flashcardsImp);

        flashcardSatisfiedNotifierImp.addObserver(noteNameModeStateImp);

        flashcardChangeNotifierImp.addObserver(grandStaffRenderer);
        flashcardChangeNotifierImp.addObserver(noteNameModeStateImp);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowser.getComponent());
        verticalPanel.add(rangeSelector.getComponent());
        verticalPanel.add(modeSelector.getComponent());
        verticalPanel.add(noteNameModeSelectorImp.getComponent());
        JPanel configPanel = new JPanel(new BorderLayout());
        configPanel.add(BorderLayout.NORTH, verticalPanel);

        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.add(BorderLayout.WEST, rangeRenderer.getComponent());
        staffPanel.add(BorderLayout.CENTER, grandStaffRenderer.getComponent());
        staffPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainPanel.add(BorderLayout.WEST, configPanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.add(BorderLayout.SOUTH, noteTextRenderer.getComponent());
        frame.setTitle("Rosetta Tone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocation(10, 10);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.pack();
    }

    public void launch(){
        frame.setVisible(true);
    }
}
