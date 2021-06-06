package DeviceIO;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import java.util.List;

public interface Instrument {
    void connect(MidiDevice.Info info, Receiver receiver);
    void close();
}
