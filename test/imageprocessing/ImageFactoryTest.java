package imageprocessing;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ImageFactoryTest {

    @Test
    void createTrebleImage() {
        BufferedImage image = ImageFactory.createTrebleImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createBassImage() {
        BufferedImage image = ImageFactory.createBassImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createNoteImage() {
        BufferedImage image = ImageFactory.createNoteImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createSharpImage() {
        BufferedImage image = ImageFactory.createSharpImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createNaturalImage() {
        BufferedImage image = ImageFactory.createNaturalImage();
        assertTrue(image.getWidth() > 0);
    }

    @Test
    void createFlatImage() {
        BufferedImage image = ImageFactory.createFlatImage();
        assertTrue(image.getWidth() > 0);
    }
}