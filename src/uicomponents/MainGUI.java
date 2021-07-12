package uicomponents;

import javax.swing.*;
import java.awt.*;

import imageprocessing.ImageLoaderImp;
import instrument.*;
import music.*;
import notification.*;
import statemodels.ClefModeModifierImp;
import statemodels.KeyStateManipulatorImp;
import statemodels.NoteRangeLimitsImp;
import statemodels.notelimit.LowerBoundedNoteModifierImp;
import statemodels.notelimit.NoteModifierImp;
import statemodels.notelimit.UpperBoundedNoteModifierImp;
import trainer.SightReadTrainerImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.noteselector.NoteSelectorImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import uicomponents.renderer.NoteTextRenderer;
import uicomponents.renderer.LimitRendererImp;
import uicomponents.clefmode.ClefModeSelectorImp;
import uicomponents.clefmode.ClefMode;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifierImp keyboardChangeNotifier = new KeyboardChangeNotifierImp();
        ClefModeChangeNotifierImp modeChangeNotifier = new ClefModeChangeNotifierImp();
        LimitChangeNotifierImp lowerLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperLimitChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifierImp lowerBoundChangeNotifier = new LimitChangeNotifierImp();
        LimitChangeNotifierImp upperBoundChangeNotifier = new LimitChangeNotifierImp();
        FlashcardSatisfiedNotifierImp flashcardSatisfiedNotifier = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifierImp flashcardChangeNotifier = new FlashcardChangeNotifierImp();

        //State Models
        KeyStateManipulatorImp keyboardState = new KeyStateManipulatorImp(keyboardChangeNotifier);
        Note lowerBoundNote = new Note(NoteName.A, 0);
        Note upperBoundNote = new Note(NoteName.C, 8);
        Note defaultLowerLimitNote = new Note(NoteName.C, 4);
        Note defaultUpperLimitNote = new Note(NoteName.C, 5);
        NoteModifierImp lowerNoteLimit = new NoteModifierImp(defaultLowerLimitNote, lowerLimitChangeNotifier);
        NoteModifierImp upperNoteLimit = new NoteModifierImp(defaultUpperLimitNote, upperLimitChangeNotifier);
        LowerBoundedNoteModifierImp lowerBoundedNoteLimit = new LowerBoundedNoteModifierImp(lowerNoteLimit, upperNoteLimit, lowerBoundNote, defaultUpperLimitNote, lowerBoundChangeNotifier);
        UpperBoundedNoteModifierImp upperBoundedNoteLimit = new UpperBoundedNoteModifierImp(upperNoteLimit, lowerNoteLimit, defaultLowerLimitNote, upperBoundNote, upperBoundChangeNotifier);

        NoteRangeLimitsImp noteRangeLimits = new NoteRangeLimitsImp(lowerNoteLimit, upperNoteLimit);
        ClefModeModifierImp clefModeState = new ClefModeModifierImp(ClefMode.Grand, modeChangeNotifier);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiver = new KeyNoteReceiverImp(keyboardState);

        //Trainer
        SightReadTrainerImp sightReadTrainer = new SightReadTrainerImp(noteRangeLimits, keyboardState, flashcardSatisfiedNotifier, flashcardChangeNotifier);

        //Selectors
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyNoteReceiver);
        ClefModeSelectorImp modeSelector = new ClefModeSelectorImp(clefModeState);
        NoteSelectorImp lowerNoteSelector = new NoteSelectorImp(lowerBoundedNoteLimit, lowerNoteLimit);
        NoteSelectorImp upperNoteSelector = new NoteSelectorImp(upperBoundedNoteLimit, upperNoteLimit);
        RangeSelectorImp rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);

        //Renderers
        ImageLoaderImp imageLoader = new ImageLoaderImp();
        GrandStaffRendererImp grandStaffRenderer = new GrandStaffRendererImp(keyboardState, clefModeState, sightReadTrainer, imageLoader);
        LimitRendererImp rangeRenderer = new LimitRendererImp(noteRangeLimits);
        NoteTextRenderer noteTextRenderer = new NoteTextRenderer(keyboardState);

        //Add Observers
        keyboardChangeNotifier.addObserver(grandStaffRenderer);
        keyboardChangeNotifier.addObserver(noteTextRenderer);
        keyboardChangeNotifier.addObserver(sightReadTrainer);

        modeChangeNotifier.addObserver(grandStaffRenderer);

        lowerLimitChangeNotifier.addObserver(rangeRenderer);
        lowerLimitChangeNotifier.addObserver(sightReadTrainer);
        lowerLimitChangeNotifier.addObserver(lowerNoteSelector);
        lowerLimitChangeNotifier.addObserver(upperBoundedNoteLimit);

        lowerBoundChangeNotifier.addObserver(lowerNoteSelector);

        upperLimitChangeNotifier.addObserver(rangeRenderer);
        upperLimitChangeNotifier.addObserver(sightReadTrainer);
        upperLimitChangeNotifier.addObserver(upperNoteSelector);
        upperLimitChangeNotifier.addObserver(lowerBoundedNoteLimit);

        upperBoundChangeNotifier.addObserver(upperNoteSelector);

        flashcardSatisfiedNotifier.addObserver(grandStaffRenderer);
        flashcardChangeNotifier.addObserver(grandStaffRenderer);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowser.getComponent());
        verticalPanel.add(rangeSelector.getComponent());
        verticalPanel.add(modeSelector.getComponent());
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
        frame.setLocation(300, 100);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.pack();
    }

    public void launch(){
        frame.setVisible(true);
    }
}
