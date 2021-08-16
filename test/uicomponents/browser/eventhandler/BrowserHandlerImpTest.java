package uicomponents.browser.eventhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uicomponents.browser.BrowserHandler;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.swing.*;

class BrowserHandlerImpTest {
    private BrowserHandler browserHandler;
    private MidiDeviceInquirer mockMidiDeviceInquirer;
    private Receiver mockReceiver;

    @BeforeEach
    void setup(){
        mockReceiver = new MockReceiver();
        mockMidiDeviceInquirer = new MockDeviceInquirer();
        browserHandler = new BrowserHandlerImp(mockReceiver, mockMidiDeviceInquirer);
    }

    @Test
    void canRefreshTransmitterDevices() {
        JList<MidiDevice> deviceList = browserHandler.createDeviceList();
        AbstractButton refreshButton = browserHandler.createRefreshButton();
        assertEquals(0, deviceList.getModel().getSize());
        refreshButton.doClick();
        assertEquals(3, deviceList.getModel().getSize());
    }

    @Test
    void canConnectToTransmitter() throws MidiUnavailableException {
        JList<MidiDevice> deviceList = browserHandler.createDeviceList();
        AbstractButton refreshButton = browserHandler.createRefreshButton();
        refreshButton.doClick();

        deviceList.setSelectedIndex(1);
        MidiDevice selectedDevice = deviceList.getModel().getElementAt(1);
        assertEquals(mockReceiver, selectedDevice.getReceiver());

        deviceList.clearSelection();
        assertNull(selectedDevice.getReceiver());

        deviceList.setSelectedIndex(0);
        selectedDevice = deviceList.getModel().getElementAt(0);
        assertEquals(mockReceiver, selectedDevice.getReceiver());
    }
}