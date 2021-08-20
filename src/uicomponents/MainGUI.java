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
        NoteGeneratorImp noteGeneratorImp = new NoteGeneratorImp(lowerLimit, upperLimit);
        FlashcardsImp flashcardsImp = new FlashcardsImp(noteGeneratorImp);
        flashcardsImp.addFlashcardChangeNotifier(flashcardChangeNotifier);
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(keyboardState, flashcardsImp, noteNameModeState, score, trainerState);
        sightReadTrainerImp.addFlashcardSatisfiedNotifier(flashcardSatisfiedNotifier);

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
        GrandRendererImp grandStaffRenderer = new GrandRendererImp(keyboardState, flashcardsImp, staffMode, noteNameModeState, score, trainerState);
        RangeRendererImp rangeRenderer = new RangeRendererImp(limitRange, previewRange);
        NoteTextRendererImp noteTextRendererImp = new NoteTextRendererImp(keyboardState);

        //Add Observers
        keyboardChangeNotifier.addObserver(grandStaffRenderer);
        keyboardChangeNotifier.addObserver(noteTextRendererImp);
        keyboardChangeNotifier.addObserver(sightReadTrainerImp);

        configChangeNotifier.addObserver(grandStaffRenderer);

        lowerLimitChangeNotifier.addObserver(rangeRenderer);
        lowerLimitChangeNotifier.addObserver(flashcardsImp);
        lowerLimitChangeNotifier.addObserver(lowerBoundHandler);
        lowerLimitChangeNotifier.addObserver(upperBoundedLimit);
        lowerLimitChangeNotifier.addObserver(score);

        lowerBoundChangeNotifier.addObserver(lowerBoundHandler);

        upperLimitChangeNotifier.addObserver(rangeRenderer);
        upperLimitChangeNotifier.addObserver(flashcardsImp);
        upperLimitChangeNotifier.addObserver(upperBoundHandler);
        upperLimitChangeNotifier.addObserver(lowerBoundedLimit);
        upperLimitChangeNotifier.addObserver(score);

        upperBoundChangeNotifier.addObserver(upperBoundHandler);

        previewLimitNotifier.addObserver(rangeRenderer);

        flashcardSatisfiedNotifier.addObserver(noteNameModeState);

        flashcardChangeNotifier.addObserver(grandStaffRenderer);
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

        JPanel staffPanel = new JPanel(new BorderLayout());
        staffPanel.add(BorderLayout.WEST, rangeRenderer.makeComponent());
        staffPanel.add(BorderLayout.CENTER, grandStaffRenderer.makeComponent());
        staffPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.setLayout(new BorderLayout());
        this.add(BorderLayout.WEST, configPanel);
        this.add(BorderLayout.CENTER, staffPanel);
        this.add(BorderLayout.SOUTH, noteTextRendererImp.makeComponent());
    }
}
