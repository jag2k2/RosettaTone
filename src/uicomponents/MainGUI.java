package uicomponents;

import javax.swing.*;
import java.awt.*;
import instrument.*;
import music.*;
import notification.FlashcardChangeNotifierImp;
import notification.KeyboardChangeNotifierImp;
import notification.ModeChangeNotifierImp;
import notification.RangeChangeNotifierImp;
import statemodels.KeyboardStateImp;
import trainer.SightReadTrainerImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import statemodels.NoteLimitModelImp;
import uicomponents.renderer.NoteTextRenderer;
import uicomponents.renderer.RangeRendererImp;
import uicomponents.staffselector.ModeSelectorImp;
import uicomponents.staffselector.StaffMode;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Notifiers
        KeyboardChangeNotifierImp keyboardChangeNotifierImp = new KeyboardChangeNotifierImp();
        ModeChangeNotifierImp modeChangeNotifierImp = new ModeChangeNotifierImp();
        RangeChangeNotifierImp rangeChangeNotifierImp = new RangeChangeNotifierImp();
        FlashcardChangeNotifierImp flashcardChangeNotifierImp = new FlashcardChangeNotifierImp();

        //State Models
        KeyboardStateImp keyboardStateImp = new KeyboardStateImp(keyboardChangeNotifierImp);
        NoteLimitModelImp lowerNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.C, 4, NoteAccidental.NATURAL), rangeChangeNotifierImp);
        NoteLimitModelImp upperNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.B, 4, NoteAccidental.NATURAL), rangeChangeNotifierImp);

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(lowerNoteLimitModelImp, upperNoteLimitModelImp, keyboardStateImp, flashcardChangeNotifierImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowserImp = new InstrumentBrowserImp(keyNoteReceiverImp);
        ModeSelectorImp modeSelectorImp = new ModeSelectorImp(StaffMode.Grand, modeChangeNotifierImp);
        RangeSelectorImp rangeSelectorImp = new RangeSelectorImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);

        //Renderers
        GrandStaffRendererImp grandStaffRendererImp = new GrandStaffRendererImp(keyboardStateImp, modeSelectorImp, sightReadTrainerImp);
        RangeRendererImp rangeRendererImp = new RangeRendererImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);
        NoteTextRenderer noteTextRendererImp = new NoteTextRenderer(keyboardStateImp);

        //Add Observers
        keyboardChangeNotifierImp.addObserver(grandStaffRendererImp);
        keyboardChangeNotifierImp.addObserver(noteTextRendererImp);
        keyboardChangeNotifierImp.addObserver(sightReadTrainerImp);

        modeChangeNotifierImp.addObserver(grandStaffRendererImp);

        rangeChangeNotifierImp.addObserver(rangeRendererImp);
        rangeChangeNotifierImp.addObserver(sightReadTrainerImp);

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
