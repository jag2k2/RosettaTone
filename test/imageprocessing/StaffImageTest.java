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
    void testResize() {
        staffImage.resize(0.5);
        assertEquals(78, staffImage.getWidth());
        assertEquals(213, staffImage.getHeight());
    }
}