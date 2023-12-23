package com.jag2k2.uicomponents;

import javax.swing.*;
import java.awt.*;

import com.jag2k2.instrument.*;
import com.jag2k2.music.*;
import com.jag2k2.notification.*;
import com.jag2k2.statemodels.*;
import com.jag2k2.trainer.randomnotegenerator.RandomNoteGeneratorImp;
import com.jag2k2.statemodels.limitstate.LowerBoundedLimitStateImp;
import com.jag2k2.statemodels.limitstate.LimitStateImp;
import com.jag2k2.statemodels.limitstate.UpperBoundedLimitStateImp;
import com.jag2k2.trainer.SightReadTrainerImp;
import com.jag2k2.uicomponents.notenamemode.NoteNameMode;
import com.jag2k2.uicomponents.notenamemode.NoteNameModeSelectorImp;
import com.jag2k2.uicomponents.browser.InstrumentBrowserImp;
import com.jag2k2.uicomponents.rangeselector.noteselector.NoteListRenderer;
import com.jag2k2.uicomponents.rangeselector.noteselector.NoteSelectorImp;
import com.jag2k2.uicomponents.rangeselector.RangeSelectorImp;
import com.jag2k2.uicomponents.renderer.grandstaff.GrandRendererImp;
import com.jag2k2.uicomponents.renderer.text.NoteTextRenderer;
import com.jag2k2.uicomponents.renderer.limit.RangeRendererImp;
import com.jag2k2.uicomponents.staffmode.StaffModeSelectorImp;
import com.jag2k2.uicomponents.staffmode.StaffMode;
import com.jag2k2.uicomponents.trainer.TrainerControlImp;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
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

        StaffModeStateImp staffModeStateImp = new StaffModeStateImp(StaffMode.Grand);
        staffModeStateImp.addConfigChangeNotifier(configChangeNotifierImp);

        NoteNameModeStateImp noteNameModeStateImp = new NoteNameModeStateImp(NoteNameMode.Off);
        noteNameModeStateImp.addConfigChangeNotifier(configChangeNotifierImp);

        ScoreImp scoreImp = new ScoreImp();
        scoreImp.addConfigChangeNotifier(configChangeNotifierImp);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        RandomNoteGeneratorImp randomNoteGeneratorImp = new RandomNoteGeneratorImp(lowerLimitImp, upperLimitImp);
        FlashcardsImp flashcardsImp = new FlashcardsImp(randomNoteGeneratorImp);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifierImp);
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(keyboardStateImp, flashcardsImp, noteNameModeStateImp, scoreImp);
        sightReadTrainerImp.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifierImp);
        sightReadTrainerImp.addConfigChangeNotifier(configChangeNotifierImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyNoteReceiverImp);
        TrainerControlImp trainerControlImp = new TrainerControlImp(sightReadTrainerImp, scoreImp);
        StaffModeSelectorImp modeSelector = new StaffModeSelectorImp(staffModeStateImp);
        NoteListRenderer lowerListRenderer = new NoteListRenderer(new DefaultListCellRenderer(), lowerPreviewLimitImp);
        NoteSelectorImp lowerNoteSelector = new NoteSelectorImp(lowerBoundedNoteLimitImp, lowerListRenderer, lowerPreviewLimitImp);
        NoteListRenderer upperListRenderer = new NoteListRenderer(new DefaultListCellRenderer(), upperPreviewLimitImp);
        NoteSelectorImp upperNoteSelector = new NoteSelectorImp(upperBoundedNoteLimitImp, upperListRenderer, upperPreviewLimitImp);
        RangeSelectorImp rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);
        NoteNameModeSelectorImp noteNameModeSelectorImp = new NoteNameModeSelectorImp(noteNameModeStateImp);

        //Renderers
        GrandRendererImp grandStaffRenderer = new GrandRendererImp(keyboardStateImp, flashcardsImp, staffModeStateImp, noteNameModeStateImp, scoreImp, sightReadTrainerImp);
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
        verticalPanel.add(instrumentBrowser.getComponent());
        verticalPanel.add(trainerControlImp.getComponent());
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
