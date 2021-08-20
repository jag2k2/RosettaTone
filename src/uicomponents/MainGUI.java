package uicomponents;

import javax.sound.midi.Receiver;
import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.note.Note;
import music.note.NoteName;
import notification.*;
import statemodels.*;
import statemodels.limitstate.*;
import trainer.FlashcardChangeNotifier;
import trainer.FlashcardSatisfiedNotifier;
import trainer.randomnotegenerator.NoteGeneratorImp;
import trainer.SightReadTrainerImp;
import uicomponents.browser.BrowserHandler;
import uicomponents.browser.eventhandler.BrowserHandlerImp;
import uicomponents.browser.eventhandler.MidiDeviceInquirer;
import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.NoteNameModeSelectorImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.noteselector.BoundSelectHandler;
import uicomponents.rangeselector.noteselector.NoteSelectorImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.rangeselector.noteselector.eventhandler.BoundSelectHandlerImp;
import uicomponents.renderer.grandstaff.GrandRendererImp;
import uicomponents.renderer.limit.RangeDrawable;
import uicomponents.renderer.text.NoteTextRendererImp;
import uicomponents.renderer.limit.RangeRendererImp;
import uicomponents.util.ModeHandler;
import uicomponents.staffmode.StaffModeSelectorImp;
import statemodels.StaffMode;
import uicomponents.util.ModeHandlerImp;
import uicomponents.trainer.ControlHandler;
import uicomponents.trainer.TrainerControlImp;
import uicomponents.trainer.eventhandler.ControlHandlerImp;

public class MainGUI extends JComponent{

    public MainGUI(){
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
        Note lowerBoundNote = new Note(NoteName.A, 0);
        Note defaultLowerLimitNote = new Note(NoteName.C, 4);
        Note defaultUpperLimitNote = new Note(NoteName.C, 5);
        Note upperBoundNote = new Note(NoteName.C, 8);

        StaffMode defaultStaffMode = StaffMode.Grand;
        NoteNameMode defaultNoteName = NoteNameMode.Off;

        LimitState lowerLimit = new LimitStateImp(defaultLowerLimitNote);
        lowerLimit.addLimitChangeNotifier(lowerLimitChangeNotifier);

        LimitState upperLimit = new LimitStateImp(defaultUpperLimitNote);
        upperLimit.addLimitChangeNotifier(upperLimitChangeNotifier);

        LimitState lowerPreviewLimit = new LimitStateImp(defaultLowerLimitNote);
        lowerPreviewLimit.addLimitChangeNotifier(previewLimitNotifier);

        LimitState upperPreviewLimit = new LimitStateImp(defaultUpperLimitNote);
        upperPreviewLimit.addLimitChangeNotifier(previewLimitNotifier);

        RangeDrawable limitRange = new RangeStateImp(lowerLimit, upperLimit);
        RangeDrawable previewRange = new RangeStateImp(lowerPreviewLimit, upperPreviewLimit);

        AbstractBoundedLimit lowerBoundedLimit = new LowerBoundedLimitStateImp(lowerBoundNote, lowerLimit, defaultUpperLimitNote);
        lowerBoundedLimit.addBoundChangeNotifier(lowerBoundChangeNotifier);
        lowerBoundedLimit.setOtherLimit(upperLimit);

        AbstractBoundedLimit upperBoundedLimit = new UpperBoundedLimitStateImp(defaultLowerLimitNote, upperLimit, upperBoundNote);
        upperBoundedLimit.addBoundChangeNotifier(upperBoundChangeNotifier);
        upperBoundedLimit.setOtherLimit(lowerLimit);

        StaffModeState staffMode = new StaffModeStateImp(defaultStaffMode);
        staffMode.addConfigChangeNotifier(configChangeNotifier);

        NoteNameModeState noteNameModeState = new NoteNameModeStateImp(defaultNoteName);
        noteNameModeState.addConfigChangeNotifier(configChangeNotifier);

        KeyboardState keyboardState = new KeyboardStateImp();
        keyboardState.addKeyboardChangeNotifier(keyboardChangeNotifier);

        ScoreKeepable score = new ScoreImp();
        score.addConfigChangeNotifier(configChangeNotifier);

        TrainerState trainerState = new TrainerStateImp();
        trainerState.addConfigChangeNotifier(configChangeNotifier);

        //Midi
        Receiver midiReceiver = new MidiReceiverImp(keyboardState);
        MidiDeviceInquirer midiDeviceInquirer = new MidiDeviceInquirerImp(this);

        //Trainer
        RandomNoteGenerator noteGenerator = new NoteGeneratorImp(lowerLimit, upperLimit);
        FlashcardState flashcardState = new FlashcardsImp(noteGenerator);
        flashcardState.addFlashcardChangeNotifier(flashcardChangeNotifier);
        SightReadTrainer sightReadTrainer = new SightReadTrainerImp(keyboardState, flashcardState, noteNameModeState, score, trainerState);
        sightReadTrainer.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifier);

