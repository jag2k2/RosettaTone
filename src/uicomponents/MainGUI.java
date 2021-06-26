package uicomponents;

import javax.swing.*;
import java.awt.*;
import instrument.*;
import music.*;
import statemodels.NoteStateImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import statemodels.NoteRangeModelImp;
import uicomponents.renderer.NoteTextRenderer;
import uicomponents.renderer.RangeRendererImp;
import uicomponents.staffselector.ModeSelectorImp;
import uicomponents.staffselector.StaffOptions;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        //State Models
        NoteStateImp noteStateImp = new NoteStateImp();
        NoteRangeModelImp noteRangeModelImp = new NoteRangeModelImp(new Note(NoteName.C, 4), new Note(NoteName.B, 4));

        //KeyReceiver
        KeyNoteReceiverImp keyNoteReceiverImp = new KeyNoteReceiverImp(noteStateImp);

        //Selectors
        InstrumentBrowserImp instrumentBrowserImp = new InstrumentBrowserImp(keyNoteReceiverImp);
        StaffSelectionImp staffSelectionImp = new StaffSelectionImp(StaffOptions.Grand);
        ModeSelectorImp modeSelectorImp = new ModeSelectorImp(staffSelectionImp);
        RangeSelectorImp rangeSelectorImp = new RangeSelectorImp(noteRangeModelImp);

        //Renderers
        GrandStaffRendererImp grandStaffRendererImp = new GrandStaffRendererImp(noteStateImp, staffSelectionImp);
        RangeRendererImp rangeRendererImp = new RangeRendererImp(noteRangeModelImp);
        NoteTextRenderer noteTextRendererImp = new NoteTextRenderer(noteStateImp);

        //Add Observers
        noteStateImp.addObserver(grandStaffRendererImp);
        noteStateImp.addObserver(noteTextRendererImp);
        noteRangeModelImp.addObserver(rangeRendererImp);

        //Build Panels
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.add(instrumentBrowserImp.getPanel());
        configPanel.add(rangeSelectorImp.getPanel());
        configPanel.add(modeSelectorImp.getPanel());

        JPanel staffPanel = new JPanel(new FlowLayout());
        staffPanel.add(grandStaffRendererImp);
        mainPanel.add(BorderLayout.WEST, configPanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.add(BorderLayout.SOUTH, noteTextRendererImp.getPanel());
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
