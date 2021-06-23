package uicomponents;

import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.*;
import notification.StaffChangeNotifierImp;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.rangeselector.RangeSelectorImp;
import uicomponents.renderer.GrandStaffRendererImp;
import uicomponents.staffselector.ModeSelectorImp;
import uicomponents.staffselector.StaffOptions;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();

        NoteStateImp noteState = new NoteStateImp();
        StaffSelectionImp staffSelectionImp = new StaffSelectionImp(StaffOptions.Grand);

        GrandStaffRendererImp grandStaffRenderer = new GrandStaffRendererImp(noteState, staffSelectionImp, textArea);
        StaffChangeNotifierImp staffChangeNotifierImp = new StaffChangeNotifierImp();

        KeyNoteReceiverImp keyReceiver = new KeyNoteReceiverImp(noteState, staffChangeNotifierImp);
        ModeSelectorImp staffSelector = new ModeSelectorImp(staffSelectionImp, staffChangeNotifierImp);
        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyReceiver);
        RangeSelectorImp rangeSelector = new RangeSelectorImp();

        staffChangeNotifierImp.add(grandStaffRenderer);

        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.add(instrumentBrowser.getPanel());
        configPanel.add(rangeSelector.getPanel());
        configPanel.add(staffSelector.getPanel());


        JPanel staffPanel = new JPanel(new FlowLayout());
        staffPanel.add(grandStaffRenderer);
        mainPanel.add(BorderLayout.WEST, configPanel);
        mainPanel.add(BorderLayout.CENTER, staffPanel);
        mainPanel.add(BorderLayout.SOUTH, textArea);
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
