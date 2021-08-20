package instrument.simluated;

import javax.sound.midi.MidiDevice;

public class SimulatedInfo extends MidiDevice.Info {

    public SimulatedInfo(String name){
        super(name, "", "Simulated Digital Instrument", "1.0");
    }


}
