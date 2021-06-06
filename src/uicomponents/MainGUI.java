package uicomponents;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import DeviceIO.*;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(1200, 100));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, new Canvas());
        mainPanel.add(BorderLayout.SOUTH, textArea);

        InstrumentBrowser instrumentBrowser = new InstrumentBrowserImp();
        List<MidiDevice> devices = instrumentBrowser.getTransmitterDevices();
        Instrument piano = new InstrumentImp();
        piano.connect(devices.get(0).getDeviceInfo(), new NoteReceiverImp(textArea));

        frame.setTitle("Rosetta Tone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setLocation(300, 100);
        frame.setMinimumSize(new Dimension(1200, 500));
        frame.setPreferredSize(new Dimension(1200, 500));
        frame.pack();
    }

    public void launch(){
        frame.setVisible(true);
    }
}
