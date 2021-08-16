package uicomponents.browser;

import javax.sound.midi.MidiDevice;
import javax.swing.*;

public interface BrowserHandler {
    JList<MidiDevice> createDeviceList();
    AbstractButton createRefreshButton();
    void refreshTransmitterDevices();
}
