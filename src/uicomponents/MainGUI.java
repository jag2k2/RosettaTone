package uicomponents;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.swing.*;
import java.awt.*;
import java.util.List;

import deviceio.*;
import music.*;

public class MainGUI {
    private final JFrame frame;

    public MainGUI() {
        this.frame = new JFrame();

        JTextArea textArea = new JTextArea();

        InstrumentBrowser instrumentBrowser = new InstrumentBrowserImp();
        List<MidiDevice> devices = instrumentBrowser.getTransmitterDevices();

        Instrument piano = new InstrumentImp();
        Keyboard keyboard = new KeyboardImp();

        KeyboardRendererImp keyboardRenderer = new KeyboardRendererImp(keyboard, textArea);
        NoteReceiver noteReceiver = new NoteReceiverImp(keyboard);
        noteReceiver.addNoteChangeObserver(keyboardRenderer);
        piano.connect(devices.get(0).getDeviceInfo(), noteReceiver);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, keyboardRenderer);
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
