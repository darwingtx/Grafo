package grafos;

import javax.swing.JOptionPane;

public class Menu {
      public Menu(){

      }
    public static void MenuPrincipal() {
        int opcion;
        Grafo G = new Grafo(JOptionPane.showInputDialog(null, "Ingrese las aristas del grafo\n"+"Asi ((A,B,2),(B,c,4))"));;
        boolean b=true;
        boolean x=true;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "----Menu principal----\n"
                    + "1. Mostrar Grafo\n"
                    + "2. Matriz de Adyacencia\n"
                    + "3. Matriz de Incidencia\n"
                    + "4. Lista de adyacencia\n"
                    + "5. Crear nuevo grafo\n"
                    + "6. Recorridos.\n"
                    + "7. Distancia Mínima\n"
                    + "0. Salir "));

            switch (opcion) {
                case 1:
                    G.Grafico();
                    break;

                case 2:
                  JOptionPane.showMessageDialog(null, G.MostrarMA());
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, G.MostrarMI());
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, G.MostrarLista());
                    break;
                case 5:
                    Grafo Gx = new Grafo(JOptionPane.showInputDialog(null, "Ingrese las aristas del grafo\n"+"Asi ((A,B,2),(B,c,4))"));
                    G = Gx;
                    break;
                case 6:
                    Menu.MenuRecorridos(G);
                    break;
                case 7:
                do{ 
                    char a,c;
                    a=JOptionPane.showInputDialog(null,"Ingrese el Nodo inicial" ).charAt(0);
                    b=Verificar(G,a);
                    c=JOptionPane.showInputDialog(null,"Ingrese el Nodo Final" ).charAt(0);
                    x=Verificar(G,c);
                    if(!b && !x){
                        G.Grafico();
                        JOptionPane.showMessageDialog(null, G.Dijkstra(a, c)); 
                    }else{
                        JOptionPane.showInputDialog("Nodos ingresados invalidos");
                    }
                }while (b); 
                    
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Gracias por probar nuestro proyecto", "Salir", 3);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción incorrecta, ingresa otra opción", "Opción invalida", 0);
                    break;
            }
        } while (opcion != 0);
    }

    public static void MenuRecorridos(Grafo G) {
        int opcion=0;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Menú recorridos\n"
            +"1. DFS\n"
            +"2. BFS\n"
            +"0. Salir\n"));

            switch (opcion) {
                case 1:
                StringBuilder s = new StringBuilder();
                int []visitado= new int[G.getNodos().length];
                char x = JOptionPane.showInputDialog(null, "Ingrese nodo inicial").charAt(0);
                if(!Verificar(G,x)){
                    
                    JOptionPane.showMessageDialog(null,G.DFS(G.buscarEnVector(x), visitado, s));
                }
                
                    break;

                case 2:
                boolean []visit= new boolean[G.getNodos().length];
                 x = JOptionPane.showInputDialog(null, "Ingrese nodo inicial").charAt(0);
                if(!Verificar(G,x)){
                    
                    JOptionPane.showMessageDialog(null,G.BFS(G.buscarEnVector(x), visit));
                }
               

                    break;

                case 0:

                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción incorrecta, ingresa otra opción", "Opción invalida", 0);
                    break;
            }
        } while (opcion != 0);
    }

    private static boolean Verificar(Grafo G,char A){
        boolean B=true;
        for(int i=0;i<G.getNodos().length;i++){
                if(G.getNodos()[i]== A){
                    B = false;
                } 
        }

        return B;


    }

}
