package uicomponents.renderer;

import imageprocessing.StaffImage;
import uicomponents.renderer.records.NoteImages;

import java.awt.*;


public interface KeyStateDrawable {
    void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeDrawable staffMode);
}
