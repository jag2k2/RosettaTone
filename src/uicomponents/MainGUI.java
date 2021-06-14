package uicomponents;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import instrument.*;
import music.*;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();

        InstrumentBrowser instrumentBrowser = new InstrumentBrowserImp();
        List<MidiDevice> devices = instrumentBrowser.getTransmitterDevices();
        Transmitter piano = instrumentBrowser.getSelectedTransmitter();
        //Transmitter piano = instrumentBrowser.getSimulatedTransmitter(frame);

        NoteState noteState = new NoteStateImp();
        GrandStaffRendererImp staffRenderer = new GrandStaffRendererImp(noteState, textArea);

        KeyReceiverImp keyReceiver = new KeyReceiverImp(noteState);
        keyReceiver.addKeyChangeObserver(staffRenderer);
        piano.setReceiver(keyReceiver);


        mainPanel.add(BorderLayout.CENTER, staffRenderer);
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
