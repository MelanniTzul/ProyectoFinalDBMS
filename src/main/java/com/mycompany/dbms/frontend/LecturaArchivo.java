/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dbms.frontend;

import com.mycompany.dbms.estructura.Estructura;
import com.mycompany.dbms.estructura.ListaColumna;
import com.mycompany.dbms.estructura.ListaEstructura;
import com.mycompany.dbms.estructura.NodoColumna;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author melanni
 */
public class LecturaArchivo {
    
    private ListaEstructura listaEstructura;
    
    public LecturaArchivo(){
        listaEstructura = new ListaEstructura();   
    }
    
   
    
    public void ingresarEstructuraArchivo(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            NodeList nodoLista = documento.getElementsByTagName("estructura");
            //Recorrido de las estructuras encontradas
            for (int i = 0; i < nodoLista.getLength(); i++) {
                Node nodo = nodoLista.item(i);
                // Verificar si el tipo de nodo es un nodo de elemento
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Estructura estructura = new Estructura(); //Estructura 
                    ListaColumna lista = new ListaColumna(); //Lista de campos de la estructura             
                    NodeList nodosSecundarios = nodo.getChildNodes();
                    String llave = null; //llave de la tabla
                    //Recorrido de los hijos de una estructura
                    for (int j = 0; j < nodosSecundarios.getLength(); j++) { 
                        Node nodoHija = nodosSecundarios.item(j);
                           // Verificar si el tipo de nodo es un nodo de elemento
                        if (nodoHija.getNodeType() == Node.ELEMENT_NODE) {
                            String nombreNodo = nodoHija.getNodeName().replaceAll(" ", "");;//Nombre del nodo
                            String valorNodo = nodoHija.getTextContent().replaceAll(" ", "");//Valor del nodo
                            valorNodo = valorNodo.replaceAll("\t", "");
                           // Verificar si el nombre del nodo es "tabla"
                            if (nombreNodo.equals("tabla")) {
                                Estructura temporal=listaEstructura.getEstructura(valorNodo);
                                if(temporal==null){//si es igual a null
                                    estructura.setNombre(valorNodo);
                                } else {
                                    System.out.println("La tabla con el nombre: "+valorNodo+" ya existe! ");
                                    break;
                                }
                            } else if (nombreNodo.equals("llave")) {
                                llave = valorNodo;
                            } else if (nombreNodo.equals("relacion")) {
                                boolean table = false;//Bandera para saber que si existe la tabla de referencia
                                String nombreColumna = "";
                                String valorColumna = "";
                                NodeList listaRelaticion = nodoHija.getChildNodes();
                                for (int k = 0; k < listaRelaticion.getLength(); k++) {
                                    Node hijaRelacion = listaRelaticion.item(k);
                                    if (hijaRelacion.getNodeType() == Node.ELEMENT_NODE) {
                                        String nombre = hijaRelacion.getNodeName().replaceAll(" ", "");
                                        String value = hijaRelacion.getTextContent().replaceAll(" ", "");
                                        Estructura st = listaEstructura.getEstructura(nombre);
                                        if (st != null) {
                                            if (st.getLlave().equals(value)) {
                                                table = true;
                                            }
                                        } else {
                                            nombreColumna = nombre;
                                            valorColumna = value;
                                        }
                                    }

                                }
                                if (table && !nombreColumna.equals("") && !valorColumna.equals("")) {
                                    NodoColumna nc = new NodoColumna(nombreColumna, valorColumna);
                                    lista.insertarFinal(nc);
                                } else {
                                    System.out.println("La relacion no cumple los requisitos");
                                    break;
                                }
                            } else {
                                NodoColumna nc = new NodoColumna(nombreNodo, valorNodo);
                                lista.insertarFinal(nc);
                            }
                        }
                    }
                    if (llave != null) {
                        NodoColumna c = lista.getNodo(llave);
                        if (c != null) {
                            estructura.setLlave(llave);
                            estructura.setColumnas(lista);
                            listaEstructura.insertarFInal(estructura);
                            System.out.println("Estructura creada");
                        } else {
                            System.out.println("No tiene los datos completos");
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEstructura.imprimirLista();
    }
    
}
