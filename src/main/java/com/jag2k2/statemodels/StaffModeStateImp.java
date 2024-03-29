package com.jag2k2.statemodels;

import com.jag2k2.music.Staff;
import com.jag2k2.uicomponents.renderer.records.ClefImages;
import com.jag2k2.uicomponents.renderer.records.RenderConstants;
import com.jag2k2.uicomponents.staffmode.StaffMode;
import com.jag2k2.uicomponents.staffmode.StaffModeModifier;
import com.jag2k2.uicomponents.renderer.grandstaff.StaffModeDrawable;
import com.jag2k2.utility.Maybe;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;


public class StaffModeStateImp implements StaffModeModifier, StaffModeDrawable {
    private final Staff trebleStaff = new Staff(RenderConstants.trebleStaff);
    private final Staff bassStaff = new Staff(RenderConstants.bassStaff);
    private Maybe<ConfigChangeNotifier> staffModeChangeNotifier = new Maybe<>();
    private StaffMode staffMode;

    public StaffModeStateImp(StaffMode staffMode){
        this.staffMode = staffMode;
    }

    public void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier){
        this.staffModeChangeNotifier = new Maybe<>(configChangeNotifier);
    }

    @Override
    public void setUISelected(JComboBox<StaffMode> comboBox) {
        comboBox.setSelectedItem(staffMode);
    }

    @Override
    public void setMode(StaffMode staffMode) {
        if (!this.staffMode.equals(staffMode)){
            this.staffMode = staffMode;
            for(ConfigChangeNotifier notifier : staffModeChangeNotifier)
                notifier.notifyObservers();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, ClefImages clefImages) {
        if (staffMode == StaffMode.Treble || staffMode == StaffMode.Grand){
            trebleStaff.draw(graphics2D, clefImages.trebleImage);
        }
        if (staffMode == StaffMode.Bass || staffMode == StaffMode.Grand){
            bassStaff.draw(graphics2D, clefImages.bassImage);
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
        if (staffMode == StaffMode.Grand){
            boolean aboveTreble = trebleStaff.isLineAboveVisible(lineNumber);
            boolean belowTreble = trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = bassStaff.isLineAboveVisible(lineNumber);
            return aboveTreble || (aboveBass && belowTreble);
        }
        else if (staffMode == StaffMode.Treble)
            return trebleStaff.isLineAboveVisible(lineNumber);
        else
            return bassStaff.isLineAboveVisible(lineNumber);
    }

    protected boolean isLineBelowVisible(int lineNumber) {
        if (staffMode == StaffMode.Grand){
            boolean belowBass = bassStaff.isLineBelowVisible(lineNumber);
            boolean belowTreble = trebleStaff.isLineBelowVisible(lineNumber);
            boolean aboveBass = bassStaff.isLineAboveVisible(lineNumber);
            return belowBass || (aboveBass && belowTreble);
        }
        else if (staffMode == StaffMode.Bass)
            return bassStaff.isLineBelowVisible(lineNumber);
        else
            return trebleStaff.isLineBelowVisible(lineNumber);
    }

    protected int getClosestVisibleLine(int lineNumber) {
        int closestTrebleLineNumber = trebleStaff.getClosestVisibleLine(lineNumber);
        int closestBassLineNumber = bassStaff.getClosestVisibleLine(lineNumber);

        if (staffMode == StaffMode.Grand) {
            int distanceFromClosestTreble = Math.abs(lineNumber - closestTrebleLineNumber);
            int distanceFromClosestBass = Math.abs(lineNumber - closestBassLineNumber);
            if (distanceFromClosestTreble < distanceFromClosestBass)
                return closestTrebleLineNumber;
            else
                return closestBassLineNumber;
        }
        else if(staffMode == StaffMode.Treble)
                return closestTrebleLineNumber;
        else
            return closestBassLineNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StaffModeStateImp){
            StaffModeStateImp toCompare = (StaffModeStateImp) obj;
            return staffMode.equals(toCompare.staffMode);
        }
        return false;
    }

    @Override
    public String toString() {
        return staffMode.toString();
    }
}
