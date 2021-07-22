package statemodels;

import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.renderer.records.NoteImages;
import utility.NoteSet;

import java.awt.*;

public interface NoteDrawable {
    void draw(Graphics2D graphics2D, NoteImages noteImages, NoteSet notes, int xPos, StaffModeEvaluator staffMode, boolean drawName);
}
