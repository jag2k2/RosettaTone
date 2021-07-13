package uicomponents.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface KeyStateDrawable {
    void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode);
}
