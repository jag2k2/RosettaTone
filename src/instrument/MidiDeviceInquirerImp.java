package instrument;

import instrument.simluated.MidiDeviceSimImp;
import uicomponents.browser.eventhandler.MidiDeviceInquirer;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MidiDeviceInquirerImp implements MidiDeviceInquirer {
    private final JComponent component;
    List<MidiDevice> devices = new ArrayList<>();

    public MidiDeviceInquirerImp(JComponent component) {
        this.component = component;
    }

    @Override
    public void refresh() {
        devices.clear();
        MidiDevice simDevice = new MidiDeviceSimImp("Simulated Keyboard", component);
        devices.add(simDevice);
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : deviceInfo) {
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                devices.add(device);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
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
