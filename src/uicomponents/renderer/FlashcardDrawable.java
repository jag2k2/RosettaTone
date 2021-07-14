package uicomponents.renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public interface FlashcardDrawable extends Runnable {
    void draw(Graphics2D graphics2D, BufferedImage noteImage, BufferedImage sharpImage, BufferedImage naturalImage, BufferedImage flatImage, StaffModeDrawable staffMode);
    void setScrollableComponent(JComponent flashcardDisplay);
}
