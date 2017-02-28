package reactiveAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Environment {

    private final Cell[][] grid;
    private final LinkedList<ReactiveAgent> agents;
    private final int numIterations;

    public Environment(int numLines, int numColumns, int numIterations) {
        this.numIterations = numIterations;
        grid = new Cell[numLines][numColumns];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        //Walls
        grid[0][3].setWall(new Wall());
        grid[0][4].setWall(new Wall());
        grid[0][5].setWall(new Wall());
        grid[0][6].setWall(new Wall());
        grid[1][3].setWall(new Wall());
        grid[1][6].setWall(new Wall());

        grid[3][1].setWall(new Wall());
        grid[4][1].setWall(new Wall());
        grid[5][1].setWall(new Wall());
        grid[6][1].setWall(new Wall());
        grid[6][2].setWall(new Wall());
        grid[3][2].setWall(new Wall());
        
        grid[9][3].setWall(new Wall());
        grid[9][4].setWall(new Wall());
        grid[9][5].setWall(new Wall());
        grid[9][6].setWall(new Wall());
        grid[8][3].setWall(new Wall());
        grid[8][6].setWall(new Wall());

        grid[3][8].setWall(new Wall());
        grid[4][8].setWall(new Wall());
        grid[5][8].setWall(new Wall());
        grid[6][8].setWall(new Wall());
        grid[3][7].setWall(new Wall());
        grid[6][7].setWall(new Wall());

        //Garbage
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            int x = r.nextInt(grid.length);
            int y = r.nextInt(grid.length);
            if (!grid[x][y].hasAgent() && !grid[x][y].hasWall()) {
                grid[x][y].setGarbage(new Garbage());
            }
        }

        //Agents
        agents = new LinkedList<ReactiveAgent>();
        agents.add(new ReactiveAgent(getCell(r.nextInt(grid.length), r.nextInt(grid.length))));

    }

    public void run() {
        for (int i = 0; i < numIterations; i++) {
            for (Agent agent : agents) {
                agent.act(this);
                fireUpdatedEnvironment();//Atualiza o ambiente - Redesenha toda a grelha
                System.out.println("Iteration: " + i);
            }
        }

        fireUpdatedEnvironment();//Atualiza o ambiente - Redesenha toda a grelha
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    public Cell getNorthCell(Cell cell) {
        if (cell.getLine() > 0) {
            return grid[cell.getLine() - 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getSouthCell(Cell cell) {
        if (cell.getLine() < grid.length - 1) {
            return grid[cell.getLine() + 1][cell.getColumn()];
        }
        return null;
    }

    public Cell getEastCell(Cell cell) {
        if (cell.getColumn() < grid[0].length - 1) {
            return grid[cell.getLine()][cell.getColumn() + 1];
        }
        return null;
    }

    public Cell getWestCell(Cell cell) {
        if (cell.getColumn() > 0) {
            return grid[cell.getLine()][cell.getColumn() - 1];
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Environment:\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].hasWall()) {
                    s.append('X');
                } else if (grid[i][j].hasAgent()) {
                    s.append('A');
                } else {
                    s.append('.');
                }
            }
            s.append('\n');
        }
        return s.toString();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
