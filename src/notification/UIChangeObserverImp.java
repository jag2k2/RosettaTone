package notification;

import javax.swing.*;

public class UIChangeObserverImp implements UIChangeObserver {
    private final JComponent noteTextRenderer;
    private final JComponent grandStaffRenderer;
    private final JComponent rangeRenderer;

    public UIChangeObserverImp(JComponent noteTextRenderer, JComponent grandStaffRenderer, JComponent rangeRenderer) {
        this.noteTextRenderer = noteTextRenderer;
        this.grandStaffRenderer = grandStaffRenderer;
        this.rangeRenderer = rangeRenderer;
    }

    @Override
    public void configChanged() {
        grandStaffRenderer.repaint();
    }

    @Override
    public void boardChangedWithKeyDown() {
        grandStaffRenderer.repaint();
        noteTextRenderer.repaint();
    }

    @Override
    public void boardChangedWithKeyUp() {
        grandStaffRenderer.repaint();
        noteTextRenderer.repaint();
    }

    @Override
    public void limitChanged() {
        rangeRenderer.repaint();
    }
}
