/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reactiveAgent;

import java.awt.Color;

/**
 *
 * @author omgitsnes
 */
public class VisitedCell {

    private int visitCount;

    public Color getColor() {
        if (visitCount == 0) {
            return Color.getHSBColor(0, 200, 1);
        
        } else {
            return Color.getHSBColor(0, 200, 1 + visitCount);
        }
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void incrementVisitCount() {
        this.visitCount++;
    }

}
