package uicomponents.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface FlashcardDrawable {
    void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode, int xTraveled);
}
