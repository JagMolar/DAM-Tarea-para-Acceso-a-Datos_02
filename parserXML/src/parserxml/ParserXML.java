/*
 * TAREA AD02.EJERCICIO 2.
 * Visualizar todas las etiquetas del fichero LIBROS.XML 
 * utilizando las técnicas DOM y SAX.
 * 
 */
package parserxml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author juang <juangmuelas@gmail.com>
 * @since 11/11/2021
 */
public class ParserXML {

    /**
     * Esta primera parte aprovecha el ejemplo mostrado en el punto 6.4.2
     * adaptándolo a nuestro fichero libros.xml, que hemos añadido a
     * nuestro directorio, y así mostrar los datos mediante el DOM
     */
    public static void main(String[] args) throws ParserConfigurationException {
        try {
         File inputFile = new File("Libros.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("ELEMENTO RAIZ :" + 
         doc.getDocumentElement().getNodeName());//retorna el elemento raiz
         NodeList nList = doc.getElementsByTagName("libro");
        
         System.out.println("----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nElemento hijo: " + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                NodeList autorList = eElement.getElementsByTagName("autor");
                System.out.println("Año : "+ eElement.getAttribute("año"));
                System.out.println("Titulo : "+ eElement.getElementsByTagName("titulo").item(0).getTextContent());
                
                /**
                 * Al tener varios elementos autor en la última tag, se 
                 * crea este nuevo for para recorrerlo, y se apunta al 
                 * item a mostrar según lo encuentre.
                 */
                for(int cont=0; cont < autorList.getLength();cont++){
                    Node node1 = autorList.item(cont);
                    if (node1.getNodeType() == node1.ELEMENT_NODE) {
                        System.out.println("Autor:");
                        System.out.println("Nombre:"+ eElement.getElementsByTagName("nombre").item(cont).getTextContent());
                        System.out.println("Apellido:"+ eElement.getElementsByTagName("apellido").item(cont).getTextContent());
                    }             
                }
                System.out.println("Editorial : "+ eElement.getElementsByTagName("editorial").item(0).getTextContent());
                System.out.println("Precio : "+eElement.getElementsByTagName("precio").item(0).getTextContent());
             }
            }
          } catch (Exception e) {
          e.printStackTrace();
       }//fin try-catch
        
        
        /**
         * RECORRIENDO ELEMENTOS CON SAX
         * según ejemplo video plataforma
         */
             
        try {
            //Obtención del Parser
            SAXParserFactory spf =  SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            
            //Obtenemos la clase creado para manejar los eventos
            DefaultHandler LibrosSAX = new LibrosSAX();
            System.out.println("\nVOLCADO SAX\n***********");
            //Lanzo el parseado
            saxParser.parse(new File("Libros.xml"), LibrosSAX);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println(e.getStackTrace());
        }//Fin try-catch SAX
              
    }//fin main    
} //fin clase ParserXML
