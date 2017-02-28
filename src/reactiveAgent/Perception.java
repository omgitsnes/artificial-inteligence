package reactiveAgent;

/*
    Classe auxiliar que devolve as Celulas vizinhas de uma celula
*/
public class Perception {

    private Cell N, S, E, W;

    public Perception(Cell N, Cell S, Cell E, Cell O) {
        this.N = N;
        this.S = S;
        this.E = E;
        this.W = O;
    }

    public Cell getE() {
        return E;
    }

    public Cell getN() {
        return N;
    }

    public Cell getW() {
        return W;
    }

    public Cell getS() {
        return S;
    }
}