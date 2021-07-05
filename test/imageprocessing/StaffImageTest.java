package imageprocessing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class StaffImageTest {
    private StaffImage staffImage;

    @BeforeEach
    void setup(){
        String path = "/images/Treble-clef.png";
        staffImage = new StaffImage(path);
    }

    @Test
    void getBufferedImage() {
        BufferedImage bufferedImage = staffImage.getBufferedImage();
        assertEquals(156, bufferedImage.getWidth());
        assertEquals(426, bufferedImage.getHeight());
    }

    @Test
    void testResize() {
        staffImage.resize(0.5);
        BufferedImage bufferedImage = staffImage.getBufferedImage();
        assertEquals(78, bufferedImage.getWidth());
        assertEquals(213, bufferedImage.getHeight());
    }
}