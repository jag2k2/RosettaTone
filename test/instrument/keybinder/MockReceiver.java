package instrument.keybinder;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MockReceiver implements Receiver {
    private int lastStatus;
    private int lastKey;

    public MockReceiver(){
        this.lastStatus = -1;
        this.lastKey = -1;
    }

    public MockReceiver(int lastStatus, int lastKey){
        this.lastStatus = lastStatus;
        this.lastKey = lastKey;
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        lastStatus = message.getStatus();
        lastKey = message.getMessage()[1];
    }

    @Override
    public void close() {
    }

    @Override
    public String toString() {
        return "Status : " + lastStatus + ", Key: " + lastKey;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MockReceiver){
            MockReceiver compare = (MockReceiver) obj;
            return (this.lastStatus == compare.lastStatus && this.lastKey == compare.lastKey);
        }
        return false;
    }
}
