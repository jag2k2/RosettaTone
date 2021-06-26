package uicomponents.renderer;

import notification.RangeChangeObserver;
import statemodels.NoteRangeModel;

import java.awt.*;

public class RangeRendererImp extends Component implements RangeChangeObserver {
    private final NoteRangeModel noteRangeModel;

    public RangeRendererImp(NoteRangeModel noteRangeModel){
        this.noteRangeModel = noteRangeModel;
    }

    @Override
    public void updateRange() {

    }
}
