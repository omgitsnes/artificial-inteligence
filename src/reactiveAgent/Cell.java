package reactiveAgent;

import java.awt.Color;

public class Cell {

    private final int line, column;
    private ReactiveAgent agent;
    private Wall wall;
    private Garbage garbage;
    private VisitedCell visitedCell;

    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public ReactiveAgent getAgent() {
        return agent;
    }

    public void setAgent(ReactiveAgent agent) {
        this.agent = agent;
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public boolean hasWall() {
        return wall != null;
    }

    public void setGarbage(Garbage garbage) {
        this.garbage = garbage;
    }

    public boolean hasVisited() {
        return visitedCell != null;
    }

    public void setVisited() {
        if (this.hasVisited()) {
            this.visitedCell.incrementVisitCount();
        } else {
            this.visitedCell = new VisitedCell();
        }
    }

    public void unsetGarbage() {
        this.garbage = null;
    }

    public boolean hasGarbage() {
        return garbage != null;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Color getColor() {
        if (agent != null) {
            return agent.getColor();
        }
        if (wall != null) {
            return wall.getColor();
        }
        if (garbage != null) {
            return garbage.getColor();
        }
        if (visitedCell != null) {
            return visitedCell.getColor();
        }

        return Color.WHITE;
    }
}
