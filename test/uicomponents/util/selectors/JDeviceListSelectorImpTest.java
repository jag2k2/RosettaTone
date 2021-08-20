package uicomponents.util.selectors;

import instrument.keybinder.MockReceiver;
import instrument.simluated.MidiDeviceSimImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uicomponents.browser.eventhandler.MidiDeviceInquirer;
import uicomponents.browser.eventhandler.MockDeviceInquirer;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Receiver;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class JDeviceListSelectorImpTest {
    private JDeviceListSelectorImp selector;
    private Receiver receiver;
    private MidiDeviceInquirer deviceInquirer;

    @BeforeEach
    void setup(){
        receiver = new MockReceiver();
        deviceInquirer = new MockDeviceInquirer();
        selector = new JDeviceListSelectorImp(receiver, deviceInquirer);
    }

    @Test
    void canDisplayAsString(){
        selector.refreshSelections();
        selector.setSelectedIndex(1);
        assertEquals("[Device 1, *Device 2, Device 3]", selector.toString());
    }

    @Test
    void canCheckEquality(){
        JDeviceListSelectorImp expected = new JDeviceListSelectorImp(receiver, deviceInquirer);
        assertEquals(expected, selector);
        selector.refreshSelections();
        assertNotEquals(expected, selector);
        expected.refreshSelections();
        assertEquals(expected, selector);
        expected.setSelectedIndex(0);
        assertNotEquals(expected, selector);
        selector.setSelectedIndex(0);
        assertEquals(expected, selector);
    }

    @Test
    void canRefreshSelections(){
        selector.refreshSelections();

        JSelector<MidiDevice> expected = new JDeviceListSelectorImp(receiver, deviceInquirer);
        expected.addItem(new MidiDeviceSimImp("Device 1", new JPanel()));
        expected.addItem(new MidiDeviceSimImp("Device 2", new JPanel()));
        expected.addItem(new MidiDeviceSimImp("Device 3", new JPanel()));
        assertEquals(expected, selector);

        selector.setSelectedIndex(0);
        assertNotEquals(expected, selector);

        expected.setSelectedIndex(0);
        assertEquals(expected, selector);
    }
}