package com.jag2k2.imageprocessing;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageFactoryTest {

    @Test
    void createTrebleImage() {
        StaffImage image = ImageFactory.createTrebleImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createBassImage() {
        StaffImage image = ImageFactory.createBassImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createNoteImage() {
        StaffImage image = ImageFactory.createNoteImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createSharpImage() {
        StaffImage image = ImageFactory.createSharpImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createNaturalImage() {
        StaffImage image = ImageFactory.createNaturalImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createFlatImage() {
        StaffImage image = ImageFactory.createFlatImage();
        assertTrue(image.getWidth() > 0);
    }
}