package com.jag2k2.music;

public enum NoteAccidental {
    NATURAL, SHARP, FLAT;

    @Override
    public String toString(){
        if (this == NATURAL){
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
