/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.arbolbplus;

import com.mycompany.dbms.estructura.ListaColumna;

/**
 *
 * @author melanni
 */
public class NodoArbolBMas {

    private int[] llaves;
    private ListaColumna[] valores;
    private NodoArbolBMas[] hijos;
    private boolean esHoja;
    private int numllaves; //Numero de hijos que puede tener
    private NodoArbolBMas siguienteHoja;

    
    
    //CONSTRUCTOR
    public NodoArbolBMas(int orden, boolean esHoja) {
        this.llaves = new int[orden];
        this.valores = new ListaColumna[orden];
        this.hijos = new NodoArbolBMas[orden + 1];
        this.esHoja = esHoja;
        this.numllaves = 0;
        this.siguienteHoja = null;
    }

    //GET Y SET

    public int[] getLlaves() {
        return llaves;
    }

    public void setLlaves(int[] llaves) {
        this.llaves = llaves;
    }

    public ListaColumna[] getValores() {
        return valores;
    }

    public void setValores(ListaColumna[] valores) {
        this.valores = valores;
    }

    public NodoArbolBMas[] getHijos() {
        return hijos;
    }

    public void setHijos(NodoArbolBMas[] hijos) {
        this.hijos = hijos;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public int getNumllaves() {
        return numllaves;
    }

    public void setNumllaves(int numllaves) {
        this.numllaves = numllaves;
    }

    public NodoArbolBMas getSiguienteHoja() {
        return siguienteHoja;
    }

    public void setSiguienteHoja(NodoArbolBMas siguienteHoja) {
        this.siguienteHoja = siguienteHoja;
    }

    
    

    /*FUNCION INSERTAR  NODO A LA LISTA DE COLUMNAS*/
    public void insertar(int llave, ListaColumna value) {
        int i = numllaves - 1;
        while (i >= 0 && llaves[i] > llave) {
            llaves[i + 1] = llaves[i];// Desplaza la llave hacia la derecha
            valores[i + 1] = valores[i];// Desplaza el valor hacia la derecha
            i--;
        }
        llaves[i + 1] = llave;// Inserta la nueva llave en la posición correcta
        valores[i + 1] = value;// Inserta el nuevo valor en la posición correcta
        numllaves++;
    }

    /*FUNCION BUSCAR*/
    public Object buscar(int llave) {
        int i = 0;
        while (i < numllaves && llave > llaves[i]) {
            i++;
        }
        if (i < numllaves && llave == llaves[i]) {
            return valores[i];// Si se encuentra la llave, se devuelve el valor correspondiente
        } else {
            return null;// Si no se encuentra la llave, se devuelve null
        }
    }

    public void dividirNodos(int splitIndex, NodoArbolBMas rightSibling) {
        // Establecer el número de llaves del hermano derecho
        rightSibling.numllaves = numllaves - splitIndex;
        // Mover las llaves y los valores al hermano derecho y limpiar los elementos en el nodo actual
        for (int i = 0; i < rightSibling.numllaves; i++) {
            rightSibling.llaves[i] = llaves[splitIndex + i];
            rightSibling.valores[i] = valores[splitIndex + i];
            llaves[splitIndex + i] = 0;
            valores[splitIndex + i] = null;
        }
        // Si el nodo actual no es una hoja, mover los hijos al hermano derecho y limpiar los elementos en el nodo actual
        if (!esHoja) {
            for (int i = 0; i < rightSibling.numllaves + 1; i++) {
                rightSibling.hijos[i] = hijos[splitIndex + i];
                hijos[splitIndex + i] = null;
            }
        }
        // Actualizar el número de llaves en el nodo actual
        numllaves = splitIndex;
        // Establecer las referencias de la siguiente hoja para mantener la estructura de la lista
        rightSibling.siguienteHoja = siguienteHoja;
        siguienteHoja = rightSibling;
    }
    
//    public void dividirNodos(int index, int order) {
//        NodoArbolBMas right = new NodoArbolBMas(order, esHoja); // Crear un nuevo nodo derecho
//        NodoArbolBMas left = hijos[index];// Obtener el nodo izquierdo actual
//        // Establecer el número de llaves en los nodos izquierdo y derecho
//        right.numllaves = order - 1;
//        left.numllaves = order - 1;
//        // Mantener la estructura de lista enlazada estableciendo la siguiente hoja
//        right.siguienteHoja = left.siguienteHoja;
//        left.siguienteHoja = right;
//         // Mover las llaves y valores desde el nodo izquierdo al nodo derecho
//        for (int i = 0; i < order - 1; i++) {
//            right.llaves[i] = left.llaves[i + order];
//            right.valores[i] = left.valores[i + order];
//        }
//         // Si el nodo izquierdo no es una hoja, mover los hijos correspondientes al nodo derecho
//        if (!left.esHoja) {
//            for (int i = 0; i < order; i++) {
//                right.hijos[i] = left.hijos[i + order];
//            }
//        }
//        // Mover los hijos y ajustar las llaves y valores en el nodo actual
//        for (int i = numllaves; i > index; i--) {
//            hijos[i + 1] = hijos[i];
//            llaves[i] = llaves[i - 1];
//            valores[i] = valores[i - 1];
//        }
//          // Establecer el hijo derecho y la llave y valor correspondientes en el nodo actual
//        hijos[index + 1] = right;
//        llaves[index] = left.llaves[order - 1];
//        valores[index] = left.valores[order - 1];
//        numllaves++;// Incrementar el número de llaves en el nodo actual
//    }
    
    

    /*toString es un metodo de java, obtiene la iformacion del objeto por defecto
    me trae el espacio en memoria*/
    @Override
    public String toString() {
        String texto = "";
        for (int i = 0; i < valores.length; i++) {
            texto += valores[i].toString();// Concatena la representación en cadena de cada elemento en "valores"
        }
        return texto;// Devuelve la concatenación de las representaciones en cadena de los elementos en "valores"
    }
}
