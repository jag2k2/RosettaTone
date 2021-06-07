package music;

public enum NoteAccidental {
    Natural, SHARP, FLAT;

    @Override
    public String toString(){
        if (this == Natural){
            return "";
        }
        else if(this == SHARP){
            return "#";
        }
        else if(this == FLAT){
            return "b";
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}
