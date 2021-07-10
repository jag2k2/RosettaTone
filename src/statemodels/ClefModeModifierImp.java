package statemodels;

import music.Staff;
import uicomponents.clefmode.ClefMode;
import uicomponents.clefmode.ClefModeModifier;
import uicomponents.renderer.StaffDecorator;
import uicomponents.renderer.RenderConstants;
import java.util.HashSet;
import java.util.Set;

public class ClefModeModifierImp implements ClefModeModifier, StaffDecorator {
    private final ClefModeChangeNotifier clefModeChangeNotifier;
    private ClefMode clefMode;

    public ClefModeModifierImp(ClefMode clefMode, ClefModeChangeNotifier clefModeChangeNotifier){
        this.clefMode = clefMode;
        this.clefModeChangeNotifier = clefModeChangeNotifier;
    }

    @Override
    public ClefMode getState() {
        return clefMode;
    }

    @Override
    public void setState(ClefMode clefMode) {
        if (!this.clefMode.equals(clefMode)){
            this.clefMode = clefMode;
            clefModeChangeNotifier.notifyObservers();
        }
    }

    @Override
    public Set<Staff> getEnabledStaffs() {
        Set<Staff> enabledStaffs = new HashSet<>();

        if (clefMode == ClefMode.Treble || clefMode == ClefMode.Grand){
            enabledStaffs.add(RenderConstants.trebleStaff);
        }
        if (clefMode == ClefMode.Bass || clefMode == ClefMode.Grand){
            enabledStaffs.add(RenderConstants.bassStaff);
        }
        return enabledStaffs;
    }

    @Override
    public Set<Integer> getHelperLines(int lineNumber) {
        Set<Integer> helperLines = new HashSet<>();
        if (isLineAboveVisible(lineNumber)){
            for (int i = lineNumber; i < getClosestVisibleLine(lineNumber); i++){
                if((i % 2) == 0)
                    helperLines.add(i);
            }
        }
        if (isLineBelowVisible(lineNumber)){
            for (int i = lineNumber; i > getClosestVisibleLine(lineNumber); i--){
                if((i % 2) == 0)
                    helperLines.add(i);
            }
        }
        return helperLines;
    }

    protected boolean isLineAboveVisible(int lineNumber) {
        if (clefMode == ClefMode.Grand){
            boolean aboveTreble = RenderConstants.trebleStaff.isLineAboveVisible(lineNumber);
            boolean belowTreble = RenderConstants.trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = RenderConstants.bassStaff.isLineAboveVisible(lineNumber);
            return aboveTreble || (aboveBass && belowTreble);
        }
        else if (clefMode == ClefMode.Treble)
            return RenderConstants.trebleStaff.isLineAboveVisible(lineNumber);
        else
            return RenderConstants.bassStaff.isLineAboveVisible(lineNumber);
    }

    protected boolean isLineBelowVisible(int lineNumber) {
        if (clefMode == ClefMode.Grand){
            boolean belowBass = RenderConstants.bassStaff.isLineBelowVisible(lineNumber);
            boolean belowTreble = RenderConstants.trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = RenderConstants.bassStaff.isLineAboveVisible(lineNumber);
            return belowBass || (aboveBass && belowTreble);
        }
        else if (clefMode == ClefMode.Bass)
            return RenderConstants.bassStaff.isLineBelowVisible(lineNumber);
        else
            return RenderConstants.trebleStaff.isLineBelowVisible(lineNumber);
    }

    protected int getClosestVisibleLine(int lineNumber) {
        int closestTrebleLineNumber = RenderConstants.trebleStaff.getClosestVisibleLine(lineNumber);
        int closestBassLineNumber = RenderConstants.bassStaff.getClosestVisibleLine(lineNumber);

        if (clefMode == ClefMode.Grand) {
            int distanceFromClosestTreble = Math.abs(lineNumber - closestTrebleLineNumber);
            int distanceFromClosestBass = Math.abs(lineNumber - closestBassLineNumber);
            if (distanceFromClosestTreble < distanceFromClosestBass)
                return closestTrebleLineNumber;
            else
                return closestBassLineNumber;
        }
        else if(clefMode == ClefMode.Treble)
                return closestTrebleLineNumber;
        else
            return closestBassLineNumber;
    }
}
