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
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author melanni
 */
public class LecturaArchivo {

    private ListaEstructura listaEstructura;

    public LecturaArchivo() {
        listaEstructura = new ListaEstructura();
    }

    public void ingresarEstructuraArchivo(File archivo,  JTextArea txtArea) {
        try {
            // Se crea una instancia del factory para obtener el builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Se parsea el archivo y se obtiene un objeto Document
            Document documento = builder.parse(archivo);
            // Se obtiene una lista de nodos con nombre "estructura"
            NodeList nodoLista = documento.getElementsByTagName("estructura");
            //Recorrido de las estructuras encontradas
            for (int i = 0; i < nodoLista.getLength(); i++) {
                Node nodo = nodoLista.item(i);
                // Verificar si el tipo de nodo es un nodo de elemento
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Estructura estructura = new Estructura(); //Estructura 
                    ListaColumna lista = new ListaColumna(); //Lista de campos de la estructura             
                    NodeList nodosSecundarios = nodo.getChildNodes();
                    String llave = null; //clave de la tabla
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
                                Estructura temporal = listaEstructura.getEstructura(valorNodo);
                                if (temporal == null) {//si es igual a null
                                    estructura.setNombre(valorNodo);
                                } else {
                                    System.out.println("La tabla con el nombre: " + valorNodo + " ya existe! ");
                                    break;
                                }
                            } else if (nombreNodo.equals("clave")) {
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
                        NodoColumna c = lista.getNodo(llave);// Se establece la llave de la estructura
                        if (c != null) {
                            estructura.setLlave(llave);// Se establece la llave de la estructura
                            estructura.setColumnas(lista);// Se establece la lista de columnas de la estructura
                            listaEstructura.insertarFInal(estructura);// Se inserta la estructura en la lista de estructuras
                            System.out.println("Estructura creada");// Se imprime un mensaje indicando que se ha creado la estructura
                        } else {
                            System.out.println("No tiene los datos completos");// No se encontró la columna de llave en la lista
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaEstructura.imprimirLista( txtArea);
    }

    
    
    /*FUNCION */
    public void agregarFilaArchivo(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            Element raiz = documento.getDocumentElement();
            // Obtener el nombre de la etiqueta raíz
            String nombreEtiquetaRaiz = raiz.getTagName();
            NodeList nodoLista = documento.getElementsByTagName(nombreEtiquetaRaiz);
            NodeList lista = nodoLista.item(0).getChildNodes();
            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    ListaColumna listaFilas = new ListaColumna();
                    String nombre = nodo.getNodeName().replaceAll(" ", "");
                    Estructura st = listaEstructura.getEstructura(nombre);
                    int cantidadColumnas = 0;
                    if (st != null) {
                        cantidadColumnas = st.getColumnas().obtenerCantidad();
                        NodeList listaColumnas = nodo.getChildNodes();
                        for (int j = 0; j < listaColumnas.getLength(); j++) {
                            Node n = listaColumnas.item(j);
                            if (n.getNodeType() == Node.ELEMENT_NODE) {
                                String nombreNodo = n.getNodeName().replaceAll(" ", "");
                                String valorNodo = n.getTextContent().replaceAll(" ", "");//Valor del nodo
                                NodoColumna nc = st.getColumnas().getNodo(nombreNodo);
                                if (nc != null) {
                                    if (!valorNodo.equals("")) {
                                        String tipo = nc.getTipo();
                                        if (tipo.equals("int")) {
                                            if (entero(valorNodo)) {
                                                listaFilas.insertarFinal(new NodoColumna(nombreNodo, valorNodo));
                                            } else {
                                                System.out.println("El valor no es un entero");
                                                break;
                                            }
                                        } else {
                                            listaFilas.insertarFinal(new NodoColumna(nombreNodo, valorNodo));
                                        }
                                    } else {
                                        System.out.println("La columna " + nombreNodo + ", no contiene dato");
                                    }
                                } else {
                                    System.out.println("La columna " + nombreNodo + ", no existe");
                                    break;
                                }
                            }

                        }
                        if (listaFilas.obtenerCantidad() == cantidadColumnas) {
                            System.out.println("Fila Agregada");
                            st.ingresarFila(listaFilas);
                            st.getArbol().printValues();
                        } else {
                            System.out.println("Los datos no son correctos para crear una fila");
                        }
                    } else {
                        System.out.println("La tabla: " + nombre + ", NO EXISTE");
                        break;
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    //ELIMINAR
    public void eliminarFila(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            Element raiz = documento.getDocumentElement();
            // Obtener el nombre de la etiqueta raíz
            String nombreEtiquetaRaiz = raiz.getTagName();
            NodeList nodoLista = documento.getElementsByTagName(nombreEtiquetaRaiz);
            NodeList lista = nodoLista.item(0).getChildNodes();
            Estructura st = listaEstructura.getEstructura(nombreEtiquetaRaiz);
            if (st != null) {
                for (int i = 0; i < lista.getLength(); i++) {
                    Node node = lista.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String nombreNodo = node.getNodeName().replaceAll(" ", "");
                        String valorNodo = node.getTextContent().replaceAll(" ", "");
                        if (st.getLlave().equals(nombreNodo)) {
                            //Buscar en arbol y eliminar
                        } else {
                            System.out.println(nombreNodo + ", No es llave primaria");
                        }
                    }
                }
            } else {
                System.out.println("La tabla de nombre: " + nombreEtiquetaRaiz + ", NO EXISTE");
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    //Reporte
    public void report(File archivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivo);
            NodeList nodoLista = documento.getElementsByTagName("reporte");
            for (int i = 0; i < nodoLista.getLength(); i++) {//Recorrido de las estructuras encontrada
                Node nodo = nodoLista.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
        }
    }

    //Funcion verifica si es entero
    public boolean entero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
