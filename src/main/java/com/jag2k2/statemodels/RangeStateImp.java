package com.jag2k2.statemodels;

import com.jag2k2.uicomponents.renderer.limit.LimitDrawable;
import com.jag2k2.uicomponents.renderer.limit.RangeDrawable;

import java.awt.*;

public class RangeStateImp implements RangeDrawable {
    private final LimitDrawable lowerLimit;
    private final LimitDrawable upperLimit;

    public RangeStateImp(LimitDrawable lowerLimit, LimitDrawable upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    @Override
    public void draw(Graphics2D graphics2D, float transparency) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        lowerLimit.draw(graphics2D);
        upperLimit.draw(graphics2D);
        lowerLimit.drawConnection(graphics2D, upperLimit);
    }
}
