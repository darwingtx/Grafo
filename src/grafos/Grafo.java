package grafos;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.PriorityQueue;

public class Grafo {

    // Atributos
    private Nodo[] vect;
    private char nodos[];
    private int matrizAd[][];
    private int matrizIn[][];

    public Grafo(String s) {
        creargrafo(s);
    }

    public String BFS(int inicio, boolean[] visitado) {
        Queue<Integer> queue = new ArrayDeque<>();
        visitado[inicio] = true;
        queue.add(inicio);
        String s = "";
        while (!queue.isEmpty()) {
            int v = queue.poll();
            s = s + nodos[v] + " ";

            Nodo p = vect[v];
            while (p != null) {
                int u = buscarEnVector(p.getDato());
                if (!visitado[u]) {
                    visitado[u] = true;
                    queue.add(u);
                }
                p = p.getLiga();
            }
        }
        return s;
    }

    public String DFS(int v, int visitado[], StringBuilder string) {
        Nodo p = null;
        char w = ' ';
        visitado[v] = 1;
        string.append(nodos[v]);

        p = vect[v];
        while (p != null) {
            w = p.getDato();
            if (visitado[buscarEnVector(w)] == 0) {
                DFS(buscarEnVector(w), visitado, string);
            }
            p = p.getLiga();
        }

        return string.toString();
    }

    private Nodo InsertarFinal(Nodo q, char d, int peso) {
        Nodo x = new Nodo(d, peso);
        if (q != null) {
            Nodo p = q;
            while (p.getLiga() != null) {
                p = p.getLiga();
            }
            p.setLiga(x);
        } else {
            q = x;
        }

        return q;

    }