        //UI Event Handlers
        BrowserHandler browserHandler = new BrowserHandlerImp(midiReceiver, midiDeviceInquirer);
        ControlHandler controlHandler = new ControlHandlerImp(trainerState, score);
        ModeHandler<StaffMode> staffModeHandler = new ModeHandlerImp<>(staffMode);
        ModeHandler<NoteNameMode> noteNameModeHandler = new ModeHandlerImp<>(noteNameModeState);
        BoundSelectHandler lowerBoundHandler = new BoundSelectHandlerImp(lowerBoundedLimit, lowerPreviewLimit);
        BoundSelectHandler upperBoundHandler = new BoundSelectHandlerImp(upperBoundedLimit, upperPreviewLimit);

        //Selectors
        JComponent instrumentBrowser = new InstrumentBrowserImp(browserHandler);
        JComponent trainerControl = new TrainerControlImp(controlHandler);
        JComponent staffModeSelector = new StaffModeSelectorImp(staffModeHandler);
        JComponent noteNameModeSelector = new NoteNameModeSelectorImp(noteNameModeHandler);
        JComponent lowerNoteSelector = new NoteSelectorImp(lowerBoundHandler, lowerPreviewLimit);
        JComponent upperNoteSelector = new NoteSelectorImp(upperBoundHandler, upperPreviewLimit);
        JComponent rangeSelector = new RangeSelectorImp(lowerNoteSelector, upperNoteSelector);

        //Renderers
        JComponent grandStaffRenderer = new GrandRendererImp(keyboardState, flashcardState, staffMode, noteNameModeState, score, trainerState);
        JComponent rangeRenderer = new RangeRendererImp(limitRange, previewRange);
        JComponent noteTextRendererImp = new NoteTextRendererImp(keyboardState);

        //Observers
        UIChangeObserver uiChangeObserver = new UIChangeObserverImp(noteTextRendererImp, grandStaffRenderer, rangeRenderer);
        FlashcardChangeObserver flashcardChangeObserver = new FlashCardChangeObserverImp(flashcardState, grandStaffRenderer);

        //Add Observers
        keyboardChangeNotifier.addObserver(uiChangeObserver);
        keyboardChangeNotifier.addObserver(sightReadTrainer);

        configChangeNotifier.addObserver(uiChangeObserver);

        lowerLimitChangeNotifier.addObserver(uiChangeObserver);
        lowerLimitChangeNotifier.addObserver(flashcardState);
        lowerLimitChangeNotifier.addObserver(lowerBoundHandler);
        lowerLimitChangeNotifier.addObserver(upperBoundedLimit);
        lowerLimitChangeNotifier.addObserver(score);

        lowerBoundChangeNotifier.addObserver(lowerBoundHandler);

        upperLimitChangeNotifier.addObserver(uiChangeObserver);
        upperLimitChangeNotifier.addObserver(flashcardState);
        upperLimitChangeNotifier.addObserver(upperBoundHandler);
        upperLimitChangeNotifier.addObserver(lowerBoundedLimit);
        upperLimitChangeNotifier.addObserver(score);

        upperBoundChangeNotifier.addObserver(upperBoundHandler);

        previewLimitNotifier.addObserver(uiChangeObserver);

        flashcardSatisfiedNotifier.addObserver(noteNameModeState);

        flashcardChangeNotifier.addObserver(flashcardChangeObserver);
        flashcardChangeNotifier.addObserver(noteNameModeState);

        //Build Panels
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(instrumentBrowser);
        verticalPanel.add(trainerControl);
        verticalPanel.add(rangeSelector);
        verticalPanel.add(staffModeSelector);
        verticalPanel.add(noteNameModeSelector);
        JPanel configPanel = new JPanel(new BorderLayout());
        configPanel.add(BorderLayout.NORTH, verticalPanel);

        JPanel staffPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(staffPanel, BoxLayout.X_AXIS);
        staffPanel.setLayout(boxLayout);
        staffPanel.setBackground(Color.WHITE);
        staffPanel.add(grandStaffRenderer);

        JPanel rangePanel = new JPanel();
        rangePanel.setBackground(Color.WHITE);
        rangePanel.add(rangeRenderer);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.WEST, rangePanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.WEST, configPanel);
        this.add(BorderLayout.CENTER, mainPanel);
        this.add(BorderLayout.SOUTH, noteTextRendererImp);
    }
}
