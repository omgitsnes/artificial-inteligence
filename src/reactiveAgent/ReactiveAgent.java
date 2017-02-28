package reactiveAgent;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

/*
    
 */
public class ReactiveAgent implements Agent {

    private Cell cell;
    private LinkedList<Cell> visitedCells;
    private LinkedList<Integer> visitedCellCount;
    private static int MAX_VISITS = 1000;

    public ReactiveAgent(Cell cell) {
        this.cell = cell;
        this.cell.setAgent(this);
        this.visitedCells = new LinkedList<Cell>();
        this.visitedCellCount = new LinkedList<Integer>();
    }

    public void act(Environment environment) {
        execute(decide(buildPerception(environment)), environment);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell.setAgent(null);
        this.cell = cell;
        this.cell.setAgent(this);
    }

    /*
        Percepcao da celula
        Da-nos as 4 direcoes possiveis que o ajente se pode mover
        a celula nessa posicao
     */
    private Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell));
    }

    /*
        Funcao devolve o numero de vezes que uma celula ja foi visitada
    */
    private int getNumberOfVisits(Cell cell) {
        if (!visitedCells.contains(cell)) {
            return 0;
        }
        return visitedCellCount.get(visitedCells.indexOf(cell));
    }

    private Action decide(Perception perception) {
        //Garbage Collecting as first priority
        //If the cell has garbage then then is not possible for it to have Agents or Walls
        if (perception.getN() != null && perception.getN().hasGarbage()) {
            return Action.NORTH;
        }
        if (perception.getE() != null && perception.getE().hasGarbage()) {
            return Action.EAST;
        }
        if (perception.getS() != null && perception.getS().hasGarbage()) {
            return Action.SOUTH;
        }
        if (perception.getW() != null && perception.getW().hasGarbage()) {
            return Action.WEST;
        }

        Action finalAction = null;
        int visits = MAX_VISITS;

        //If no garbage is found nearby then move to another cell that has not been previously visited or has been visited the least
        if (perception.getN() != null && (!perception.getN().hasWall() && !perception.getN().hasAgent())) {
            if (getNumberOfVisits(perception.getN()) < visits) {
                visits = getNumberOfVisits(perception.getN());
                finalAction = Action.NORTH;
            }
        }
        if (perception.getW() != null && (!perception.getW().hasWall() && !perception.getW().hasAgent())) {
            if (getNumberOfVisits(perception.getW()) < visits) {
                visits = getNumberOfVisits(perception.getW());
                finalAction = Action.WEST;
            }
        }
        if (perception.getS() != null && (!perception.getS().hasWall() && !perception.getS().hasAgent())) {
            if (getNumberOfVisits(perception.getS()) < visits) {
                visits = getNumberOfVisits(perception.getS());
                finalAction = Action.SOUTH;
            }
        }
        if (perception.getE() != null && (!perception.getE().hasWall() && !perception.getE().hasAgent())) {
            if (getNumberOfVisits(perception.getE()) < visits) {
                visits = getNumberOfVisits(perception.getE());
                finalAction = Action.EAST;
            }
        }
        
        System.out.println("reactiveAgent.ReactiveAgent.decide()");
        System.out.println("Trying to move: " + finalAction);
        System.out.println("Has #" + visits + " visits!");
        
        return finalAction;
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell = null;
        if (action == Action.NORTH && cell.getLine() != 0) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && cell.getLine() != environment.getNumLines()) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.EAST && cell.getColumn() != environment.getNumColumns()) {
            nextCell = environment.getEastCell(cell);
        } else if (action == Action.WEST && cell.getColumn() != 0) {
            nextCell = environment.getWestCell(cell);
        }

        if (nextCell != null && !nextCell.hasWall() && !nextCell.hasAgent()) {

            if (!visitedCells.contains(cell)) {
                visitedCells.add(cell);
                visitedCellCount.add(1);
            } else {
                int position = visitedCells.indexOf(cell);
                //If the cell has been visited already increments the cell visit count
                visitedCellCount.set(position, visitedCellCount.get(position) + 1);
            }

            //Move agent to the new cell
            
            cell.setVisited();
            setCell(nextCell);
            

            //Collect Grabage
            if (nextCell.hasGarbage()) {
                nextCell.unsetGarbage();
            }
            
        }
    }

    public Color getColor() {
        return Color.BLACK;
    }

}
