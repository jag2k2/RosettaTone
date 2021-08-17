package music.line;

import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.renderer.records.StaffConstants;
import java.awt.*;

public class Line implements Comparable<Line>{
    private final int lineNumber;

    public Line(int lineNumber){
        this.lineNumber = lineNumber;
    }

    public Line(StaffPlaceable staffPlaceable){
        this.lineNumber = 4 + RenderConstants.numberOfLines - staffPlaceable.getOctavePlusPosition();
    }


    protected void draw(Graphics2D graphics2D, int xStart, int xEnd) {
        int lineThickness = RenderConstants.ledgerLineThickness;
        graphics2D.setStroke(new BasicStroke(lineThickness));
        int yPos = RenderConstants.topMargin + lineNumber * RenderConstants.lineSpacing;
        graphics2D.drawLine(xStart, yPos, xEnd, yPos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Line){
            Line toCompare = (Line) obj;
            return lineNumber == toCompare.lineNumber;
        }
        return false;
    }

    @Override
    public String toString() {
        return (Integer.toString(lineNumber));
    }

    @Override
    public int compareTo(Line otherLine) {
        return lineNumber - otherLine.lineNumber;
    }

    @Override
    public int hashCode() {
        return lineNumber;
    }

    public int getYOffset(){
        return RenderConstants.topMargin + RenderConstants.lineSpacing * lineNumber;
    }

    public boolean isEven(){
        return ((lineNumber % 2) == 0);
    }

    public boolean isAboveVisible(StaffModeEvaluator staffMode) {
        boolean aboveTreble = lineNumber < RenderConstants.trebleStaff.topVisibleLine;
        boolean belowTreble = lineNumber > RenderConstants.trebleStaff.bottomVisibleLine;
        boolean aboveBass = lineNumber < RenderConstants.bassStaff.topVisibleLine;

        if (staffMode.isGrand()){
            return aboveTreble || (aboveBass && belowTreble);
        }
        else if (staffMode.isTrebleOnly())
            return aboveTreble;
        else
            return aboveBass;
    }

    public boolean isBelowVisible(StaffModeEvaluator staffMode) {
        boolean belowBass = lineNumber > RenderConstants.bassStaff.bottomVisibleLine;
        boolean belowTreble = lineNumber > RenderConstants.trebleStaff.bottomVisibleLine;
        boolean aboveBass = lineNumber < RenderConstants.bassStaff.topVisibleLine;
        if (staffMode.isGrand()){
            return belowBass || (aboveBass && belowTreble);
        }
        else if (staffMode.isBassOnly())
            return belowBass;
        else
            return belowTreble;
    }

    public Line getClosestVisibleLine(StaffModeEvaluator staffMode) {
        int closestTrebleLineNumber = getClosestVisibleLineNumber(RenderConstants.trebleStaff);
        int closestBassLineNumber = getClosestVisibleLineNumber(RenderConstants.bassStaff);

        if (staffMode.isGrand()) {
            int distanceFromClosestTreble = Math.abs(lineNumber - closestTrebleLineNumber);
            int distanceFromClosestBass = Math.abs(lineNumber - closestBassLineNumber);
            if (distanceFromClosestTreble < distanceFromClosestBass)
                return new Line(closestTrebleLineNumber);
            else
                return new Line(closestBassLineNumber);
        }
        else if(staffMode.isTrebleOnly())
            return new Line(closestTrebleLineNumber);
        else
            return new Line(closestBassLineNumber);
    }

    protected int getClosestVisibleLineNumber(StaffConstants staffConstants){
        int distanceFromTop = Math.abs(lineNumber - staffConstants.topVisibleLine);
        int distanceFromBottom = Math.abs(lineNumber - staffConstants.bottomVisibleLine);
        if (distanceFromTop < distanceFromBottom)
            return staffConstants.topVisibleLine;
        else
            return staffConstants.bottomVisibleLine;
    }

    public Line getNext(){
        return new Line(lineNumber + 1);
    }

    public Line getPrevious(){
        return new Line(lineNumber - 1);
    }
}