package instrument;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Transmitter;
import javax.swing.*;
import java.util.List;

public interface InstrumentBrowser {
    List<MidiDevice> getTransmitterDevices();
    Transmitter getSelectedTransmitter();
    Transmitter getSimulatedTransmitter(JFrame frame);
}
