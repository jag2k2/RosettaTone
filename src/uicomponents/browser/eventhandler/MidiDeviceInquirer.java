package uicomponents.browser.eventhandler;

import javax.sound.midi.MidiDevice;
import java.util.List;

public interface MidiDeviceInquirer {
    List<MidiDevice> getMidiDevices();
}
