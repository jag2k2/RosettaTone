package com.jag2k2.uicomponents.renderer.records;

import com.jag2k2.imageprocessing.StaffImage;

public class NoteImages {
    public final StaffImage noteImage;
    public final StaffImage sharpImage;
    public final StaffImage naturalImage;
    public final StaffImage flatImage;

    public NoteImages(StaffImage noteImage, StaffImage sharpImage, StaffImage naturalImage, StaffImage flatImage){
        this.noteImage = noteImage;
        this.sharpImage = sharpImage;
        this.naturalImage = naturalImage;
        this.flatImage = flatImage;
    }
}
