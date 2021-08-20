package uicomponents.browser.eventhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uicomponents.browser.BrowserHandler;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.selectors.JDeviceListSelectorImp;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.swing.*;

class BrowserHandlerImpTest {
    private BrowserHandler browserHandler;
    private MidiDeviceInquirer mockMidiDeviceInquirer;
    private Receiver mockReceiver;

    private JSelector<MidiDevice> selector;
    private AbstractButton refreshButton;

    @BeforeEach
    void setup(){
        mockReceiver = new MockReceiver();
        mockMidiDeviceInquirer = new MockDeviceInquirer();
        browserHandler = new BrowserHandlerImp(mockReceiver, mockMidiDeviceInquirer);

        selector = browserHandler.createDeviceList();
        refreshButton = browserHandler.createRefreshButton();
    }

    @Test
    void canDisplayDevices() {
        JSelector<MidiDevice> expected = new JDeviceListSelectorImp(mockReceiver, mockMidiDeviceInquirer);
        expected.refreshSelections();
        refreshButton.doClick();
        assertEquals(expected, selector);
    }

    @Test
    void canConnectToTransmitter() throws MidiUnavailableException {
        int selectedIndex;

        refreshButton.doClick();
        for(MidiDevice device : mockMidiDeviceInquirer.getMidiDevices()){
            assertNull(device.getReceiver());
        }

        selectedIndex = 1;
        selector.setSelectedIndex(selectedIndex);
        MidiDevice selectedDevice = mockMidiDeviceInquirer.getMidiDevice(selectedIndex);
        assertEquals(mockReceiver, selectedDevice.getReceiver());

        selectedIndex = 0;
        selector.setSelectedIndex(selectedIndex);
        selectedDevice = mockMidiDeviceInquirer.getMidiDevice(selectedIndex);
        assertEquals(mockReceiver, selectedDevice.getReceiver());
    }
}