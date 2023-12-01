package grafos;

public class Nodo {
    // Atributos
    private Nodo Liga;
    private char dato;
    private int peso;

    public Nodo(char dato, int peso) {
        Liga = null;
        this.dato = dato;
        this.peso = peso;
       
    }
    

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Nodo getLiga() {
        return Liga;
    }

    public void setLiga(Nodo liga) {
        Liga = liga;
    }

    public char getDato() {
        return dato;
    }

    public void setDato(char dato) {
        this.dato = dato;
    }
}
