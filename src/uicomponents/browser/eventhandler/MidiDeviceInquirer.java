package uicomponents.browser.eventhandler;

import javax.sound.midi.MidiDevice;
import java.util.List;

public interface MidiDeviceInquirer {
    void refresh();
    List<MidiDevice> getMidiDevices();
    MidiDevice getMidiDevice(int index);
}
