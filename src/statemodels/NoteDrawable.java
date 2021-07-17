package statemodels;

import imageprocessing.StaffImage;
import utility.NoteSet;

import java.awt.*;
import java.util.Set;

public interface NoteDrawable {
    void draw(Graphics2D graphics2D, StaffImage noteImage, StaffImage sharpImage, StaffImage naturalImage, StaffImage flatImage,
              NoteSet notes, int xPos, Set<Integer> ledgerLines, boolean drawName);
}
