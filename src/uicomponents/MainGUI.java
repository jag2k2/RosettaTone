package uicomponents;

import javax.swing.*;
import java.awt.*;
import instrument.*;
import music.*;
import statemodels.NoteStateImp;
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
        NoteStateImp noteStateImp = new NoteStateImp();
        NoteLimitModelImp lowerNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.C, 4));
        NoteLimitModelImp upperNoteLimitModelImp = new NoteLimitModelImp(new Note(NoteName.B, 4));

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(noteStateImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowserImp = new InstrumentBrowserImp(keyNoteReceiverImp);
        ModeSelectorImp modeSelectorImp = new ModeSelectorImp(StaffMode.Grand);
        RangeSelectorImp rangeSelectorImp = new RangeSelectorImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);

        //Renderers
        GrandStaffRendererImp grandStaffRendererImp = new GrandStaffRendererImp(noteStateImp, modeSelectorImp);
        RangeRendererImp rangeRendererImp = new RangeRendererImp(lowerNoteLimitModelImp, upperNoteLimitModelImp);
        NoteTextRenderer noteTextRendererImp = new NoteTextRenderer(noteStateImp);

        //Add Observers
        noteStateImp.addObserver(grandStaffRendererImp);
        noteStateImp.addObserver(noteTextRendererImp);
        modeSelectorImp.addObserver(grandStaffRendererImp);
        lowerNoteLimitModelImp.addObserver(rangeRendererImp);
        upperNoteLimitModelImp.addObserver(rangeRendererImp);

        //Build Panels
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.add(instrumentBrowserImp.getComponent());
        configPanel.add(rangeSelectorImp.getComponent());
        configPanel.add(modeSelectorImp.getComponent());

        JPanel staffPanel = new JPanel(new FlowLayout());
        staffPanel.add(rangeRendererImp);
        staffPanel.add(grandStaffRendererImp);
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
