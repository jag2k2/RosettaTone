package uicomponents;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import deviceio.*;
import music.NoteRenderer;
import music.NoteRendererImp;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();

        JTextArea textArea = new JTextArea();

        InstrumentBrowser instrumentBrowser = new InstrumentBrowserImp();
        List<MidiDevice> devices = instrumentBrowser.getTransmitterDevices();
        Instrument piano = new InstrumentImp();
        NoteRendererImp noteRenderer = new NoteRendererImp(textArea);
        piano.connect(devices.get(0).getDeviceInfo(), new NoteReceiverImp(noteRenderer));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, noteRenderer);
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
