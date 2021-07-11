package imageprocessing;

import uicomponents.renderer.ImageLoader;
import uicomponents.renderer.RenderConstants;

import java.awt.image.BufferedImage;

public class ImageLoaderImp implements ImageLoader {
    private final BufferedImage noteImage;
    private final BufferedImage sharpImage;
    private final BufferedImage naturalImage;
    private final BufferedImage flatImage;

    public ImageLoaderImp(){
        StaffImage noteStaffImage = new StaffImage(RenderConstants.notePath);
        noteStaffImage.resize(RenderConstants.noteResizeFactor);
        this.noteImage = noteStaffImage.getBufferedImage();

        StaffImage sharpStaffImage = new StaffImage(RenderConstants.sharpPath);
        sharpStaffImage.resize(RenderConstants.accidentalResizeFactor);
        this.sharpImage = sharpStaffImage.getBufferedImage();

        StaffImage naturalStaffImage = new StaffImage(RenderConstants.naturalPath);
        naturalStaffImage.resize(RenderConstants.accidentalResizeFactor);
        this.naturalImage = naturalStaffImage.getBufferedImage();

        StaffImage flatStaffImage = new StaffImage(RenderConstants.flatPath);
        flatStaffImage.resize(RenderConstants.accidentalResizeFactor);
        this.flatImage = flatStaffImage.getBufferedImage();
    }

    @Override
    public BufferedImage getNoteImage() {
        return noteImage;
    }

    @Override
    public BufferedImage getSharpImage() {
        return sharpImage;
    }

    @Override
    public BufferedImage getNaturalImage() {
        return naturalImage;
    }

    @Override
    public BufferedImage getFlatImage() {
        return flatImage;
    }
}
