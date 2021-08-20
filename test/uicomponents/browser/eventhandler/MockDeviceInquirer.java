package uicomponents.browser.eventhandler;

import instrument.simluated.MidiDeviceSimImp;

import javax.sound.midi.MidiDevice;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MockDeviceInquirer implements MidiDeviceInquirer{
    private final List<MidiDevice> devices = new ArrayList<>();

    @Override
    public void refresh() {
        devices.clear();

        MidiDevice device1 = new MidiDeviceSimImp("Device 1", new JPanel());
        MidiDevice device2 = new MidiDeviceSimImp("Device 2", new JPanel());
        MidiDevice device3 = new MidiDeviceSimImp("Device 3", new JPanel());

        devices.add(device1);
        devices.add(device2);
        devices.add(device3);
    }

    @Override
    public List<MidiDevice> getMidiDevices() {
        return devices;
    }

    @Override
    public MidiDevice getMidiDevice(int index) {
        return devices.get(index);
    }
}
