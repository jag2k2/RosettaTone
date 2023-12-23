package com.jag2k2.uicomponents.renderer.grandstaff;

import com.jag2k2.uicomponents.renderer.records.NoteImages;
import javax.swing.*;
import java.awt.*;

public interface FlashcardDrawable extends Runnable {
    void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeDrawable staffMode, NoteNameDrawable noteNameMode);
    void setScrollableComponent(JComponent flashcardDisplay);
}
