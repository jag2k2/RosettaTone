package imageprocessing;

import uicomponents.renderer.records.RenderConstants;

public class ImageFactory {

    static public StaffImage createTrebleImage() {
        StaffImage trebleStaffImage = new StaffImage(RenderConstants.trebleClefPath);
        trebleStaffImage.resize(RenderConstants.trebleResizeFactor);
        return trebleStaffImage;
    }

    static public StaffImage createBassImage() {
        StaffImage bassStaffImage = new StaffImage(RenderConstants.bassClefPath);
        bassStaffImage.resize(RenderConstants.bassResizeFactor);
        return bassStaffImage;
    }

    static public StaffImage createNoteImage() {
        StaffImage noteStaffImage = new StaffImage(RenderConstants.notePath);
        noteStaffImage.resize(RenderConstants.noteResizeFactor);
        return noteStaffImage;
    }

    static public StaffImage createSharpImage() {
        StaffImage sharpStaffImage = new StaffImage(RenderConstants.sharpPath);
        sharpStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return sharpStaffImage;
    }

    static public StaffImage createNaturalImage() {
        StaffImage naturalStaffImage = new StaffImage(RenderConstants.naturalPath);
        naturalStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return naturalStaffImage;
    }

    static public StaffImage createFlatImage() {
        StaffImage flatStaffImage = new StaffImage(RenderConstants.flatPath);
        flatStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return flatStaffImage;
    }
}
