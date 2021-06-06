package deviceio;

import javax.sound.midi.MidiDevice;
import java.util.List;

public interface InstrumentBrowser {
    List<MidiDevice> getTransmitterDevices();
}
