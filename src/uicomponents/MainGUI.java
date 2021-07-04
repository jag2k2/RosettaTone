package uicomponents;

import javax.swing.*;
import java.awt.*;
import instrument.*;
import music.*;
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

        //State Models
        KeyboardStateImp keyboardStateImp = new KeyboardStateImp();
        NoteLimitModelImp lowerNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.C, 4, NoteAccidental.NATURAL));
        NoteLimitModelImp upperNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.B, 4, NoteAccidental.NATURAL));

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(keyboardStateImp);

        //Trainer
        SightReadTrainerImp sightReadTrainerImp = new SightReadTrainerImp(lowerNoteLimitModelImp, upperNoteLimitModelImp, keyboardStateImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowserImp = new InstrumentBrowserImp(keyNoteReceiverImp);
        ModeSelectorImp modeSelectorImp = new ModeSelectorImp(StaffMode.Grand);
        RangeSelectorImp rangeSelectorImp = new RangeSelectorImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);

        //Renderers
        GrandStaffRendererImp grandStaffRendererImp = new GrandStaffRendererImp(keyboardStateImp, modeSelectorImp, sightReadTrainerImp);
        RangeRendererImp rangeRendererImp = new RangeRendererImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);
        NoteTextRenderer noteTextRendererImp = new NoteTextRenderer(keyboardStateImp);

        //Add Observers
        keyboardStateImp.addObserver(grandStaffRendererImp);
        keyboardStateImp.addObserver(noteTextRendererImp);
        keyboardStateImp.addObserver(sightReadTrainerImp);

        modeSelectorImp.addObserver(grandStaffRendererImp);

        lowerNoteLimitModelImp.addObserver(rangeRendererImp);
        lowerNoteLimitModelImp.addObserver(sightReadTrainerImp);

        upperNoteLimitModelImp.addObserver(rangeRendererImp);
        upperNoteLimitModelImp.addObserver(sightReadTrainerImp);

        sightReadTrainerImp.addObserver(grandStaffRendererImp);

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
