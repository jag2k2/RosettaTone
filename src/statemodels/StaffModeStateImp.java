package statemodels;

import music.Staff;
import uicomponents.clefmode.ClefMode;
import uicomponents.clefmode.ClefModeModifier;
import uicomponents.renderer.StaffModeDrawable;
import utility.Maybe;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;


public class StaffModeStateImp implements ClefModeModifier, StaffModeDrawable {
    private final Staff trebleStaff;
    private final Staff bassStaff;
    private Maybe<StaffModeChangeNotifier> clefModeChangeNotifier = new Maybe<>();
    private ClefMode clefMode;

    public StaffModeStateImp(ClefMode clefMode, Staff trebleStaff, Staff bassStaff){
        this.clefMode = clefMode;
        this.trebleStaff = trebleStaff;
        this.bassStaff = bassStaff;
    }

    public void addClefModeChangeNotifier(StaffModeChangeNotifier staffModeChangeNotifier){
        this.clefModeChangeNotifier = new Maybe<>(staffModeChangeNotifier);
    }

    @Override
    public void setUISelected(JComboBox<ClefMode> comboBox) {
        comboBox.setSelectedItem(clefMode);
    }

    @Override
    public void setState(ClefMode clefMode) {
        if (!this.clefMode.equals(clefMode)){
            this.clefMode = clefMode;
            for(StaffModeChangeNotifier notifier : clefModeChangeNotifier)
                notifier.notifyObservers();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage trebleImage, BufferedImage bassImage) {
        if (clefMode == ClefMode.Treble || clefMode == ClefMode.Grand){
            trebleStaff.draw(graphics2D, trebleImage);
        }
        if (clefMode == ClefMode.Bass || clefMode == ClefMode.Grand){
            bassStaff.draw(graphics2D, bassImage);
        }
    }

    @Override
    public Set<Integer> getLedgerLines(int lineNumber) {
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
