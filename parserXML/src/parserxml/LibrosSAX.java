/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parserxml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author juang
 */
public class LibrosSAX extends DefaultHandler{
    public LibrosSAX(){ super();}
    //Handler para el evento comienzo de documento
    @Override
    public void startDocument() throws SAXException{
        super.startDocument();
        System.out.println("Comienza parseado del documento.");
    }
    
    
    //Handler para el evento fin de documento
    @Override
    public void endDocument() throws SAXException{
        super.endDocument();
        System.out.println("\nFin del parseado del documento.");
    }
    
    //Handler etiqueta apertura
    @Override
    public void startElement(String uri,String localName, String qName, Attributes attributes) throws SAXException{
        super.startElement(uri, localName, qName, attributes);
        
        //Abro etiqueta
        System.out.print("<"+qName);
        
        //Recorro los atributos si los hay
        if(attributes != null){
            for (int i = 0; i <attributes.getLength(); i++) {
                System.out.print(" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"=\"");
            }
        }
        
        //cierro etiqueta
        System.out.print(">");
    }
    
    //Handler etiqueta cierre
    @Override
    public void endElement(String uri,String localName, String qName) throws SAXException{
        super.endElement(uri, localName, qName);
        System.out.print("</"+qName+">");
    }
    
    //Handler para el evento Nodo texto encontrado
    @Override
    public void characters(char[] ch,int start, int length) throws SAXException{
        super.characters(ch, start, length);
        
        String content = new String(ch, start, length);
        System.out.print(content);
    }
}

