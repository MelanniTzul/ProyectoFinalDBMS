/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.estructura;

/**
 *
 * @author melanni
 */
public class ListaColumna {
    
    private NodoColumna raiz;
    
    public ListaColumna(){
        this.raiz = null;
    }
    
    public boolean isEmty(){
        return raiz == null;
    }
    
    
      //Obtiene un nodo por le nombre, busca el nombre con ese nombre 
    public NodoColumna getNodo(String nombre){
        NodoColumna actual = raiz;
        while(actual != null){
            if(actual.getNombre().equals(nombre)){
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    
    //
    public void insertarFinal(NodoColumna nodoColumna){
        NodoColumna nuevoNodo= nodoColumna;
        if (isEmty()) {
            raiz = nuevoNodo;
            return;         
        }
        
        NodoColumna actual =raiz;
        while (actual.getSiguiente()!= null) { 
            actual=actual.getSiguiente();
            
        }
        actual.setSiguiente(nuevoNodo);
    }
    
    
    //Imprime la lista
    public String imprimirLista(){
        String texto="";
        NodoColumna actual = raiz;
        while (actual!=null) {
            texto += "Nombre " + actual.getNombre()+ ", Tipo " + actual.getTipo()+ "\n";
            actual = actual.getSiguiente();      
        }
        
        return texto;
    }
    
    /*@Override Indicar que un método en una clase hija está sobrescribiendo un método de la clase padre*/
    @Override
    public String toString(){
         String texto= "";
        NodoColumna actual = raiz;
        while (actual != null) {
            texto += actual.getNombre()+ ": " + actual.getTipo()+ "\n";
            actual = actual.getSiguiente();
        }
        return texto;
    }
    
    /*Función que devuelve el número de elementos de la lista enlazada */
    public int obtenerCantidad() {
        int cantidad = 0;
        NodoColumna actual = raiz;
        while (actual != null) {
            cantidad++;
            actual = actual.getSiguiente();
        }
        return cantidad;
    }
}
