package uicomponents.renderer;

import imageprocessing.StaffImage;

import javax.swing.*;
import java.awt.*;

public interface FlashcardDrawable extends Runnable {
    void draw(Graphics2D graphics2D, StaffImage noteImage, StaffImage sharpImage, StaffImage naturalImage, StaffImage flatImage, StaffModeDrawable staffMode, NoteNameDrawable alphabetMode);
    void setScrollableComponent(JComponent flashcardDisplay);
}
