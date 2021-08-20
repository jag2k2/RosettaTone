package instrument.simluated;

import instrument.keybinder.KeyBindTransmitter;

import javax.sound.midi.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MidiDeviceSimImp implements MidiDevice {
    private final Transmitter midiTransmitter;
    private final List<Transmitter> transmitters;
    private final SimulatedInfo deviceInfo;
    private boolean open;

    public MidiDeviceSimImp(String name, JComponent component){
        this.midiTransmitter = new KeyBindTransmitter(component);
        this.transmitters = new ArrayList<>();
        this.deviceInfo = new SimulatedInfo(name);
        this.open = false;
        transmitters.add(this.midiTransmitter);
    }

    @Override
    public Info getDeviceInfo() {
        return deviceInfo;
    }

    @Override
    public void open() {
        open = true;
        System.out.println("Opening Simulated Device");
    }

    @Override
    public void close() {
        midiTransmitter.close();
        open = false;
        System.out.println("Closing Simulated Device");
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public long getMicrosecondPosition() {
        return 0;
    }

    @Override
    public int getMaxReceivers() {
        return 0;
    }

    @Override
    public int getMaxTransmitters() {
        return 1;
    }

    @Override
    public Receiver getReceiver() {
        return midiTransmitter.getReceiver();
    }

    @Override
    public List<Receiver> getReceivers() {
        return null;
    }

    @Override
    public Transmitter getTransmitter() {
        return midiTransmitter;
    }

    @Override
    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MidiDeviceSimImp){
            MidiDeviceSimImp compare = (MidiDeviceSimImp) obj;
            return (deviceInfo.getName().equals(compare.deviceInfo.getName()) &&
                    deviceInfo.getDescription().equals(compare.deviceInfo.getDescription()) &&
                    deviceInfo.getVendor().equals(compare.deviceInfo.getVendor()) &&
                    deviceInfo.getVersion().equals(compare.deviceInfo.getVersion()));
        }
        return false;
    }
}
