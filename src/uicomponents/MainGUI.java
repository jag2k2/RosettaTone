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
import trainer.SightReadTrainerImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.noteselector.NoteSelectorImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import statemodels.NoteLimitModelImp;
import uicomponents.renderer.NoteTextRenderer;
import uicomponents.renderer.RangeRendererImp;
import uicomponents.clefmode.ClefModeSelectorImp;
import uicomponents.clefmode.ClefMode;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifierImp keyboardChangeNotifierImp = new KeyboardChangeNotifierImp();
        ClefModeChangeNotifierImp modeChangeNotifierImp = new ClefModeChangeNotifierImp();
        RangeChangeNotifierImp rangeChangeNotifierImp = new RangeChangeNotifierImp();
        FlashcardSatisfiedNotifierImp flashcardSatisfiedNotifierImp = new FlashcardSatisfiedNotifierImp();
        FlashcardChangeNotifierImp flashcardChangeNotifierImp = new FlashcardChangeNotifierImp();

        //State Models
        KeyStateManipulatorImp keyboardStateImp = new KeyStateManipulatorImp(keyboardChangeNotifierImp);
        Note defaultLowerRangeNote = new Note(NoteName.C, 4);
        Note defaultUpperRangeNote = new Note(NoteName.C, 5);
        NoteLimitModelImp lowerNoteLimitModelImp = new NoteLimitModelImp(defaultLowerRangeNote, rangeChangeNotifierImp);
        NoteLimitModelImp upperNoteLimitModelImp = new NoteLimitModelImp(defaultUpperRangeNote, rangeChangeNotifierImp);
        NoteRangeLimitsImp noteRangeLimitsImp = new NoteRangeLimitsImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);
        ClefModeModifierImp clefModeStateImp = new ClefModeModifierImp(ClefMode.Grand, modeChangeNotifierImp);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(noteRangeLimitsImp, keyboardStateImp, flashcardSatisfiedNotifierImp, flashcardChangeNotifierImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowserImp = new InstrumentBrowserImp(keyNoteReceiverImp);
        ClefModeSelectorImp modeSelectorImp = new ClefModeSelectorImp(clefModeStateImp);
        NoteSelectorImp lowerNoteSelectorImp = new NoteSelectorImp(lowerNoteLimitModelImp);
        NoteSelectorImp upperNoteSelectorImp = new NoteSelectorImp(upperNoteLimitModelImp);
        RangeSelectorImp rangeSelectorImp = new RangeSelectorImp(lowerNoteSelectorImp, upperNoteSelectorImp);

        //Renderers
        ImageLoaderImp imageLoaderImp = new ImageLoaderImp();
        GrandStaffRendererImp grandStaffRendererImp = new GrandStaffRendererImp(keyboardStateImp, clefModeStateImp, sightReadTrainerImp, imageLoaderImp);
        RangeRendererImp rangeRendererImp = new RangeRendererImp(noteRangeLimitsImp);
        NoteTextRenderer noteTextRendererImp = new NoteTextRenderer(keyboardStateImp);

        //Add Observers
        keyboardChangeNotifierImp.addObserver(grandStaffRendererImp);
        keyboardChangeNotifierImp.addObserver(noteTextRendererImp);
        keyboardChangeNotifierImp.addObserver(sightReadTrainerImp);

        modeChangeNotifierImp.addObserver(grandStaffRendererImp);

        rangeChangeNotifierImp.addObserver(rangeRendererImp);
        rangeChangeNotifierImp.addObserver(sightReadTrainerImp);

        flashcardSatisfiedNotifierImp.addObserver(grandStaffRendererImp);
        flashcardChangeNotifierImp.addObserver(grandStaffRendererImp);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowserImp.getComponent());
        verticalPanel.add(rangeSelectorImp.getComponent());
        verticalPanel.add(modeSelectorImp.getComponent());
        JPanel configPanel = new JPanel(new BorderLayout());
        configPanel.add(BorderLayout.NORTH, verticalPanel);

        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.add(BorderLayout.WEST, rangeRendererImp.getComponent());
        staffPanel.add(BorderLayout.CENTER, grandStaffRendererImp.getComponent());
        staffPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainPanel.add(BorderLayout.WEST, configPanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.add(BorderLayout.SOUTH, noteTextRendererImp.getComponent());
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
