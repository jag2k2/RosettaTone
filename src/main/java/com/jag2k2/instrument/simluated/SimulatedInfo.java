package instrument.simluated;

import javax.sound.midi.MidiDevice;

public class SimulatedInfo extends MidiDevice.Info {

    public SimulatedInfo(){
        super("Simulated Keyboard", "", "Simulated Digital Instrument", "1.0");
    }
}
