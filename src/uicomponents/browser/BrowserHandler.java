package uicomponents.browser;

import uicomponents.util.selectors.JSelector;
import javax.sound.midi.MidiDevice;
import javax.swing.*;

public interface BrowserHandler {
    JSelector<MidiDevice> createDeviceList();
    AbstractButton createRefreshButton();
}
