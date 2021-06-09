package instrument;

public class Key {
    private int midiNumber;

    public Key(int midiNumber){
        this.midiNumber = midiNumber;
    }

    public int getOctave(){
        return (midiNumber / 12) - 1;
    }

    public int getNaturalIndex(){
       if (isCDorE())
           return getOctaveKey() / 2;
       else
           return (1 + getOctaveKey()) / 2;
    }

    protected int getOctaveKey(){
        return midiNumber % 12;
    }

    public boolean isCDorE(){
        return (getOctaveKey() < 5);
    }

    public boolean isEven(){
        return ((midiNumber % 2) == 0);
    }
}
