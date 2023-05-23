/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.estructura;

/**
 *
 * @author melanni
 */
public class NodoColumna {
    private String tipo;
    private String nombre;
    private NodoColumna siguiente;
    
    public NodoColumna(String nombre, String tipo){
        this.nombre=nombre;
        this.tipo=tipo;
        this.siguiente=null;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NodoColumna getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoColumna siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
    
}
