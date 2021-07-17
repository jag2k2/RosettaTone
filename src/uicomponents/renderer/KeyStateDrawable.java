package uicomponents.renderer;

import imageprocessing.StaffImage;
import java.awt.*;


public interface KeyStateDrawable {
    void draw(Graphics2D graphics2D, StaffImage noteImage, StaffImage sharpImage, StaffImage naturalImage, StaffImage flatImage, StaffModeDrawable staffMode);
}
