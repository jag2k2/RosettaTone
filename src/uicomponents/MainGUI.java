package uicomponents;

import javax.swing.*;
import java.awt.*;

import instrument.*;
import music.*;
import uicomponents.browser.InstrumentBrowserImp;
import uicomponents.renderer.GrandStaffRendererImp;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();

        NoteStateImp noteState = new NoteStateImp();
        GrandStaffRendererImp grandStaffRenderer = new GrandStaffRendererImp(noteState, textArea);
        KeyReceiverImp keyReceiver = new KeyReceiverImp(noteState);
        keyReceiver.addKeyChangeObserver(grandStaffRenderer);

        InstrumentBrowserImp instrumentBrowser = new InstrumentBrowserImp(keyReceiver);

        mainPanel.add(BorderLayout.WEST, instrumentBrowser.getPanel());
        mainPanel.add(BorderLayout.CENTER, grandStaffRenderer);
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
