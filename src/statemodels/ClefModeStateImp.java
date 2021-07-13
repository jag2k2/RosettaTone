package statemodels;

import music.Staff;
import uicomponents.clefmode.ClefMode;
import uicomponents.clefmode.ClefModeModifier;
import uicomponents.MusicDrawable;
import utility.Maybe;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;


public class ClefModeStateImp implements ClefModeModifier, MusicDrawable {
    private final Staff trebleStaff;
    private final Staff bassStaff;
    private Maybe<ClefModeChangeNotifier> clefModeChangeNotifier = new Maybe<>();
    private ClefMode clefMode;

    public ClefModeStateImp(ClefMode clefMode, Staff trebleStaff, Staff bassStaff){
        this.clefMode = clefMode;
        this.trebleStaff = trebleStaff;
        this.bassStaff = bassStaff;
    }

    public void addClefModeChangeNotifier(ClefModeChangeNotifier clefModeChangeNotifier){
        this.clefModeChangeNotifier = new Maybe<>(clefModeChangeNotifier);
    }

    @Override
    public void setUISelected(JComboBox<ClefMode> comboBox) {
        comboBox.setSelectedItem(clefMode);
    }

    @Override
    public void setState(ClefMode clefMode) {
        if (!this.clefMode.equals(clefMode)){
            this.clefMode = clefMode;
            for(ClefModeChangeNotifier notifier : clefModeChangeNotifier)
                notifier.notifyObservers();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        if (clefMode == ClefMode.Treble || clefMode == ClefMode.Grand){
            trebleStaff.draw(graphics2D);
        }
        if (clefMode == ClefMode.Bass || clefMode == ClefMode.Grand){
            bassStaff.draw(graphics2D);
        }
    }

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
            boolean aboveTreble = trebleStaff.isLineAboveVisible(lineNumber);
            boolean belowTreble = trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = bassStaff.isLineAboveVisible(lineNumber);
            return aboveTreble || (aboveBass && belowTreble);
        }
        else if (clefMode == ClefMode.Treble)
            return trebleStaff.isLineAboveVisible(lineNumber);
        else
            return bassStaff.isLineAboveVisible(lineNumber);
    }

    protected boolean isLineBelowVisible(int lineNumber) {
        if (clefMode == ClefMode.Grand){
            boolean belowBass = bassStaff.isLineBelowVisible(lineNumber);
            boolean belowTreble = trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = bassStaff.isLineAboveVisible(lineNumber);
            return belowBass || (aboveBass && belowTreble);
        }
        else if (clefMode == ClefMode.Bass)
            return bassStaff.isLineBelowVisible(lineNumber);
        else
            return trebleStaff.isLineBelowVisible(lineNumber);
    }

    protected int getClosestVisibleLine(int lineNumber) {
        int closestTrebleLineNumber = trebleStaff.getClosestVisibleLine(lineNumber);
        int closestBassLineNumber = bassStaff.getClosestVisibleLine(lineNumber);

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
