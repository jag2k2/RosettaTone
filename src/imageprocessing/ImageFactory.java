package imageprocessing;

import uicomponents.renderer.records.RenderConstants;

import java.awt.image.BufferedImage;

public class ImageFactory {

    static public BufferedImage createTrebleImage() {
        StaffImage trebleStaffImage = new StaffImage(RenderConstants.trebleClefPath);
        trebleStaffImage.resize(RenderConstants.trebleResizeFactor);
        return trebleStaffImage.getBufferedImage();
    }

    static public BufferedImage createBassImage() {
        StaffImage bassStaffImage = new StaffImage(RenderConstants.bassClefPath);
        bassStaffImage.resize(RenderConstants.bassResizeFactor);
        return bassStaffImage.getBufferedImage();
    }

    static public BufferedImage createNoteImage() {
        StaffImage noteStaffImage = new StaffImage(RenderConstants.notePath);
        noteStaffImage.resize(RenderConstants.noteResizeFactor);
        return noteStaffImage.getBufferedImage();
    }

    static public BufferedImage createSharpImage() {
        StaffImage sharpStaffImage = new StaffImage(RenderConstants.sharpPath);
        sharpStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return sharpStaffImage.getBufferedImage();
    }

    static public BufferedImage createNaturalImage() {
        StaffImage naturalStaffImage = new StaffImage(RenderConstants.naturalPath);
        naturalStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return naturalStaffImage.getBufferedImage();
    }

    static public BufferedImage createFlatImage() {
        StaffImage flatStaffImage = new StaffImage(RenderConstants.flatPath);
        flatStaffImage.resize(RenderConstants.accidentalResizeFactor);
        return flatStaffImage.getBufferedImage();
    }
}
