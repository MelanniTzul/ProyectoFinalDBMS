/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.estructura;

import javax.swing.JTextArea;

/**
 *
 * @author melanni
 */
public class ListaEstructura {
    
    //Atributo de tipo estructura
    private Estructura raiz;
    
    //Apunta a null
   public ListaEstructura(){
       this.raiz=null;
   }
   
   public boolean isEmpty(){
       return raiz == null;
   }
   
   
   /*Funcion que inserta al final*/
   public void insertarFInal(Estructura estructura){
       Estructura nuevaEstructura = estructura;
       
       if(isEmpty()){
           raiz =nuevaEstructura;
           return;
       }
       Estructura actual = raiz;
       while (actual.getSiguiente()!= null) {
           actual=actual.getSiguiente();
       }
       actual.setSiguiente(nuevaEstructura);
   }
   
   
   
   /*Funcion de tipo Estructura  obtener una estructura, 
   asi obtenerla*/
   public Estructura getEstructura(String nombre){
       Estructura actual = raiz;
       while(actual != null){
           if (actual.getNombre().equals(nombre)) {
               return actual;
           } 
           actual = actual.getSiguiente();
       }
       return null;
   }
   
   
   public void imprimirLista(JTextArea txtArea){
       Estructura actual = raiz;
       while (actual!=null) {
           txtArea.append("Tabla:" + actual.getNombre() + ", Clave: "+ actual.getLlave()+", Columnas:\n"+actual.getColumnas().imprimirLista()+"\n");
           actual=actual.getSiguiente();
           
       }
   }
    
}
