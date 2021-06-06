package deviceio;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;

public interface Instrument {
    void connect(MidiDevice.Info info, Receiver receiver);
    void close();
}