    public String Dijkstra(char inicio, char destino) {
        String[][] tabla = new String[nodos.length + 1][nodos.length + 1];

        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                tabla[i][j] = "-------\t";
            }
        }
        tabla[0][0] = "  ";
        for (int j = 0; j < tabla[0].length - 1; j++) {
            tabla[0][j + 1] = "P" + (j + 1) + "     \t";
            tabla[j + 1][0] = nodos[j] + "";
        }

        int start = buscarEnVector(inicio);
        boolean[] visitado = new boolean[nodos.length];
        Nodo p = vect[start];
        visitado[start] = true;
        int distancia = 0;
        int v = 0;
        int pre = 0;
        String s = "";
        pre = start;
        int prev = start;
        for (int i = 0; i < vect.length; i++) {
            int minDistancia = Integer.MAX_VALUE;
            tabla[v + 1][i + 2] = "[" + distancia + nodos[prev] + "]  \t";
            s = s + nodos[pre] + "";
            if (visitado[buscarEnVector(destino)] == false) {
                prev = buscarEnVector(nodos[v]);
                
                while (p != null) {
                    if ((p.getPeso() < minDistancia) && visitado[buscarEnVector(p.getDato())] == false) {
                        minDistancia = p.getPeso();
                        v = buscarEnVector(p.getDato());

                    }
                    if (visitado[buscarEnVector(p.getDato())] == false) {
                        tabla[buscarEnVector(p.getDato()) + 1][i + 1] = "[" + (distancia + p.getPeso()) + nodos[pre]
                                + "]  \t";
                    }
                    p = p.getLiga();
                }

                distancia = distancia + minDistancia;
                if(distancia<0){
                    return "No hay camino existente";
                }
                visitado[v] = true;
                p = vect[v];
                pre = v;

            } else {
                tabla[pre + 1][i + 1] = "[" + distancia + nodos[prev] + "]  \t";
                i = i + vect.length;
            }

        }
        tabla[start + 1][1] = "[" + 0 + inicio + "&]   \t";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < nodos.length + 1; i++) {
            for (int j = 0; j < nodos.length + 1; j++) {
                sb.append(tabla[i][j]).append(" ");
            }
            sb.append("\n");
        }

        sb.append("\n La distancia minima entre ")
                .append(inicio)
                .append(" y ")
                .append(destino)
                .append(" es ")
                .append(distancia);

        return sb.toString() + "\n " + s;
    }

    public String MostrarLista() {
        String s = "";
        Nodo p = null;
        int i = 0;
        while (i < vect.length) {
            p = vect[i];
            s = s + "\n[" + nodos[i] + "] ";
            while (p != null) {
                s = s + "---> |" + p.getDato() + ", " + p.getPeso() + "| ";
                p = p.getLiga();
            }
            i++;
        }
        return s;
    }

    public void creargrafo(String s) {

        String[] conexiones = ObtenerNodos(s);
        vect = new Nodo[nodos.length];
        MTAdyacencia(conexiones);
        for (int i = 0; i < matrizAd.length; i++) {
            for (int j = 0; j < matrizAd[0].length; j++) {
                if (matrizAd[i][j] != 0) {
                    vect[i] = InsertarFinal(vect[i], nodos[j], matrizAd[i][j]);
                }
            }
        }
        crearmatrizIn(conexiones);

    }

    public String MostrarMA() {
        String s = "[  ]";

        for (int q = 0; q < matrizAd[0].length; q++) {
            s += "[" + nodos[q] + "]";

        }
        s += "\n";
        for (int i = 0; i < matrizAd.length; i++) {
            s += "[" + nodos[i] + "]";
            for (int j = 0; j < matrizAd[0].length; j++) {
                s += "[" + matrizAd[i][j] + "]";
            }
            s += "\n";
        }
        return s;
    }

    public String MostrarMI() {
        String s = "[  ]\t    ";

        for (int p = 1; p < matrizIn[0].length; p++) {
            s += "[e" +p+ "]\t     ";

        }
        s += "\n";
        for (int i = 1; i < matrizIn.length; i++) {
            s += "[" + nodos[i - 1] + "]\t    ";
            for (int j = 1; j < matrizIn[0].length; j++) {
                s += "   [" + matrizIn[i][j] + "]\t     ";
            }
            s += "\n";
        }
        return s;
    }

    private void MTAdyacencia(String[] conexiones) {
        matrizAd = new int[nodos.length][nodos.length];
        boolean bol = true;
        int i = 0;
        int auxi = 0;
        int auxj = 0;
        while (i < conexiones.length && bol) {

            if (!conexiones[i].equals("")) {
                auxi = buscarEnVector(conexiones[i].charAt(0));
                auxj = buscarEnVector(conexiones[i + 1].charAt(0));
                matrizAd[auxi][auxj] = Integer.parseInt(conexiones[i + 2]);
                if (matrizAd[auxj][auxi] == 0) {
                    matrizAd[auxj][auxi] = Integer.parseInt(conexiones[i + 2]);
                } else {
                    bol = !bol;
                    matrizAd = new int[nodos.length][nodos.length];
                }
                i += 2;
            }
            i++;
        }
        i = 0;
        while (i < conexiones.length && !bol) {
            if (!conexiones[i].equals("")) {
                auxi = buscarEnVector(conexiones[i].charAt(0));
                auxj = buscarEnVector(conexiones[i + 1].charAt(0));
                matrizAd[auxi][auxj] = Integer.parseInt(conexiones[i + 2]);
                i += 2;
            }

            i++;
        }

    }

    private void crearmatrizIn(String[] conexiones) {
        String[] s = obtenerAris(conexiones);
        matrizIn = new int[nodos.length + 1][s.length / 3 + 1];
        int p = 0, r = 0, f = 2;

        for (int q = 1; q < matrizIn[0].length; q += 1) {
            matrizIn[0][q] = Integer.parseInt(s[f]);
            f += 3;

        }
        int k = 0;
        int z = 0;
        for (int i = 1; i < s.length; i += 2) {
            if (Character.isDigit(s[i - 1].charAt(0))) {
                i += 1;
                k = 0;
            }
            p = buscarEnVector(s[i - 1].charAt(0)) + 1;
            r = ((i + 2) / 3) + k;
            matrizIn[p][r] = 1;

            p = buscarEnVector(s[i].charAt(0)) + 1;

            matrizIn[p][r] = 1;
            k = 1;
        }

    }

    private String[] obtenerAris(String[] conexiones) {
        String[] a = new String[conexiones.length];
        int j = 0, cont = 0;
        for (int i = 0; i < conexiones.length; i++) {
            if (!conexiones[i].equals("")) {
                a[j] = conexiones[i];
                cont++;
                j++;
            }

        }
        String[] b = new String[cont];
        for (int q = 0; q < cont; q++) {
            b[q] = a[q];
        }
        return b;

    }

    private String[] ObtenerNodos(String s) {
        String[] vectaux = s.split("[(), ]");
        char[] c = new char[vectaux.length];
        char b;
        String p = "";
        int i = 0, j = 0;
        boolean bol = true;

        while (i < vectaux.length) {
            if (!vectaux[i].equals("")) {
                b = vectaux[i].charAt(0);
                c = p.toCharArray();
                bol = true;
                j = 0;
                while (j < c.length && bol) {
                    if (c[j] == b) {
                        bol = false;
                    }
                    j++;
                }
                if (bol != false && !Character.isDigit(b)) {
                    p += b;
                }
            }
            i++;
        }
        nodos = c;
        return vectaux;
    }

    public int buscarEnVector(char dato) {
        for (int i = 0; i < nodos.length; i++) {
            if (nodos[i] == dato) {
                return i;
            }
        }
        return -1;
    }

    public Nodo[] getVect() {
        return vect;
    }

    public void setVect(Nodo[] vect) {
        this.vect = vect;
    }

    public char[] getNodos() {
        return nodos;
    }

    public void setNodos(char[] nodos) {
        this.nodos = nodos;
    }

    public int[][] getMatrizAd() {
        return matrizAd;
    }

    public void setMatrizAd(int[][] matrizAd) {
        this.matrizAd = matrizAd;
    }

    public int[][] getMatrizIn() {
        return matrizIn;
    }

    public void setMatrizIn(int[][] matrizIn) {
        this.matrizIn = matrizIn;
    }

    public void Grafico() {
        GraficoGrafo x = new GraficoGrafo(matrizAd, nodos);
       // x.pintarGrafo(matrizAd);
    }


}
