package com.jag2k2.uicomponents.renderer.grandstaff;

import com.jag2k2.uicomponents.renderer.records.NoteImages;

import java.awt.*;


public interface KeyStateDrawable {
    void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeDrawable staffMode);
}
