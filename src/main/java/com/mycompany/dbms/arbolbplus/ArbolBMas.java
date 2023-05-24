/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.arbolbplus;

import com.mycompany.dbms.estructura.ListaColumna;
import javax.swing.JTextArea;

/**
 *
 * @author melanni
 */
//El arbol b + es una lista enlazada
public class ArbolBMas {

    private NodoArbolBMas raiz;
    private int orden;

    // Constructor:
    public ArbolBMas() {
        this.orden = 200;// - Crea un nuevo árbol B+ con un orden predeterminado .
        this.raiz = new NodoArbolBMas(orden, true); // - Inicializa la raíz como un nuevo nodo hoja.
    }

    public void insertar(int llave, ListaColumna valor) {
        NodoArbolBMas nodo = raiz;
        if (nodo.getNumllaves() == 2 * orden - 1) {// Verificar si el nodo actual está lleno
            // Crear una nueva raíz y asignar la raíz actual como su hijo
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(orden, false);
            nuevaRaiz.getHijos()[0] = raiz;
            raiz = nuevaRaiz;
            nodo.dividirNodos(orden, nuevaRaiz.getHijos()[0]);   // Dividir el nodo actual y actualizar la raíz
            insertarNoCompleto(nuevaRaiz, llave, valor); // Insertar el par de valores en el nuevo nodo raíz o en sus hijos
        } else {
            insertarNoCompleto(nodo, llave, valor); // Si el nodo no está lleno, insertar el par de valores en el nodo actual o en sus hijos
        }
    }

    /* Se utiliza para insertar una llave y valor en un nodo del árbol B+*/
   // private void  insertarNoCompleto(NodoArbolBMas nodo, int llave, ListaColumna valor) {
       private void  insertarNoCompleto(NodoArbolBMas nodo, int llave, ListaColumna valor) {
        int i = nodo.getNumllaves() - 1;
        if (nodo.isEsHoja()) { // Si el nodo es una hoja
            // Desplazarse hacia atrás hasta encontrar la posición adecuada para la nueva llave
            while (i >= 0 && llave < nodo.getLlaves()[i]) {
                nodo.getLlaves()[i + 1] = nodo.getLlaves()[i];// Desplazar la llave hacia la derecha
                nodo.getValores()[i + 1] = nodo.getValores()[i];// Desplazar el valor hacia la derecha
                i--;
            }
            // Insertar la nueva llave y valor en la posición correcta
            nodo.getLlaves()[i + 1] = llave;
            nodo.getValores()[i + 1] = valor;
            nodo.setNumllaves(nodo.getNumllaves() + 1);// Incrementar el número de llaves en el nodo
        } else {
            // Si el nodo no es una hoja
            // Buscar el hijo adecuado para la inserción
            while (i >= 0 && llave < nodo.getLlaves()[i]) {
                i--;
            }
            i++;// Incrementar i para obtener el hijo correcto
            // Verificar si el hijo tiene el número máximo de llaves permitidas
            if (nodo.getHijos()[i].getNumllaves() == 2 * orden - 1) {
                // Realizar una división de nodos para mantener el equilibrio del árbol
                nodo.dividirNodos(i, nodo.getHijos()[i]);
                // Ajustar i según la posición de la nueva llave en el nodo actualizado
                if (llave > nodo.getLlaves()[i]) {
                    i++;
                }
            }
            // Llamar recursivamente a insertNonFull en el hijo correspondiente
            insertarNoCompleto(nodo.getHijos()[i], llave, valor);
        }
    }
    
    

    public Object buscar(int llave) {
        NodoArbolBMas nodo = raiz;// Comenzar la búsqueda desde la raíz del árbol
         // Mientras no se llegue a una hoja
        while (!nodo.isEsHoja()) {
            int i = 0;
            // Buscar la posición adecuada para la llave en el nodo actual
            while (i < nodo.getNumllaves()&& llave >= nodo.getLlaves()[i]) {
                i++;
            }
              // Moverse al hijo correspondiente
            nodo = nodo.getHijos()[i];
        }
         // Una vez se llega a una hoja, llamar al método buscar en el nodo hoja
        return nodo.buscar(llave);
    }
    
    
    //Funcion que me imprime todas las filas creadas
     public void imprimirValores(JTextArea txtArea) {
        NodoArbolBMas nodo = raiz;
        while (!nodo.isEsHoja()) {
            nodo = nodo.getHijos()[0];
        }
        while (nodo != null) {
            for (int i = 0; i < nodo.getNumllaves(); i++) {
                txtArea.append(nodo.getValores()[i].toString());
                txtArea.append("\n\n");
            }
            nodo = nodo.getSiguienteHoja();
        }
    }
    

}
