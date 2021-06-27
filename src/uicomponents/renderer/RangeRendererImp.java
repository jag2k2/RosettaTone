package uicomponents.renderer;

import notification.RangeChangeObserver;
import statemodels.NoteLimitModel;

import java.awt.*;

public class RangeRendererImp extends Component implements RangeChangeObserver {
    private final NoteLimitModel lowerNoteLimitModel;
    private final NoteLimitModel upperNoteLimitModel;

    public RangeRendererImp(NoteLimitModel lowerNoteLimitModel, NoteLimitModel upperNoteLimitModel){
        this.lowerNoteLimitModel = lowerNoteLimitModel;
        this.upperNoteLimitModel = upperNoteLimitModel;
    }

    @Override
    public void updateRange() {
        System.out.println("[" + lowerNoteLimitModel.getLimit().toString() + ", " + upperNoteLimitModel.getLimit().toString() + "]");
    }
}
