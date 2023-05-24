/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.estructura;

import com.mycompany.dbms.arbolbplus.ArbolBMas;

/**
 *
 * @author melanni
 */
public class Estructura {
    
    /*Atributos*/
    private String nombre;
    private String llave;
    private Estructura siguiente;
    private ListaColumna columnas;
    private ArbolBMas arbol;

    
    
    
    /*Constructor vacio*/
    public Estructura(){
        this.siguiente=null;
        this.arbol=new ArbolBMas();
        
    }
    
//      /*Constructor*/
//    public Estructura(){
//        this.siguiente=null;
//    }
  
    
    public void ingresarFila(ListaColumna dato){
        this.arbol.insertar(0, dato);
    }
  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Estructura getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Estructura siguiente) {
        this.siguiente = siguiente;
    }

    public ListaColumna getColumnas() {
        return columnas;
    }

    public void setColumnas(ListaColumna columnas) {
        this.columnas = columnas;
    }

    public ArbolBMas getArbol() {
        return arbol;
    }

    public void setArbol(ArbolBMas arbol) {
        this.arbol = arbol;
    }
 
    
    
    
    
    
    
}
