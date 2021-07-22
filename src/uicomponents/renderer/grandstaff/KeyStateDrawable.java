package uicomponents.renderer.grandstaff;

import uicomponents.renderer.records.NoteImages;
import java.awt.*;

public interface KeyStateDrawable {
    void draw(Graphics2D graphics2D, NoteImages noteImages, StaffModeEvaluator staffMode);
}
