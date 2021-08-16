package uicomponents.browser.eventhandler;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MockReceiver implements Receiver {
    @Override
    public void send(MidiMessage message, long timeStamp) {

    }

    @Override
    public void close() {

    }
}
